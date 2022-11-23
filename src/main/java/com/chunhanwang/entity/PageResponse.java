package com.chunhanwang.entity;

import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {
    List<BankTransaction> monthlyTransactions;
    HashMap<String, Double> creditAndDebit;
}
