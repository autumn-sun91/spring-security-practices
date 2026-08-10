package com.example.securitypractices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // 어떻게 인가 할것인지?
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/info")
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
    public UserDetailsService userDetailsService() {
        // authenticationManager 직접 쓰는거 대신
        // spring security 6 부터는 AuthenticationManager -> UserDetailsService 순으로 내부적으로 인증 흐름을 갖음.
        // authenticationManager 경우 custom 으로 사용 e.g.) JwtAuthenticationProvider
        return new InMemoryUserDetailsManager(
                // {noop} > spring security 에서 지정해놓은 패스워드 인코더를 사용하는 문법, noop 은 인코더 사용X
                // role 경우 내부적으로 ROLE_USER, ROLE_ADMIN 으로 지정됨.
                User.withUsername("thk").password("{noop}123").roles("USER").build(),
                User.withUsername("admin").password("{noop}!@#").roles("ADMIN").build());
    }
}
