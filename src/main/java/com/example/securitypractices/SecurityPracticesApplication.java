package com.example.securitypractices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SecurityPracticesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityPracticesApplication.class, args);
    }
}
