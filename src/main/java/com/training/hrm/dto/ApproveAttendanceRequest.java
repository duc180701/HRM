package com.training.hrm.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

public class ApproveAttendanceRequest {
    @Column(name = "attendance_id")
    private Long attendanceID;

    @Column(name = "employee_id")
    private Long employeeID;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotBlank(message = "Please enter a valid end date")
    @Column(name = "date")
    private String date;

    @Column(name = "check_in_time")
    private String checkInTime;

    @Column(name = "check_out_time")
    private String checkOutTime;

    public ApproveAttendanceRequest() {
    }

    public ApproveAttendanceRequest(Long attendanceID, Long employeeID, String date, String checkInTime, String checkOutTime) {
        this.attendanceID = attendanceID;
        this.employeeID = employeeID;
        this.date = date;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

    public Long getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(Long attendanceID) {
        this.attendanceID = attendanceID;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
