package com.training.hrm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PersonnelRequest {
    @NotBlank(message = "Please enter a valid level")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Level must contain only uppercase and lowercase letters")
    private String level;

    @NotBlank(message = "Please enter a valid education")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Education must contain only uppercase and lowercase letters")
    private String education;

    @NotBlank(message = "Please enter a valid graduation major")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Graduation major must contain only uppercase and lowercase letters")
    private String graduationMajor;

    @NotBlank(message = "Please enter a valid graduation school")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Graduation school must contain only uppercase and lowercase letters")
    private String graduationSchool;

    @NotNull(message = "Please enter a valid graduation year")
    private int graduationYear;

    @NotBlank(message = "Please enter a valid internal phone number")
    @Pattern(regexp = "^[0-9]+$", message = "Internal phone number must contain only numbers")
    @Pattern(regexp = "^0\\d{9}$", message = "Internal phone number must be 10 characters long and start with 0")
    private String internalPhoneNumber;

    @NotBlank(message = "Please enter a valid email")
    @Email(message = "Please enter a valid email")
    private String internalEmail;

    private String employeeAccount;

    @NotBlank(message = "Please enter a valid department")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Department must contain only uppercase and lowercase letters")
    private String department;

    @NotBlank(message = "Please enter a valid position")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Position must contain only uppercase and lowercase letters")
    private String position;

    @NotBlank(message = "Please enter a valid direct management staff")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Direct management staff must contain only uppercase and lowercase letters")
    private String directManagementStaff;

    @NotNull(message = "Please enter a valid status")
    private String status;

    public PersonnelRequest() {
    }

    public PersonnelRequest(String level, String education, String graduationMajor, String graduationSchool, int graduationYear, String internalPhoneNumber, String internalEmail, String employeeAccount, String department, String position, String directManagementStaff, String status) {
        this.level = level;
        this.education = education;
        this.graduationMajor = graduationMajor;
        this.graduationSchool = graduationSchool;
        this.graduationYear = graduationYear;
        this.internalPhoneNumber = internalPhoneNumber;
        this.internalEmail = internalEmail;
        this.employeeAccount = employeeAccount;
        this.department = department;
        this.position = position;
        this.directManagementStaff = directManagementStaff;
        this.status = status;
    }

    public @NotBlank(message = "Please enter a valid level") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Level must contain only uppercase and lowercase letters") String getLevel() {
        return level;
    }

    public void setLevel(@NotBlank(message = "Please enter a valid level") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Level must contain only uppercase and lowercase letters") String level) {
        this.level = level;
    }

    public @NotBlank(message = "Please enter a valid education") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Education must contain only uppercase and lowercase letters") String getEducation() {
        return education;
    }

    public void setEducation(@NotBlank(message = "Please enter a valid education") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Education must contain only uppercase and lowercase letters") String education) {
        this.education = education;
    }

    public @NotBlank(message = "Please enter a valid graduation major") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Graduation major must contain only uppercase and lowercase letters") String getGraduationMajor() {
        return graduationMajor;
    }

    public void setGraduationMajor(@NotBlank(message = "Please enter a valid graduation major") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Graduation major must contain only uppercase and lowercase letters") String graduationMajor) {
        this.graduationMajor = graduationMajor;
    }

    public @NotBlank(message = "Please enter a valid graduation school") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Graduation school must contain only uppercase and lowercase letters") String getGraduationSchool() {
        return graduationSchool;
    }

    public void setGraduationSchool(@NotBlank(message = "Please enter a valid graduation school") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Graduation school must contain only uppercase and lowercase letters") String graduationSchool) {
        this.graduationSchool = graduationSchool;
    }

    @NotNull(message = "Please enter a valid graduation year")
    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(@NotNull(message = "Please enter a valid graduation year") int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public @NotBlank(message = "Please enter a valid internal phone number") @Pattern(regexp = "^[0-9]+$", message = "Internal phone number must contain only numbers") @Pattern(regexp = "^0\\d{9}$", message = "Internal phone number must be 10 characters long and start with 0") String getInternalPhoneNumber() {
        return internalPhoneNumber;
    }

    public void setInternalPhoneNumber(@NotBlank(message = "Please enter a valid internal phone number") @Pattern(regexp = "^[0-9]+$", message = "Internal phone number must contain only numbers") @Pattern(regexp = "^0\\d{9}$", message = "Internal phone number must be 10 characters long and start with 0") String internalPhoneNumber) {
        this.internalPhoneNumber = internalPhoneNumber;
    }

    public @NotBlank(message = "Please enter a valid email") @Email(message = "Please enter a valid email") String getInternalEmail() {
        return internalEmail;
    }

    public void setInternalEmail(@NotBlank(message = "Please enter a valid email") @Email(message = "Please enter a valid email") String internalEmail) {
        this.internalEmail = internalEmail;
    }

    public String getEmployeeAccount() {
        return employeeAccount;
    }

    public void setEmployeeAccount(String employeeAccount) {
        this.employeeAccount = employeeAccount;
    }

    public @NotBlank(message = "Please enter a valid department") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Department must contain only uppercase and lowercase letters") String getDepartment() {
        return department;
    }

    public void setDepartment(@NotBlank(message = "Please enter a valid department") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Department must contain only uppercase and lowercase letters") String department) {
        this.department = department;
    }

    public @NotBlank(message = "Please enter a valid position") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Position must contain only uppercase and lowercase letters") String getPosition() {
        return position;
    }

    public void setPosition(@NotBlank(message = "Please enter a valid position") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Position must contain only uppercase and lowercase letters") String position) {
        this.position = position;
    }

    public @NotBlank(message = "Please enter a valid direct management staff") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Direct management staff must contain only uppercase and lowercase letters") String getDirectManagementStaff() {
        return directManagementStaff;
    }

    public void setDirectManagementStaff(@NotBlank(message = "Please enter a valid direct management staff") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Direct management staff must contain only uppercase and lowercase letters") String directManagementStaff) {
        this.directManagementStaff = directManagementStaff;
    }

    public @NotNull(message = "Please enter a valid status") String getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Please enter a valid status") String status) {
        this.status = status;
    }
}
