package com.training.hrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeID;

    @Column(name="fullname")
    @NotBlank (message = "Please enter a valid fullname")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Fullname must contain only uppercase and lowercase letters")
    private String fullName;

    @Column(name="gender")
    @NotNull
    private int gender;

    @Column(name="address")
    @NotBlank (message = "Please enter a valid address")
    private String address;

    @Column(name="dateofbirth")
    @Past(message = "Birth date must be in the past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    @Column(name="phonenumber")
    @Pattern(regexp = "^[0-9]+$", message = "Phonenumber must contain only numbers")
    @Pattern(regexp = "^0\\d{9}$", message = "Phonenumber must be 10 characters long and start with 0")
    private String phoneNumber;

    @Column(name="email")
    @Email(message = "Please enter a valid email")
    private String email;

    @Column(name="position")
    @NotBlank (message = "Please enter a valid position")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Position must contain only uppercase and lowercase letters")
    private String position;

    @Column(name="department")
    @NotBlank (message = "Please enter a valid department")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Department must contain only uppercase and lowercase letters")
    private String department;

    @Column(name="startdate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Column(name="enddate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Column(name="status")
    @NotNull
    private int status;

    public Employee() {

    }

    public Employee(Long employeeID, String fullName, int gender, String address, LocalDate dateOfBirth, String phoneNumber, String email, String position, String department, LocalDate startDate, LocalDate endDate, int status) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.position = position;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
