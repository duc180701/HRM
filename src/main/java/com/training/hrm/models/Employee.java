package com.training.hrm.models;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeID;

    @Column(name = "personnel_id")
    private Long personnelID;

    @Column(name = "person_id")
    private Long personID;

    @Column(name = "contract_id")
    private Long contractID;

    public Employee() {
    }

    public Employee(Long employeeID, Long personnelID, Long personID, Long contractID) {
        this.employeeID = employeeID;
        this.personnelID = personnelID;
        this.personID = personID;
        this.contractID = contractID;
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
