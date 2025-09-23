package com.websecurity.app02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/h2-console/**").permitAll()
                .requestMatchers("/information-leakage/**").authenticated()
                .anyRequest().authenticated()
            )
            .httpBasic()
            .and()
            .csrf().disable()
            .headers().frameOptions().disable(); // For H2 console

        return http.build();
    }
}