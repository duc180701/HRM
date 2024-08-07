package com.training.hrm.models;

import jakarta.persistence.*;

@Entity
@Table(name = "forgot_passwords")
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forgotPasswordID;

    @Column(name = "employee_id")
    private Long employeeID;

    @Column(name = "username")
    private String username;

    @Column(name = "status")
    private boolean status;

    public ForgotPassword() {
    }

    public ForgotPassword(Long forgotPasswordID, Long employeeID, String username, boolean status) {
        this.forgotPasswordID = forgotPasswordID;
        this.employeeID = employeeID;
        this.username = username;
        this.status = status;
    }

    public Long getForgotPasswordID() {
        return forgotPasswordID;
    }

    public void setForgotPasswordID(Long forgotPasswordID) {
        this.forgotPasswordID = forgotPasswordID;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
