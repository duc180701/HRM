package com.training.hrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "approve_employee_contract")
public class ApproveEmployeeContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approveEmployeeContractID;

    @Column(name = "person_id")
    private Long personID;

    @Column(name = "personnel_id")
    private Long personnelID;

    @Column(name = "contractID")
    private Long contractID;

    @Column(name = "status")
    private String status;

    @Column(name = "approve_by")
    private String approveBy;

    @Column(name = "approve_date")
    private LocalDate approveDate;

    public ApproveEmployeeContract() {
    }

    public ApproveEmployeeContract(Long approveEmployeeContractID, Long personID, Long personnelID, Long contractID, String status, String approveBy, LocalDate approveDate) {
        this.approveEmployeeContractID = approveEmployeeContractID;
        this.personID = personID;
        this.personnelID = personnelID;
        this.contractID = contractID;
        this.status = status;
        this.approveBy = approveBy;
        this.approveDate = approveDate;
    }

    public Long getPersonID() {
        return personID;
    }

    public void setPersonID(Long personID) {
        this.personID = personID;
    }

    public Long getApproveEmployeeContractID() {
        return approveEmployeeContractID;
    }

    public void setApproveEmployeeContractID(Long approveEmployeeContractID) {
        this.approveEmployeeContractID = approveEmployeeContractID;
    }

    public Long getPersonnelID() {
        return personnelID;
    }

    public void setPersonnelID(Long personnelID) {
        this.personnelID = personnelID;
    }

    public Long getContractID() {
        return contractID;
    }

    public void setContractID(Long contractID) {
        this.contractID = contractID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(String approveBy) {
        this.approveBy = approveBy;
    }

    public LocalDate getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(LocalDate approveDate) {
        this.approveDate = approveDate;
    }
}
