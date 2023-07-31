package com.chunhanwang.service;

import com.chunhanwang.entity.*;
import com.chunhanwang.repository.*;
import okhttp3.*;
import org.json.*;
import org.springframework.data.domain.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

@Service
public class BankTransactionService {
    public final BankTransactionRepository bankTransactionRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "ebankTopic-transactions";
    public final AppUserService appUserService;

    public BankTransactionService(BankTransactionRepository bankTransactionRepository, AppUserService appUserService, KafkaTemplate<String, Object> kafkaTemplate) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.appUserService = appUserService;
        this.kafkaTemplate = kafkaTemplate;
    }


    public Page<BankTransaction> getTransactionsByIban(String iban, int offset, int pageSize) {
        Pageable paging = PageRequest.of(offset, pageSize, Sort.by("date").descending());
        return bankTransactionRepository.findByIban(iban, paging);
    }

    public void generateTenTransactionsByUsers(String iban) {
        for (int i = 1; i <= 10; i++) {
            BankTransaction bankTransaction = new BankTransaction();
            bankTransaction.setId(UUID.randomUUID().toString());
            bankTransaction.setIban(iban);
            bankTransaction.setAmountWithCurrency(getRandomAmountWithCurrency());
            bankTransaction.setDate(getRandomDate());
            bankTransaction.setDescription(getRandomDescription());
            kafkaTemplate.send(TOPIC, bankTransaction);
//            bankTransactionRepository.insert(bankTransaction);
        }

    }

    public void deleteAllTransactions() {
        bankTransactionRepository.deleteAll();
    }

    public PageResponse getMonthlyTransactions(String iban, JSONObject monthlyRate, int offset, int pageSize) {
        List<BankTransaction> monthlyTransactions = new ArrayList<>();
        HashMap<String, Double> totalCreditAndDebit = new HashMap<>();
        totalCreditAndDebit.put("credit", 0.0);
        totalCreditAndDebit.put("debit", 0.0);

        // get monthlyTransactions, totalCreditAndDebit
        monthlyRate.keySet().forEach(dateInMonth -> {
            List<BankTransaction> dailyTransactions = bankTransactionRepository.findByIbanAndDate(iban, dateInMonth);
            monthlyTransactions.addAll(dailyTransactions);

            JSONObject allRatesPerDay = monthlyRate.getJSONObject(dateInMonth);     // all exchange rates of a day
            dailyTransactions.forEach(dailyTransaction -> {
                String currencyType = dailyTransaction.getAmountWithCurrency().split(" ")[0];   // user's transaction currency type
                int amountWithCurrency = Integer.parseInt(dailyTransaction.getAmountWithCurrency().split(" ")[1]);
                double rate = Double.parseDouble(allRatesPerDay.get(currencyType).toString());  // exchange rate by currency type
                double realAmount = amountWithCurrency / rate;  // unify all types of currencies to EUR
                if (realAmount < 0.0)
                    totalCreditAndDebit.put("debit", totalCreditAndDebit.get("debit") + Math.abs(realAmount));    // -100 <-> debit 100
                else
                    totalCreditAndDebit.put("credit", totalCreditAndDebit.get("credit") + Math.abs(realAmount));
            });
        });

        // pagination
        Pageable paging = PageRequest.of(offset, pageSize, Sort.by("date").ascending());
        int start = Math.min((int)paging.getOffset(), monthlyTransactions.size());
        int end = Math.min((start + paging.getPageSize()), monthlyTransactions.size());
        Page<BankTransaction> page = new PageImpl<>(monthlyTransactions.subList(start, end), paging, monthlyTransactions.size());

        return new PageResponse(page.getContent(), totalCreditAndDebit);
    }

    public JSONObject getMonthlyRate(int inputYear, int inputMonth) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String month = String.valueOf(inputMonth);
        if (inputMonth < 10)
            month = "0" + month;
        String startDate = inputYear + "-" + month + "-01";
        String endDate = inputYear + "-" + month + "-31";

        // user Fixer API
        Request request = new Request.Builder()
                .url("https://api.apilayer.com/exchangerates_data/timeseries?start_date=" + startDate + "&end_date=" + endDate)
                .addHeader("apikey", "tBFJwzvmAjfR9EXDhin3HpxUIltmrpxo")
                .method("GET", null)
                .build();
        JSONObject rates = new JSONObject();
        try {
            Response response = client.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            rates = json.getJSONObject("rates");
//            rates.keySet().forEach(dateInMonth -> rateList.add(rates.getJSONObject(dateInMonth)));  // store all daily exchange rates in a month
//            System.out.println(rates);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rates;
    }

    public String getRandomAmountWithCurrency() {
        Random rand = new Random();
        String[] typeOfCurrency = {"AED","AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD"};
        int amount = rand.nextInt(100);
        amount = (rand.nextBoolean()) ? amount : amount * (-1); // + or -
        int type = rand.nextInt(typeOfCurrency.length);
        return typeOfCurrency[type] + " " + amount; // GBP -100
    }

    public String getRandomDate() {
        Random rand = new Random();
//        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int year = rand.nextInt(10) + 2013;
        int month = rand.nextInt(12) + 1;
        int day = rand.nextInt(31) + 1;
        String dayInString = "";
        String monthString = "";
        if (day > 28 && month == 2)
            day = 28;
        else if (day == 31 && (month == 4 || month == 6 || month == 9 || month == 11))
            day = 30;

        if (month < 10)
            monthString = "0" + month;
        else
            monthString = String.valueOf(month);
        if (day < 10)
            dayInString = "0" + day;
        else
            dayInString = String.valueOf(day);

        return year + "-" + monthString + "-" + dayInString;
    }

    public String getRandomDescription() {
        Random rand = new Random();
        String[] descriptions = {"Online payment", "Food", "Entertainment", "Daily necessity", "Tax", "Investment", "Travel"};
        int type = rand.nextInt(descriptions.length);
        return descriptions[type];
    }
}
