package com.chunhanwang.controller;

import com.chunhanwang.entity.*;
import com.chunhanwang.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import java.util.*;

@RestController
@RequestMapping(value = "/verification", produces = MediaType.APPLICATION_JSON_VALUE)
public class JWTController {

    @Autowired
    private JWTService jwtService;
    @Autowired
    @Lazy
    private AppUserService appUserService;


    @Operation(summary = "Login", description = "error code 404 -> no such user 403 -> incorrect password")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> issueToken(@Valid @RequestBody AuthRequest request) {
        if (appUserService.hasExistByIban(request.getIban())) {
            String token = jwtService.generateToken(request);
            Map<String, Object> response = new HashMap<>(Collections.singletonMap("token", token));

            Map<String, Object> user = jwtService.parseToken(response.get("token").toString());

            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> res = new HashMap<>();
            res.put("msg", "not found this iban.");
            return ResponseEntity.status(404).body(res);
        }
    }

    @Operation(summary = "Authenticate users")
    @PostMapping("/parse")
    public ResponseEntity<Map<String, Object>> parseToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        Map<String, Object> response = jwtService.parseToken(token);

        return ResponseEntity.ok(response);
    }
}
