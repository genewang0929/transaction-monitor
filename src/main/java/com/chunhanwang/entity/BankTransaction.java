package com.chunhanwang.entity;

import lombok.*;

@Data
public class BankTransaction {
    String id;
    String amountWithCurrency;
    String iban;
    String date;
    String description;
}
