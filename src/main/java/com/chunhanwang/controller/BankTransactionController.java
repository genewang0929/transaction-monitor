package com.chunhanwang.controller;

import com.chunhanwang.entity.*;
import com.chunhanwang.service.*;
import io.swagger.v3.oas.annotations.*;
import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/bankTransaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class BankTransactionController {
    @Autowired
    public BankTransactionService bankTransactionService;

    @Operation(summary = "Get all transactions")
    @GetMapping("")
    public ResponseEntity<Object> getAllTransactions() {
        Map<String, Object> map = new HashMap<>();
        map.put("transactions", bankTransactionService.getAllTransactions());
        return ResponseEntity.ok(map);
    }

    @Operation(summary = "Get a user's monthly transactions")
    @GetMapping("/monthlyTransactions/{year}/{month}/{offset}/{pageSize}")
    public ResponseEntity<Object> getMonthlyTransactions(@PathVariable("year") int year,
                                                         @PathVariable("month") int month,
                                                         @PathVariable("offset") int offset,
                                                         @PathVariable("pageSize") int pageSize) {
        Map<String, Object> map = new HashMap<>();
        JSONObject monthlyRate = bankTransactionService.getMonthlyRate(year, month);
        PageResponse getMonthlyTransactions = bankTransactionService.getMonthlyTransactions(monthlyRate, offset, pageSize);
        map.put("transactions", getMonthlyTransactions);
        return ResponseEntity.ok(map);
    }

    @Operation(summary = "Insert 1200 transactions")
    @PostMapping("")
    public ResponseEntity<Object> generateTransaction() {
        bankTransactionService.generateTransactionsByUsers();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete all transactions")
    @DeleteMapping("")
    public ResponseEntity<Object> deleteAllTransactions() {
        bankTransactionService.deleteAllTransactions();
        return ResponseEntity.noContent().build();
    }
}
