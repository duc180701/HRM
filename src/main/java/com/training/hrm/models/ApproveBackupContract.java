package com.training.hrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "approve_backup_contract")
public class ApproveBackupContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approveBackupContractID;

    @Column(name = "contract_id")
    private Long contractID;

    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "salary")
    @NotNull(message = "Please enter a valid salary")
    private Long salary;

    @Column(name = "start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @Column(name = "end_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Column(name = "reason")
    @NotBlank(message = "Please enter valid reason")
    private String reason;

    @Column(name = "approve")
    private boolean approve;

    public ApproveBackupContract() {
    }

    public ApproveBackupContract(Long approveBackupContractID, Long contractID, String contractType, Long salary, LocalDate startDate, LocalDate endDate, LocalDate date, String reason, boolean approve) {
        this.approveBackupContractID = approveBackupContractID;
        this.contractID = contractID;
        this.contractType = contractType;
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.date = date;
        this.reason = reason;
        this.approve = approve;
    }

    public Long getApproveBackupContractID() {
        return approveBackupContractID;
    }

    public void setApproveBackupContractID(Long approveBackupContractID) {
        this.approveBackupContractID = approveBackupContractID;
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

    public @NotNull(message = "Please enter a valid salary") Long getSalary() {
        return salary;
    }

    public void setSalary(@NotNull(message = "Please enter a valid salary") Long salary) {
        this.salary = salary;
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

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }
}
