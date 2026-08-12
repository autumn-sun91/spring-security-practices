package com.example.securitypractices.config;

import com.password4j.Argon2Function;
import com.password4j.types.Argon2;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.security.autoconfigure.web.StaticResourceLocation;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.Argon2Password4jPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 5버전에는 AccessDecisionManager 로 관리
    // 6버전 이후에는 내부적으로 AuthorizationManager 가 관리
    // beam 으로 등록 시 내부적으로 권한 체크 시 roleHierarchy 도 같이 검사
    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER");
    }

    // SecurityFilterChain 은 spring security 내부 동작에서 FilterChainProxy 객체에서 filter 를 순회하면서 아래 설정한 값을 적용
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // 어떻게 인가 할것인지?
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .dispatcherTypeMatchers(DispatcherType.ERROR)
                        .permitAll()
                        .requestMatchers("/", "/info", "/h2-console", "/signup")
                        .permitAll()
                        .requestMatchers("/admin")
                        .hasRole("ADMIN")
                        .requestMatchers("/user")
                        .hasRole("USER")
                        .anyRequest()
                        .authenticated())
                .formLogin(Customizer.withDefaults()) // security formLogin 을 사용할것인지?
                .httpBasic(Customizer.withDefaults()); // 기본적인 인증 시스템을 사용

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web ->
                web.ignoring().requestMatchers(PathRequest.toStaticResources().at(StaticResourceLocation.FAVICON)));
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
