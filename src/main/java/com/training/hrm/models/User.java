package com.training.hrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

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

    public enum Role {
        ADMIN,
        BAN_GIAM_DOC,
        TRUONG_PHONG,
        PHO_PHONG,
        NHAN_VIEN
    }

    public User() {
    }

    public User(Long userID, Long employeeID, Role role, String username, String password) {
        this.userID = userID;
        this.employeeID = employeeID;
        this.role = role;
        this.username = username;
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userID, user.userID) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                role == user.role &&
                Objects.equals(employeeID, user.employeeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, username, password, role, employeeID);
    }
}
