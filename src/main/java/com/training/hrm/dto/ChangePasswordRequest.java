package com.training.hrm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.security.SecureRandom;

public class ChangePasswordRequest {

    @NotBlank(message = "Please enter a valid old password")
    private String oldPassword;

    @NotBlank(message = "Please enter a valid password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one special character, and one digit"
    )
    @Pattern(regexp = "\\S+", message = "Password must not contain whitespace")
    private String password;

    @NotBlank(message = "Please enter a valid password")
    private String retypePassword;

//    private String captcha = generateCaptcha();

//    @NotBlank(message = "Please enter a valid captcha")
//    private String inputCaptcha;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String password, String retypePassword) {
        this.password = password;
        this.retypePassword = retypePassword;
    }

    public @NotBlank(message = "Please enter a valid password") @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one special character, and one digit"
    ) @Pattern(regexp = "\\S+", message = "Password must not contain whitespace") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Please enter a valid password") @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one special character, and one digit"
    ) @Pattern(regexp = "\\S+", message = "Password must not contain whitespace") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Please enter a valid password") String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(@NotBlank(message = "Please enter a valid password") String retypePassword) {
        this.retypePassword = retypePassword;
    }

    public @NotBlank(message = "Please enter a valid old password") String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(@NotBlank(message = "Please enter a valid old password") String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String generateCaptcha() {
        // Sinh tự động mã captcha
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final int length = 6;
        final SecureRandom RANDOM = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(length);
        for(int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }
}
