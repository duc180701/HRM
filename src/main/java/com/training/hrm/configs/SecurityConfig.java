package com.training.hrm.configs;

import com.training.hrm.customservices.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
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
                                "/user/**",
                                "/backup/**",
                                "/report/**",
                                "/attendance/**",
                                "/role/**").permitAll()
//                        .requestMatchers().hasRole("ADMIN")
//                        .requestMatchers().hasRole("BAN_GIAM_DOC")
//                        .requestMatchers().hasRole("TRUONG_PHONG")
//                        .requestMatchers().hasRole("PHO_PHONG")
//                        .requestMatchers().hasRole("NHAN_VIEN")
                        .anyRequest().authenticated())
                .sessionManagement(sessionManagement ->
                        // Ứng dụng không duy trì session của người dùng
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin((formLogin) -> formLogin
                        .permitAll())
                .logout((logout) -> logout
                        .permitAll()
                );

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
