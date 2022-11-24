package com.chunhanwang.controller;

import com.chunhanwang.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.security.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppUserController {
    @Autowired
    public AppUserService appUserService;
    @Autowired
    public JWTService jwtService;

    @Operation(summary = "Get all users")
    @GetMapping("")
    public ResponseEntity<Object> getAllUsers() {
        Map<String, Object> map = new HashMap<>();
        map.put("transaction", appUserService.getAllUsers());
        return ResponseEntity.ok(map);
    }

    @Operation(summary = "Insert 10 users")
    @PostMapping("")
    public ResponseEntity<Object> generateTransaction() {
        appUserService.generateUsers();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete all users")
    @DeleteMapping("")
    public ResponseEntity<Object> deleteAllTransactions() {
        appUserService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }
}
