package com.training.hrm.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "approve_backup_employee")
public class ApproveBackupEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approveBackupEmployeeID;

    @Column(name = "employee_id")
    private Long employeeID;

    @Column(name = "person_id")
    private Long personID;

    @Column(name = "personnel_id")
    private Long personnelID;

    @Column(name = "old_contract_id")
    private Long oldContractID;

    @Column(name = "new_contract_id")
    private Long newContractID;

    @Column(name = "version")
    private Integer version;

    @Column(name = "approve_by")
    private String approveBy;

    @Column(name = "approve_date")
    private LocalDate approveDate;

    @Column(name = "status")
    private String status;

    public ApproveBackupEmployee() {
    }

    public ApproveBackupEmployee(Long approveBackupEmployeeID, Long employeeID, Long personID, Long personnelID, Long oldContractID, Long newContractID, Integer version, String approveBy, LocalDate approveDate, String status) {
        this.approveBackupEmployeeID = approveBackupEmployeeID;
        this.employeeID = employeeID;
        this.personID = personID;
        this.personnelID = personnelID;
        this.oldContractID = oldContractID;
        this.newContractID = newContractID;
        this.version = version;
        this.approveBy = approveBy;
        this.approveDate = approveDate;
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getApproveBackupEmployeeID() {
        return approveBackupEmployeeID;
    }

    public void setApproveBackupEmployeeID(Long approveBackupEmployeeID) {
        this.approveBackupEmployeeID = approveBackupEmployeeID;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public Long getPersonID() {
        return personID;
    }

    public void setPersonID(Long personID) {
        this.personID = personID;
    }

    public Long getPersonnelID() {
        return personnelID;
    }

    public void setPersonnelID(Long personnelID) {
        this.personnelID = personnelID;
    }

    public Long getOldContractID() {
        return oldContractID;
    }

    public void setOldContractID(Long oldContractID) {
        this.oldContractID = oldContractID;
    }

    public Long getNewContractID() {
        return newContractID;
    }

    public void setNewContractID(Long newContractID) {
        this.newContractID = newContractID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
