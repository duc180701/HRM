package com.training.hrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "backup_personnel_position")
public class BackupPersonnelPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backupPersonnelPositionID;

    @Column(name = "employee_id")
    private Long employeeID;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "position")
    private String position;

    @Column(name = "department")
    private String department;

    @Column(name = "status")
    private String status;

    @Column(name = "reason")
    @NotBlank(message = "Please enter valid reason")
    private String reason;

    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public BackupPersonnelPosition() {
    }

    public BackupPersonnelPosition(Long backupPersonnelPositionID, Long employeeID, String fullName, String position, String department, String status, String reason, LocalDate date) {
        this.backupPersonnelPositionID = backupPersonnelPositionID;
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.position = position;
        this.department = department;
        this.status = status;
        this.reason = reason;
        this.date = date;
    }

    public Long getBackupPersonnelPositionID() {
        return backupPersonnelPositionID;
    }

    public void setBackupPersonnelPositionID(Long backupPersonnelPositionID) {
        this.backupPersonnelPositionID = backupPersonnelPositionID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public @NotBlank(message = "Please enter valid reason") String getReason() {
        return reason;
    }

    public void setReason(@NotBlank(message = "Please enter valid reason") String reason) {
        this.reason = reason;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
