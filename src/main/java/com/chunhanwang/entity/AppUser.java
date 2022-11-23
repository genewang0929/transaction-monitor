package com.chunhanwang.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@Document(collection = "users")
public class AppUser {
    String iban;
    String password;
}
