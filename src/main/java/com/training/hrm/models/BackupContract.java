package com.training.hrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class BackupContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backupContractID;

    @Column(name = "contract_id")
    private Long contractID;

    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Column(name = "end_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    public BackupContract() {
    }

    public BackupContract(Long backupContractID, Long contractID, String contractType, LocalDate startDate, LocalDate endDate) {
        this.backupContractID = backupContractID;
        this.contractID = contractID;
        this.contractType = contractType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getBackupContractID() {
        return backupContractID;
    }

    public void setBackupContractID(Long backupContractID) {
        this.backupContractID = backupContractID;
    }

    public Long getContractID() {
        return contractID;
    }

    public void setContractID(Long contractID) {
        this.contractID = contractID;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
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
}
