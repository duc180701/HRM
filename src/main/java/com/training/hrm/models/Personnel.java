package com.training.hrm.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "personnels")
public class Personnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personnelID;

    @Column(name = "level")
    @NotBlank(message = "Please enter a valid level")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Level must contain only uppercase and lowercase letters")
    private String level;

    @Column(name = "education")
    @NotBlank(message = "Please enter a valid education")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Education must contain only uppercase and lowercase letters")
    private String education;

    @Column(name = "graduation_major")
    @NotBlank(message = "Please enter a valid graduation major")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Graduation major must contain only uppercase and lowercase letters")
    private String graduationMajor;

    @Column(name = "graduation_school")
    @NotBlank(message = "Please enter a valid graduation school")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Graduation school must contain only uppercase and lowercase letters")
    private String graduationSchool;

    @Column(name = "graduation_ year")
    @NotNull(message = "Please enter a valid graduation year")
    private int graduationYear;

    @Column(name = "internal_phone_number")
    @NotBlank(message = "Please enter a valid internal phone number")
    @Pattern(regexp = "^[0-9]+$", message = "Internal phone number must contain only numbers")
    @Pattern(regexp = "^0\\d{9}$", message = "Internal phone number must be 10 characters long and start with 0")
    private String internalPhoneNumber;

    @Column(name = "internal_email")
    @NotBlank(message = "Please enter a valid email")
    @Email(message = "Please enter a valid email")
    private String internalEmail;

    @Column(name = "employee_id")
    @NotBlank(message = "Please enter a valid employee id")
    private String employeeID;

    @Column(name = "employee_account")
    private String employeeAccount;

    @Column(name = "department")
    @NotBlank(message = "Please enter a valid department")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Department must contain only uppercase and lowercase letters")
    private String department;

    @Column(name = "position")
    @NotBlank(message = "Please enter a valid position")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Position must contain only uppercase and lowercase letters")
    private String position;

    @Column(name = "direct_management_staff")
    @NotBlank(message = "Please enter a valid direct management staff")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Direct management staff must contain only uppercase and lowercase letters")
    private String directManagementStaff;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull(message = "Please enter a valid status")
    private Status status;

    public Personnel() {
    }

    public Personnel(Long personnelID, String level, String education, String graduationMajor, String graduationSchool, int graduationYear, String internalPhoneNumber, String internalEmail, String employeeID, String employeeAccount, String department, String position, String directManagementStaff, Status status) {
        this.personnelID = personnelID;
        this.level = level;
        this.education = education;
        this.graduationMajor = graduationMajor;
        this.graduationSchool = graduationSchool;
        this.graduationYear = graduationYear;
        this.internalPhoneNumber = internalPhoneNumber;
        this.internalEmail = internalEmail;
        this.employeeID = employeeID;
        this.employeeAccount = employeeAccount;
        this.department = department;
        this.position = position;
        this.directManagementStaff = directManagementStaff;
        this.status = status;
    }

    public Long getPersonnelID() {
        return personnelID;
    }

    public void setPersonnelID(Long personnelID) {
        this.personnelID = personnelID;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGraduationMajor() {
        return graduationMajor;
    }

    public void setGraduationMajor(String graduationMajor) {
        this.graduationMajor = graduationMajor;
    }

    public String getGraduationSchool() {
        return graduationSchool;
    }

    public void setGraduationSchool(String graduationSchool) {
        this.graduationSchool = graduationSchool;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getInternalPhoneNumber() {
        return internalPhoneNumber;
    }

    public void setInternalPhoneNumber(String internalPhoneNumber) {
        this.internalPhoneNumber = internalPhoneNumber;
    }

    public String getInternalEmail() {
        return internalEmail;
    }

    public void setInternalEmail(String internalEmail) {
        this.internalEmail = internalEmail;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeAccount() {
        return employeeAccount;
    }

    public void setEmployeeAccount(String employeeAccount) {
        this.employeeAccount = employeeAccount;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDirectManagementStaff() {
        return directManagementStaff;
    }

    public void setDirectManagementStaff(String directManagementStaff) {
        this.directManagementStaff = directManagementStaff;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        DANG_LAM_VIEC,
        NGHI_CHE_DO,
        NGHI_THAI_SAN
    }
}
