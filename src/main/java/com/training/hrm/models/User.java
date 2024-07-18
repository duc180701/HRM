package com.training.hrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(name = "employee_id")
    @NotNull(message = "Please enter a valid employee id")
    private Long employeeID;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Please enter a valid role")
    @Column(name = "role")
    private Role role;

    @Column(name = "user_name")
    @NotBlank(message = "Please enter a valid username")
    @Size(min = 8, message = "Username must be at least 8 characters long")
    @Pattern(regexp = "\\S+", message = "Username must not contain whitespace")
    private String username;

    @Column(name = "pass_word")
    @NotBlank(message = "Please enter a valid password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one special character, and one digit"
    )
    @Pattern(regexp = "\\S+", message = "Password must not contain whitespace")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    public enum Role {
        ADMIN,
        BAN_GIAM_DOC,
        TRUONG_PHONG,
        PHO_PHONG,
        NHAN_VIEN
    }

    public User() {
    }

    public User(Long userID, Long employeeID, Role role, String username, String password, String avatar) {
        this.userID = userID;
        this.employeeID = employeeID;
        this.role = role;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public @NotNull(message = "Please enter a valid employee id") Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(@NotNull(message = "Please enter a valid employee id") Long employeeID) {
        this.employeeID = employeeID;
    }

    public @NotNull(message = "Please enter a valid role") Role getRole() {
        return role;
    }

    public void setRole(@NotNull(message = "Please enter a valid role") Role role) {
        this.role = role;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
