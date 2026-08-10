package com.example.securitypractices.config;

import com.password4j.Argon2Function;
import com.password4j.types.Argon2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.Argon2Password4jPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // 어떻게 인가 할것인지?
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/info", "/h2-console")
                        .permitAll()
                        .requestMatchers("/admin")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())
                .formLogin(Customizer.withDefaults()) // security formLogin 을 사용할것인지?
                .httpBasic(Customizer.withDefaults()); // 기본적인 인증 시스템을 사용

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2Password4jPasswordEncoder(Argon2Function.getInstance(
                65536, // Memory cost in KiB (64 MB)
                3, // Number of iterations
                4, // Parallelism (threads)
                32, // Hash length
                Argon2.ID // Argon2 type (Argon2id is best for general password hashing)
                ));
    }
}
