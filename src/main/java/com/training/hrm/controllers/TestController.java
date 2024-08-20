package com.training.hrm.controllers;

import com.training.hrm.components.JwtUtil;
import com.training.hrm.customservices.CustomUserDetails;
import com.training.hrm.exceptions.InvalidException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidAlgorithmParameterException;
import java.util.Collection;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/get-attribute-payload-jwt")
    public ResponseEntity<Object> getAttributePayloadJWT(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
            }

            String username = jwtUtil.extractUsername(token);
            String userID = jwtUtil.extractUserID(token);
            String employeeID = jwtUtil.extractEmployeeID(token);
            String role = jwtUtil.extractRole(token);

            System.out.println("Username: " + username);
            System.out.println("UserID: " + userID);
            System.out.println("EmployeeID: " + employeeID);
            System.out.println("Role: " + role);

            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
