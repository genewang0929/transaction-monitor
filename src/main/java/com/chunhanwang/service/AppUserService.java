package com.chunhanwang.service;

import com.chunhanwang.entity.*;
import com.chunhanwang.repository.*;
import org.iban4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class AppUserService {
    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public AppUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public AppUser getUserByIban(String iban) { return userRepository.findByIban(iban); }

    public boolean hasExistByIban(String iban) { return userRepository.existsByIban(iban); }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void generateUsers() {
//        List<AppUser> users = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
            AppUser user = new AppUser();
            user.setIban(Iban.random().toString());
            user.setPassword(passwordEncoder.encode("0000"));
//            users.add(user);
//        }
        userRepository.save(user);

//        userRepository.saveAll(users);
    }
}
