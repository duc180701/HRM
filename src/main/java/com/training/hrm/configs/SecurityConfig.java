package com.training.hrm.configs;

import com.training.hrm.customservices.CustomAccessDeniedHandler;
import com.training.hrm.customservices.CustomUserDetailsService;
import com.training.hrm.repositories.RoleRepository;
import com.training.hrm.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        // Lấy tất cả các quyền có trong hệ thống
        List<String> roles = roleRepository.findAllRoleNames();
        String rolesString = String.join("','", roles);
        String accessExpressionWithQuotes = rolesString.replace("'", "\"");
        String accessExpression = "\"" + accessExpressionWithQuotes + "\"";
        System.out.println(accessExpression);

        http
                .csrf((csrf -> csrf.disable()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/**",
                                "/user/login",
                                "/user/forgot-password/**",
                                "/user/change-password",
                                "/backup/**",
                                "/report/**",
                                "/user/create/**",
                                "/attendance/**").permitAll()
                        .requestMatchers("/role/**").hasAuthority("HE_THONG")
                        .requestMatchers(
                                "/employee/update/**",
                                "/employee/delete/**",
                                "/employee/create/**",
                                "/person/create/**",
                                "/person/update/**",
                                "/person/delete/**",
                                "/personnel/create/**",
                                "/personnel/update/**",
                                "/personnel/delete/**",
                                "/contract/create/**",
                                "/contract/update/**",
                                "/contract/delete/**",
                                "/employee/filter/**",
                                "/user/delete/**",
                                "/user/read/**"
                                ).hasAnyAuthority("TRUONG_PHONG_NS", "PHO_PHONG_NS", "NHAN_VIEN_NS", "HE_THONG")
                        .requestMatchers("/backup/approve-contract/**",
                                "/backup/read-all-approve-contract/**",
                                "/backup/read-all-approve-personnel-position/**",
                                "/backup/approve-personnel-position/**").hasAnyAuthority("TRUONG_PHONG_NS", "PHO_PHONG_NS", "HE_THONG")
                        .requestMatchers("/backup/read-backup-contract/**").hasAnyAuthority("ADMIN", "HE_THONG", "BAN_GIAM_DOC", "TRUONG_PHONG_NS", "PHO_PHONG_NS", "NHAN_VIEN_NS")
                        .requestMatchers(
                                "/employee/search/**",
                                "/employee/read/**",
                                "/person/read/**",
                                "/personnel/read/**",
                                "/contract/read/**",
                                "/user/avatar/**").hasAnyAuthority("HE_THONG","ADMIN","GUEST","NHAN_VIEN","BAN_GIAM_DOC","TRUONG_PHONG_HC","PHO_PHONG_HC","NHAN_VIEN_HC","TRUONG_PHONG_NS","PHO_PHONG_NS","NHAN_VIEN_NS","TRUONG_PHONG_TB","PHO_PHONG_TB","NHAN_VIEN_TB","TRUONG_PHONG_TT","PHO_PHONG_TT","NHAN_VIEN_TT","TO_TRUONG_MEDIA","TO_PHO_MEDIA","NHAN_VIEN_MEDIA","TO_TRUONG_SK","TO_PHO_SK","NHAN_VIEN_SK","TRUONG_PHONG_CSKH","PHO_PHONG_CSKH","NHAN_VIEN_CSKH")
                        .anyRequest().authenticated())
                .sessionManagement(sessionManagement ->
                        // Ứng dụng không duy trì session của người dùng
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin((formLogin) -> formLogin
                        .permitAll())
                .logout((logout) -> logout
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedHandler(accessDeniedHandler)
                );;

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
