package com.training.hrm.recoveries;

import jakarta.persistence.*;

@Entity
@Table(name = "recovery_employee")
public class RecoveryEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recoveryEmployeeID;

    @Column(name = "employee_id")
    private Long employeeID;

    @Column(name = "personnel_id")
    private Long personnelID;

    @Column(name = "person_id")
    private Long personID;

    @Column(name = "contract_id")
    private Long contractID;

    public RecoveryEmployee() {
    }

    public RecoveryEmployee(Long recoveryEmployeeID, Long employeeID, Long personnelID, Long personID, Long contractID) {
        this.recoveryEmployeeID = recoveryEmployeeID;
        this.employeeID = employeeID;
        this.personnelID = personnelID;
        this.personID = personID;
        this.contractID = contractID;
    }

    public Long getRecoveryEmployeeID() {
        return recoveryEmployeeID;
    }

    public void setRecoveryEmployeeID(Long recoveryEmployeeID) {
        this.recoveryEmployeeID = recoveryEmployeeID;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public Long getPersonnelID() {
        return personnelID;
    }

    public void setPersonnelID(Long personnelID) {
        this.personnelID = personnelID;
    }

    public Long getPersonID() {
        return personID;
    }

    public void setPersonID(Long personID) {
        this.personID = personID;
    }

    public Long getContractID() {
        return contractID;
    }

    public void setContractID(Long contractID) {
        this.contractID = contractID;
    }
}
