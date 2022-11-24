package com.chunhanwang.entity;

import lombok.*;

@Data
public class AuthRequest {
    private String iban;
    private String password;
}
