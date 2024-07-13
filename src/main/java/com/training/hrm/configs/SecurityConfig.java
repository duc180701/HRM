package com.training.hrm.configs;

import com.training.hrm.customservices.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http
                .csrf((csrf -> csrf.disable()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/home",
                                "/swagger-ui/**",
                                "/v3/**",
                                "/person/**",
                                "/personnel/**",
                                "/contract/**",
                                "/employee/**",
                                "/user/**").permitAll()
//                        .requestMatchers().hasRole("ADMIN")
//                        .requestMatchers().hasRole("BAN_GIAM_DOC")
//                        .requestMatchers().hasRole("TRUONG_PHONG")
//                        .requestMatchers().hasRole("PHO_PHONG")
//                        .requestMatchers().hasRole("NHAN_VIEN")
                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
