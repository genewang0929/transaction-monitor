package com.chunhanwang;
import com.chunhanwang.entity.*;
import com.chunhanwang.repository.*;
import com.chunhanwang.service.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

@SpringBootApplication
public class SynpulseBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(SynpulseBackendApplication.class, args);
    }

    // Insert users and transactions
    @Bean
    CommandLineRunner runner(AppUserService appUserService, BankTransactionService bankTransactionService) {
        return args -> {
            appUserService.deleteAllUsers();
            bankTransactionService.deleteAllTransactions();
            appUserService.generateUsers();
            bankTransactionService.generateTransactionsByUsers();
        };
    }
}
