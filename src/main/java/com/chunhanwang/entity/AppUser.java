package com.chunhanwang.entity;

import lombok.*;
import org.iban4j.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@Document(collection = "users")
public class AppUser {
    @Id
    String iban;
    String password;
}
