package com.training.hrm.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {
    @GetMapping("")
//    public ResponseEntity<Map<String, String>> home() {
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Welcome to HOME PAGE <3");
//
//        return ResponseEntity.ok(response);
//    }
    public ResponseEntity<?> home() {
        return ResponseEntity.ok("Welcome to HOME PAGE!");
    }
}
