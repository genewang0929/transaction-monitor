package com.chunhanwang.controller;

import com.chunhanwang.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class BankTransactionController {
    public BankTransactionService transactionService;

    @Autowired
    public BankTransactionController(BankTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getTransactionByDate() {
        Map<String, Object> map = new HashMap<>();
        map.put("transaction", transactionService.getTransactionsByUsers());
        return ResponseEntity.ok(map);
    }
}
