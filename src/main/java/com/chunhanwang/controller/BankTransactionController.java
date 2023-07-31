package com.chunhanwang.controller;

import com.chunhanwang.entity.*;
import com.chunhanwang.service.*;
import io.swagger.v3.oas.annotations.*;
import org.json.*;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/bankTransaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class BankTransactionController {
    public final BankTransactionService bankTransactionService;

    public BankTransactionController(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    @Operation(summary = "Get a user's transactions")
    @GetMapping("/{userIban}/{offset}/{pageSize}")
    public ResponseEntity<Object> getTransactionsByIban(@PathVariable("userIban") String iban,
                                                        @PathVariable("offset") int offset,
                                                        @PathVariable("pageSize") int pageSize) {
        Map<String, Object> map = new HashMap<>();
        Page getTransactionsByIban = bankTransactionService.getTransactionsByIban(iban, offset, pageSize);
        map.put("transactions", getTransactionsByIban);
        return ResponseEntity.ok(map);
    }

    @Operation(summary = "Get a user's monthly transactions")
    @GetMapping("/{userIban}/{year}/{month}/{offset}/{pageSize}")
    public ResponseEntity<Object> getMonthlyTransactions(@PathVariable("userIban") String iban,
                                                         @PathVariable("year") int year,
                                                         @PathVariable("month") int month,
                                                         @PathVariable("offset") int offset,
                                                         @PathVariable("pageSize") int pageSize) {
        Map<String, Object> map = new HashMap<>();
        JSONObject monthlyRate = bankTransactionService.getMonthlyRate(year, month);
        PageResponse getMonthlyTransactions = bankTransactionService.getMonthlyTransactions(iban, monthlyRate, offset, pageSize);
        map.put("transactions", getMonthlyTransactions);
        return ResponseEntity.ok(map);
    }

    @Operation(summary = "Insert 10 transactions to the user's account")
    @PostMapping("")
    public ResponseEntity<Object> generateTransaction(String iban) {
        bankTransactionService.generateTenTransactionsByUsers(iban);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete all transactions")
    @DeleteMapping("")
    public ResponseEntity<Object> deleteAllTransactions() {
        bankTransactionService.deleteAllTransactions();
        return ResponseEntity.noContent().build();
    }
}
