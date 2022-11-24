package com.chunhanwang.controller;

import com.chunhanwang.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppUserController {
    @Autowired
    public AppUserService appUserService;

    @GetMapping("")
    public ResponseEntity<Object> getAllTransactions() {
        Map<String, Object> map = new HashMap<>();
        map.put("transaction", appUserService.getAllUsers());
        return ResponseEntity.ok(map);
    }

    @PostMapping("")
    public ResponseEntity<Object> generateTransaction() {
        appUserService.generateUsers();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteAllTransactions() {
        appUserService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }
}
