package com.training.hrm.recoveries;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "recovery_contracts")
public class RecoveryContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recoveryContractID;

    @Column(name = "contract_id")
    private Long contractID;

    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "salary")
    private Long salary;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public RecoveryContract() {
    }

    public RecoveryContract(Long recoveryContractID, Long contractID, String contractType, Long salary, LocalDate startDate, LocalDate endDate) {
        this.recoveryContractID = recoveryContractID;
        this.contractID = contractID;
        this.contractType = contractType;
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getContractID() {
        return contractID;
    }

    public void setContractID(Long contractID) {
        this.contractID = contractID;
    }

    public Long getRecoveryContractID() {
        return recoveryContractID;
    }

    public void setRecoveryContractID(Long recoveryContractID) {
        this.recoveryContractID = recoveryContractID;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
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
}
