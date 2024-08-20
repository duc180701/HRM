package com.training.hrm.configs;

import com.training.hrm.customservices.CustomAccessDeniedHandler;
import com.training.hrm.customservices.CustomUserDetailsService;
import com.training.hrm.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
                                "/employee/**",
                                "/test/**",
                                "/contract/**",
                                "/user/**",
                                "/attendance/**",
                                "/swagger-ui/**",
                                "/v3/**",
                                "/user/login",
                                "/user/forgot-password/**",
                                "/user/create/**").permitAll()
                        .requestMatchers(
                                "/role/**",
                                "/attendance/create-by-machine-attendance/**").hasAuthority("HE_THONG")
                        .requestMatchers(
                                "/backup/read-backup-personnel-position/**",
                                "/backup/read-backup-personnel-department/**",
                                "/backup/read-backup-employee-contract/",
                                "/backup/read-backup-contract/**",
                                "/employee/read-all-approve-employee-contract/**"
                               ).hasAnyAuthority("HE_THONG", "ADMIN", "BAN_GIAM_DOC")
                        .requestMatchers(
                                "/recovery/**",
                                "/contract/approve-contract/{contractID)/**",
                                "/employee/approve-employee-contract-change/{approveEmployeeContractID}",
                                "/employee/approve-backup-employee/{approveBackupEmployeeID}"
                        ).hasAnyAuthority("BAN_GIAM_DOC", "HE_THONG")
                        .requestMatchers(
                                "/attendance/create-by-manually/**",
                                "/attendance/create-by-file/**").hasAnyAuthority("ADMIN", "HE_THONG")
                        .requestMatchers("/attendance/read-attendance-by-time").hasAnyAuthority("TRUONG_PHONG_NS", "PHO_PHONG_NS", "NHAN_VIEN_NS", "HE_THONG", "ADMIN")
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
                                "/user/read/**",
                                "/report/**",
                                "/attendance/create-attendance-table/**",
                                "/attendance/update-attendance/{attendanceID}",
                                "/attendance/read-all-approve-attendance/**",
                                "/attendance/update-approve-attendance/{approveAttendanceID}/**").hasAnyAuthority("TRUONG_PHONG_NS", "PHO_PHONG_NS", "NHAN_VIEN_NS", "HE_THONG")
                        .requestMatchers("/backup/approve-contract/**",
                                "/backup/approve-personnel-position/**").hasAnyAuthority("TRUONG_PHONG_NS", "PHO_PHONG_NS")
                        .requestMatchers("/attendance/approve-attendance-table/{month}").hasAnyAuthority("TRUONG_PHONG_NS", "PHO_PHONG_NS", "BAN_GIAM_DOC")
                        .requestMatchers("/backup/read-all-approve-contract/**",
                                "/backup/read-all-approve-personnel-position/**").hasAnyAuthority("TRUONG_PHONG_NS", "PHO_PHONG_NS",  "HE_THONG")
                        .requestMatchers(
                                "/employee/search/**",
                                "/employee/read/**",
                                "/employee/read-all-employee/**",
                                "/employee/read-myself-employee/**",
                                "/person/read/**",
                                "/personnel/read/**",
                                "/contract/read/**",
                                "/user/avatar/**",
                                "/user/change-password").hasAnyAuthority("HE_THONG","ADMIN","GUEST","NHAN_VIEN","BAN_GIAM_DOC","TRUONG_PHONG_HC","PHO_PHONG_HC","NHAN_VIEN_HC","TRUONG_PHONG_NS","PHO_PHONG_NS","NHAN_VIEN_NS","TRUONG_PHONG_TB","PHO_PHONG_TB","NHAN_VIEN_TB","TRUONG_PHONG_TT","PHO_PHONG_TT","NHAN_VIEN_TT","TO_TRUONG_MEDIA","TO_PHO_MEDIA","NHAN_VIEN_MEDIA","TO_TRUONG_SK","TO_PHO_SK","NHAN_VIEN_SK","TRUONG_PHONG_CSKH","PHO_PHONG_CSKH","NHAN_VIEN_CSKH")
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
