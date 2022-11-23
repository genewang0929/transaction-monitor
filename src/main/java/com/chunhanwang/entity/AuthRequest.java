package com.chunhanwang.entity;

import lombok.*;

@Data
public class AuthRequest {
    private String password;
    private String iban;
}
