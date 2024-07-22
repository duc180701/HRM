package com.training.hrm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ContractRequest {
    @NotBlank(message = "Please enter a valid contract type")
    private String contractType;

    @NotNull(message = "Please enter a valid salary")
    private Long salary;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Please enter a valid start date")
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Please enter a valid end date")
    private LocalDate endDate;

    public ContractRequest() {
    }

    public ContractRequest(String contractType, Long salary, LocalDate startDate, LocalDate endDate) {
        this.contractType = contractType;
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public @NotBlank(message = "Please enter a valid contract type") String getContractType() {
        return contractType;
    }

    public void setContractType(@NotBlank(message = "Please enter a valid contract type") String contractType) {
        this.contractType = contractType;
    }

    public @NotNull(message = "Please enter a valid salary") Long getSalary() {
        return salary;
    }

    public void setSalary(@NotNull(message = "Please enter a valid salary") Long salary) {
        this.salary = salary;
    }

    public @NotNull(message = "Please enter a valid start date") LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull(message = "Please enter a valid start date") LocalDate startDate) {
        this.startDate = startDate;
    }

    public @NotNull(message = "Please enter a valid end date") LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull(message = "Please enter a valid end date") LocalDate endDate) {
        this.endDate = endDate;
    }
}
