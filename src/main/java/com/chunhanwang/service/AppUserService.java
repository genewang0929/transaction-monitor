package com.chunhanwang.service;

import com.chunhanwang.entity.*;
import com.chunhanwang.repository.*;
import org.iban4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class AppUserService {
    @Autowired
    public UserRepository userRepository;

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void generateUsers() {
        List<AppUser> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AppUser user = new AppUser();
            user.setIban(Iban.random().toString());
            user.setPassword("0000");
            users.add(user);
        }

        userRepository.saveAll(users);
    }
}
