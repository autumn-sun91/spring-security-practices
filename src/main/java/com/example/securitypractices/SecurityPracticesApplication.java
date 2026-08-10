package com.example.securitypractices;

import com.example.securitypractices.account.Account;
import com.example.securitypractices.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecurityPracticesApplication implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SecurityPracticesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("==================== init insert user data ====================");
        accountRepository.save(Account.create("user1", passwordEncoder.encode("123"), "USER"));
        accountRepository.save(Account.create("user2", passwordEncoder.encode("123"), "USER"));
        accountRepository.save(Account.create("user3", passwordEncoder.encode("123"), "USER"));
        accountRepository.save(Account.create("admin", passwordEncoder.encode("admin"), "ADMIN"));
        System.out.println("==================== finish insert user data ====================");
    }
}
