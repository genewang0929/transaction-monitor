package com.chunhanwang.service;

import com.chunhanwang.entity.*;
import com.chunhanwang.repository.*;
import okhttp3.*;
import org.bson.types.*;
import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

@Service
public class BankTransactionService {
    @Autowired
    public AppUserService appUserService;
    @Autowired
    public BankTransactionRepository bankTransactionRepository;

    public List<BankTransaction> getTransactionsByUsers() {
        List<AppUser> users = appUserService.getUsersByIban();
        List<BankTransaction> bankTransactions = new ArrayList<>();

        for (AppUser user: users) {
            for (int year = 1; year <= 10; year++) {
                for (int month = 1; month <= 12; month++) {
                    BankTransaction bankTransaction = new BankTransaction();
                    bankTransaction.setId(new ObjectId().toString());
                    bankTransaction.setIban(user.getIban());
                    bankTransaction.setAmountWithCurrency(getAmountWithCurrency());
                    bankTransaction.setDate(getDate());
                    bankTransaction.setDescription(getDescription());
                    bankTransactions.add(bankTransaction);
                }
            }
        }

        return bankTransactions;
    }

    public PageResponse getMonthlyTransactions(JSONObject monthlyRate, int offset, int pageSize) {
        List<BankTransaction> monthlyTransactions = new ArrayList<>();
        HashMap<String, Double> totalCreditAndDebit = new HashMap<>();
        totalCreditAndDebit.put("credit", 0.0);
        totalCreditAndDebit.put("debit", 0.0);

        // get monthlyTransactions, totalCreditAndDebit
        monthlyRate.keySet().forEach(dateInMonth -> {
            // TODO -> find transactions by date
            List<BankTransaction> dailyTransactions = bankTransactionRepository.findByDate(dateInMonth);
            monthlyTransactions.addAll(dailyTransactions);

            JSONObject allRatesPerDay = monthlyRate.getJSONObject(dateInMonth);     // all exchange rates of a day
            dailyTransactions.stream().forEach(dailyTransaction -> {
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
//
//    public void getTotalCredit(List<BankTransaction> monthlyTransaction, List<JSONObject> monthlyRate) {
//        for (int i = 1; i <= 31; i++) {
//            if (i >= monthlyRate.size())
//                break;
//            JSONObject dailyRateOfAllCurrency = monthlyRate.get(i - 1);  // 0~30
//
//        }
//
//    }

    public JSONObject getMonthlyRate(int inputMonth, int inputYear) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        JSONObject rates = new JSONObject();

        String month = String.valueOf(inputMonth);
        if (inputMonth < 10)
            month = "0" + month;
        String startDate = inputYear + "-" + month + "-01";
        String endDate = inputYear + "-" + month + "-31";

        Request request = new Request.Builder()
                .url("https://api.apilayer.com/fixer/timeseries?=start_date" + startDate + "&end_date=" + endDate)
                .addHeader("apikey", "tBFJwzvmAjfR9EXDhin3HpxUIltmrpxo")
                .method("GET", null)
                .build();
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

    public String getAmountWithCurrency() {
        Random rand = new Random();
        String[] typeOfCurrency = {"AED","AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD"};
        int amount = rand.nextInt(100);
        amount = (rand.nextBoolean()) ? amount : amount * (-1); // + or -
        int type = rand.nextInt(typeOfCurrency.length);
        return typeOfCurrency[type] + " " + amount; // GBP -100
    }

    public String getDate() {
        Random rand = new Random();
//        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int year = rand.nextInt(10) + 2013;
        int month = rand.nextInt(12) + 1;
        String day = "31";
        if (month == 2)
            day = "28";
        else if (month == 4 || month == 6 || month == 9 || month == 11)
            day = "30";
        if (Integer.parseInt(day) < 10)
            day = "0" + day;

        return year + "-" + month + "-" + day;
    }

    public String getDescription() {
        Random rand = new Random();
        String[] descriptions = {"Online payment", "Food", "Entertainment", "Daily necessity", "Tax", "Investment", "Travel"};
        int type = rand.nextInt(descriptions.length);
        return descriptions[type];
    }
}
