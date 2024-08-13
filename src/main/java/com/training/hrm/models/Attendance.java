package com.training.hrm.models;

import jakarta.persistence.*;
import lombok.extern.apachecommons.CommonsLog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "attendances")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceID;

    @Column(name = "employee_id")
    private Long employeeID;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "work_hours")
    private LocalTime workHour;

    @Column(name = "check_in_time")
    private LocalTime checkInTime;

    @Column(name = "check_out_time")
    private LocalTime checkOutTime;

    @Column(name = "note")
    private String note;

    @Column(name = "used")
    private boolean used;

    public Attendance() {
    }

    public Attendance(Long attendanceID, Long employeeID, LocalDate date, LocalTime workHour, LocalTime checkInTime, LocalTime checkOutTime, String note, boolean used) {
        this.attendanceID = attendanceID;
        this.employeeID = employeeID;
        this.date = date;
        this.workHour = workHour;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.note = note;
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getWorkHour() {
        return workHour;
    }

    public void setWorkHour(LocalTime workHour) {
        this.workHour = workHour;
    }

    public LocalTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
