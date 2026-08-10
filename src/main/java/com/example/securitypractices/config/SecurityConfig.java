package com.example.securitypractices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {


        // 어떻게 인가 할것인지?
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers("/", "/info").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
        )
        .formLogin(Customizer.withDefaults()) // security formLogin 을 사용할것인지?
        .httpBasic(Customizer.withDefaults()); // 기본적인 인증 시스템을 사용


        return http.build();
    }
}
