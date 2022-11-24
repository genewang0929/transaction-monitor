package com.chunhanwang.entity;

import lombok.*;
import org.iban4j.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

@Document(collection = "bankTransactions")
@Data
public class BankTransaction {
    @Id
    String id;
    String amountWithCurrency;
    String iban;
    String date;
    String description;
}
