package com.training.hrm.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class MachineAttendanceRequest {

    @NotNull(message = "Please enter a valid employee id")
    private Long employeeID;

    @NotNull(message = "The timeStamp cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeStamp;

    public MachineAttendanceRequest() {
    }

    public MachineAttendanceRequest(Long employeeID, LocalDateTime timeStamp) {
        this.employeeID = employeeID;
        this.timeStamp = timeStamp;
    }

    public @NotNull(message = "Please enter a valid employee id") Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(@NotNull(message = "Please enter a valid employee id") Long employeeID) {
        this.employeeID = employeeID;
    }

    public @NotNull(message = "The date cannot be null") LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(@NotNull(message = "The date cannot be null") LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
