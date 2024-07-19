package com.training.hrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "backup_personnel")
public class BackupPersonnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backupPersonnelID;

    @Column(name = "personnel_id")
    private Long personnelID;

    @Column(name = "department")
    private String department;

    @Column(name = "position")
    private String position;

    @Column(name = "reason")
    @NotBlank(message = "Please enter valid reason")
    private String reason;

    public BackupPersonnel() {
    }

    public BackupPersonnel(Long backupPersonnelID, Long personnelID, String department, String position, String reason) {
        this.backupPersonnelID = backupPersonnelID;
        this.personnelID = personnelID;
        this.department = department;
        this.position = position;
        this.reason = reason;
    }

    public Long getBackupPersonnelID() {
        return backupPersonnelID;
    }

    public void setBackupPersonnelID(Long backupPersonnelID) {
        this.backupPersonnelID = backupPersonnelID;
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

    public Long getPersonnelID() {
        return personnelID;
    }

    public void setPersonnelID(Long personnelID) {
        this.personnelID = personnelID;
    }

    public @NotBlank(message = "Please enter valid reason") String getReason() {
        return reason;
    }

    public void setReason(@NotBlank(message = "Please enter valid reason") String reason) {
        this.reason = reason;
    }
}