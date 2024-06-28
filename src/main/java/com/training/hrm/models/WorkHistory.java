package com.training.hrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "workhistories")
public class WorkHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workHistoryID;

    @Column(name = "employeeid")
    @NotNull
    @Pattern(regexp = "^[0-9]+$", message = "Employee ID must contain numbers")
    private Long employeeID;

    @Column(name = "position")
    @NotBlank(message = "Please enter a valid position")
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

    @Column(name = "reason")
    @NotBlank(message = "Reason is not empty")
    private String reason;

    public WorkHistory() {

    }

    public WorkHistory(Long workHistoryID, Long employeeID, String position, String department, LocalDate startDate, LocalDate endDate, String reason) {
        this.workHistoryID = workHistoryID;
        this.employeeID = employeeID;
        this.position = position;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    public Long getWorkHistoryID() {
        return workHistoryID;
    }

    public void setWorkHistoryID(Long workHistoryID) {
        this.workHistoryID = workHistoryID;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
