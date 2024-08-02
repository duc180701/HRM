package com.training.hrm.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ManuallyAttendanceRequest {

    @NotNull(message = "Please enter a valid employee id")
    private Long employeeID;

    @NotNull(message = "Please enter a valid date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    private String checkInTime;

    @DateTimeFormat(pattern = "HH:mm")
    private String checkOutTime;

    public ManuallyAttendanceRequest() {
    }

    public ManuallyAttendanceRequest(Long employeeID, LocalDate date, String checkInTime, String checkOutTime) {
        this.employeeID = employeeID;
        this.date = date;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

    public @NotNull(message = "Please enter a valid employee id") Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(@NotNull(message = "Please enter a valid employee id") Long employeeID) {
        this.employeeID = employeeID;
    }

    public @NotNull(message = "Please enter a valid date") LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull(message = "Please enter a valid date") LocalDate date) {
        this.date = date;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
}
