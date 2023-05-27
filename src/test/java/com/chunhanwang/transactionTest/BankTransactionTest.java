//package com.chunhanwang.transactionTest;
//
//import com.chunhanwang.entity.*;
//import com.chunhanwang.repository.*;
//import okhttp3.*;
//import org.iban4j.*;
//import org.json.*;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.*;
//import org.springframework.beans.factory.annotation.*;
//import org.springframework.boot.test.autoconfigure.web.servlet.*;
//import org.springframework.boot.test.context.*;
//import org.springframework.http.*;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.*;
//import org.springframework.test.web.servlet.*;
//import org.springframework.test.web.servlet.request.*;
//import scala.*;
//
//import java.io.*;
//import java.lang.*;
//import java.lang.Double;
//import java.util.*;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class BankTransactionTest {
//    private HttpHeaders httpHeaders;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private BankTransactionRepository bankTransactionRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    @BeforeEach
//    public void init() {
//        userRepository.deleteAll();
//        bankTransactionRepository.deleteAll();
//        httpHeaders = new HttpHeaders();
//        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//    }
//
//    @Test
//    public void testGetMonthlyTransactions() throws Exception {
//        createUser();
//        createTransaction("2019-09-29", "GBP -100", "Entertainment");
//
//        double exchangeRate = 0.890161;     // The exchange rate of EUR -> GBP in 2019-09-29
//        double realAmount = 100 / exchangeRate;
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get("/bankTransaction/CH9300000000000000000/2019/9/0/10")
//                .headers(httpHeaders);
//
//        mockMvc.perform(requestBuilder)
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.transactions.monthlyTransactions.[0].id").hasJsonPath())
//                .andExpect(jsonPath("$.transactions.monthlyTransactions.[0].amountWithCurrency").value("GBP -100"))
//                .andExpect(jsonPath("$.transactions.monthlyTransactions.[0].iban").value("CH9300000000000000000"))
//                .andExpect(jsonPath("$.transactions.monthlyTransactions.[0].date").value("2019-09-29"))
//                .andExpect(jsonPath("$.transactions.monthlyTransactions.[0].description").value("Entertainment"))
//                .andExpect(jsonPath("$.transactions.creditAndDebit.credit").value(0))
//                .andExpect(jsonPath("$.transactions.creditAndDebit.debit").value(realAmount))
//                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
//    }
//
//    public void createUser() {
//        AppUser user = new AppUser();
//        user.setIban("CH9300000000000000000");
//        user.setPassword("0000");
//        userRepository.insert(user);
//    }
//
//    public void createTransaction(String date, String amountWithCurrency, String description) {
//        BankTransaction bankTransaction = new BankTransaction();
//        bankTransaction.setId(UUID.randomUUID().toString());
//        bankTransaction.setIban("CH9300000000000000000");
//        bankTransaction.setDate(date);
//        bankTransaction.setAmountWithCurrency(amountWithCurrency);
//        bankTransaction.setDescription(description);
//        bankTransactionRepository.insert(bankTransaction);
//    }
//
////    public double getExchangeRate(String date, String currency) {
////        OkHttpClient client = new OkHttpClient().newBuilder().build();
////
////        Request request = new Request.Builder()
////                .url("https://api.apilayer.com/fixer/" + date + "?symbols=" + currency + "&base=")
////                .addHeader("apikey", "tBFJwzvmAjfR9EXDhin3HpxUIltmrpxo")
////                .method("GET", null)
////                .build();
////        double rates = 0.0;
////        try {
////            Response response = client.newCall(request).execute();
////            JSONObject json = new JSONObject(response.peekBody(2048).string());
////            rates = Double.parseDouble(json.getJSONObject("rates").get(currency).toString());
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        return rates;
////    }
//
//    @AfterEach
//    public void clear() {
//        userRepository.deleteAll();
//        bankTransactionRepository.deleteAll();
//    }
//}
