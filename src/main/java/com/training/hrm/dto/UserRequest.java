package com.training.hrm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRequest {

    @NotNull(message = "Please enter a valid employee id")
    private Long employeeID;

    @NotBlank(message = "Please enter a valid username")
    @Size(min = 8, message = "Username must be at least 8 characters long")
    @Pattern(regexp = "\\S+", message = "Username must not contain whitespace")
    private String username;

    @NotBlank(message = "Please enter a valid password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one special character, and one digit"
    )
    @Pattern(regexp = "\\S+", message = "Password must not contain whitespace")
    private String password;

    @NotBlank(message = "Please enter a valid role")
    private String role;

    public UserRequest() {
    }

    public UserRequest(Long employeeID, String username, String password, String role) {
        this.employeeID = employeeID;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public @NotNull(message = "Please enter a valid employee id") Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(@NotNull(message = "Please enter a valid employee id") Long employeeID) {
        this.employeeID = employeeID;
    }

    public @NotBlank(message = "Please enter a valid username") @Size(min = 8, message = "Username must be at least 8 characters long") @Pattern(regexp = "\\S+", message = "Username must not contain whitespace") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Please enter a valid username") @Size(min = 8, message = "Username must be at least 8 characters long") @Pattern(regexp = "\\S+", message = "Username must not contain whitespace") String username) {
        this.username = username;
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

    public @NotBlank(message = "Please enter a valid role") String getRole() {
        return role;
    }

    public void setRole(@NotBlank(message = "Please enter a valid role") String role) {
        this.role = role;
    }
}
