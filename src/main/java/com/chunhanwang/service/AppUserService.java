package com.chunhanwang.service;

import com.chunhanwang.entity.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class AppUserService {
    public List<AppUser> getUsersByIban() {
        List<AppUser> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AppUser user = new AppUser();
            user.setIban(UUID.randomUUID().toString());
            user.setPassword("1111");
            users.add(user);
        }
        return users;
    }
}
