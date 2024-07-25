package com.training.hrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "backup_employee_contract")
public class BackupEmployeeContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backupEmployeeContractID;

    @Column(name = "employee_id")
    private Long employeeID;

    @Column(name = "contract_id")
    private Long contractID;

    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Column(name = "reason")
    @NotBlank(message = "Please enter valid reason")
    private String reason;

    public BackupEmployeeContract() {
    }

    public BackupEmployeeContract(Long backupEmployeeContractID, Long employeeID, Long contractID, LocalDate date, String reason) {
        this.backupEmployeeContractID = backupEmployeeContractID;
        this.employeeID = employeeID;
        this.contractID = contractID;
        this.date = date;
        this.reason = reason;
    }

    public Long getBackupEmployeeContractID() {
        return backupEmployeeContractID;
    }

    public void setBackupEmployeeContractID(Long backupEmployeeContractID) {
        this.backupEmployeeContractID = backupEmployeeContractID;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public Long getContractID() {
        return contractID;
    }

    public void setContractID(Long contractID) {
        this.contractID = contractID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public @NotBlank(message = "Please enter valid reason") String getReason() {
        return reason;
    }

    public void setReason(@NotBlank(message = "Please enter valid reason") String reason) {
        this.reason = reason;
    }
}
