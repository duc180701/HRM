package com.training.hrm.dto;

import jakarta.validation.constraints.NotNull;

public class EmployeeRequest {
    @NotNull(message = "Please enter a valid personnel id")
    private Long personnelID;

    @NotNull(message = "Please enter a valid person id")
    private Long personID;

//    @NotNull(message = "Please enter a valid contract id")
    private Long contractID;

    public EmployeeRequest() {
    }

    public EmployeeRequest(Long personnelID, Long personID, Long contractID) {
        this.personnelID = personnelID;
        this.personID = personID;
        this.contractID = contractID;
    }

    public @NotNull(message = "Please enter a valid personnel id") Long getPersonnelID() {
        return personnelID;
    }

    public void setPersonnelID(@NotNull(message = "Please enter a valid personnel id") Long personnelID) {
        this.personnelID = personnelID;
    }

    public @NotNull(message = "Please enter a valid person id") Long getPersonID() {
        return personID;
    }

    public void setPersonID(@NotNull(message = "Please enter a valid person id") Long personID) {
        this.personID = personID;
    }

    public Long getContractID() {
        return contractID;
    }

    public void setContractID(Long contractID) {
        this.contractID = contractID;
    }
}
