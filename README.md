
# E-Banking Transactions Monitor

The application is built for an e-banking system, where users can check their transactions information by any given date.


## How to use?
- Clone the github project and run it on your local machine.
- After running the application, open the [Swagger UI](http://localhost:8080/swagger-ui/index.html).
- **Authorization**
  - Use the API endpoint  `POST localhost:8080/verification/login` and get JWT Token.
  - Paste the token on the "Authorize" button on Swagger UI.
- Start using the API.
### API Reference

#### REST API

Get all user's transactions by given calender month

```http
  GET localhost:8080/bankTransaction/{userIban}/{year}/{month}/{offset}/{pageSize}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userIban`| `string` | user - the requested user's iban |
| `year`    | `string` | date - arbitrary year |
| `month`   | `string` | date - arbitrary month |
| `offset`  | `int`    | pagination - page number |
| `pageSize`| `int`    | pagination - size of page |

Sample input:

![input](https://i.imgur.com/iONLHNH.jpg)

Sample output:

![output](https://i.imgur.com/7e1RMMV.jpg)


#### [Fixer API](https://apilayer.com/marketplace/fixer-api?txn=free&e=Sign%20In&l=Success&live_demo=show) (Third-Party)

Get all exchange rates in a timeframe

```http
  GET https://api.apilayer.com/fixer/timeseries?start_date={start_date}&end_date={end_date}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `start_date`| `string` | a date in timeseries (e.g. 2018-08-01) |
| `end_date`  | `string` | a date in timeseries (e.g. 2018-08-31) |

## C4 Diagram

### Level 1:
![C4-Level1](https://i.imgur.com/JdCAfNP.png)

### Level 2:
![C4-Level2](https://i.imgur.com/6a3WQrk.png)

### Level 3:
![C4-Level3](https://i.imgur.com/a5jeikD.png)

## Implementation

### Thoughts
Every user has multiple money accounts in different currencies.  
We have to
- Get all transactions in a given year and month.
- Unify the currencies and compute the total amount of credit, debit value.

Below are the steps to achieve it:
- Use the external API to get a list of **daily exchange rates of given month**.
- In each day's transactions of given month, find the ones that are **made by the user**.
- From each user's transaction in a day, **convert the currency** into EUR and get the credit, debit value.
- **Sum up** the credit, debit value for each day.


### Code Walkthrough

    public PageResponse getMonthlyTransactions(String iban, JSONObject monthlyRate, int offset, int pageSize);

Initialize the total credit and debit value

    HashMap<String, Double> totalCreditAndDebit = new HashMap<>();
    totalCreditAndDebit.put("credit", 0.0);
    totalCreditAndDebit.put("debit", 0.0);

Get all transations in given timeframe & Compute credit and debit value

    List<BankTransaction> monthlyTransactions = new ArrayList<>();  
    
    monthlyRate.keySet().forEach(dateInMonth -> {
        // get all user's transactions in given timeframe
        List<BankTransaction> dailyTransactions = bankTransactionRepository.findByIbanAndDate(iban, dateInMonth);
        monthlyTransactions.addAll(dailyTransactions);      

        // sum up total credit, debit value
        JSONObject allRatesPerDay = monthlyRate.getJSONObject(dateInMonth);     // all exchange rates of a day
        dailyTransactions.stream().forEach(dailyTransaction -> {
            String currencyType = dailyTransaction.getAmountWithCurrency().split(" ")[0];   // user's transaction currency type
            int amountWithCurrency = Integer.parseInt(dailyTransaction.getAmountWithCurrency().split(" ")[1]);
            double rate = Double.parseDouble(allRatesPerDay.get(currencyType).toString());  // exchange rate by currency type
            double realAmount = amountWithCurrency / rate;  // unify all types of currencies to EUR
            if (realAmount < 0.0)
                totalCreditAndDebit.put("debit", totalCreditAndDebit.get("debit") + Math.abs(realAmount));    // -100 -> debit value 100
               else
                   totalCreditAndDebit.put("credit", totalCreditAndDebit.get("credit") + Math.abs(realAmount));
           });
    });