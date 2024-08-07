package com.training.hrm.dto;

import jakarta.validation.constraints.NotBlank;

public class ForgotPasswordRequest {

    @NotBlank(message = "Please enter a valid employee ID")
    private String employeeID;

    @NotBlank(message = "Please enter a valid username")
    private String username;

    @NotBlank(message = "Please enter a valid phone number")
    private String internalPhoneNumber;

    @NotBlank(message = "Please enter a valid citizenIdentityID")
    private String citizenIdentityID;

    public ForgotPasswordRequest() {
    }

    public ForgotPasswordRequest(String employeeID, String username, String internalPhoneNumber, String citizenIdentityID) {
        this.employeeID = employeeID;
        this.username = username;
        this.internalPhoneNumber = internalPhoneNumber;
        this.citizenIdentityID = citizenIdentityID;
    }

    public @NotBlank(message = "Please enter a valid employee ID") String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(@NotBlank(message = "Please enter a valid employee ID") String employeeID) {
        this.employeeID = employeeID;
    }

    public @NotBlank(message = "Please enter a valid username") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Please enter a valid username") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Please enter a valid phone number") String getInternalPhoneNumber() {
        return internalPhoneNumber;
    }

    public void setInternalPhoneNumber(@NotBlank(message = "Please enter a valid phone number") String internalPhoneNumber) {
        this.internalPhoneNumber = internalPhoneNumber;
    }

    public @NotBlank(message = "Please enter a valid citizenIdentityID") String getCitizenIdentityID() {
        return citizenIdentityID;
    }

    public void setCitizenIdentityID(@NotBlank(message = "Please enter a valid citizenIdentityID") String citizenIdentityID) {
        this.citizenIdentityID = citizenIdentityID;
    }
}
