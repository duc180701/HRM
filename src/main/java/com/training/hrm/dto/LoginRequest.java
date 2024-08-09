package com.training.hrm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Please enter a valid username")
    private String username;

    @NotBlank(message = "Please enter a valid password")
    private String password;

    private String verificationCode;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password, String verificationCode) {
        this.username = username;
        this.password = password;
        this.verificationCode = verificationCode;
    }

    public @NotBlank(message = "Please enter a valid username") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Please enter a valid username") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Please enter a valid password") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Please enter a valid password") String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
