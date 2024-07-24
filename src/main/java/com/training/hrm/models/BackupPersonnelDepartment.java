package com.training.hrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "backup_personnel_department")
public class BackupPersonnelDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backup_personnel_department_ID;

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

    public BackupPersonnelDepartment() {
    }

    public BackupPersonnelDepartment(Long backup_personnel_department_ID, Long employeeID, String fullName, String position, String department, String status, String reason) {
        this.backup_personnel_department_ID = backup_personnel_department_ID;
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.position = position;
        this.department = department;
        this.status = status;
        this.reason = reason;
    }

    public Long getBackup_personnel_department_ID() {
        return backup_personnel_department_ID;
    }

    public void setBackup_personnel_department_ID(Long backup_personnel_department_ID) {
        this.backup_personnel_department_ID = backup_personnel_department_ID;
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
}
