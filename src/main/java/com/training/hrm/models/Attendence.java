package com.training.hrm.models;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendences")
public class Attendence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendenceID;

    @Column(name = "employee_id")
    private Long employeeID;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    public Attendence() {
    }

    public Attendence(Long attendenceID, Long employeeID, LocalDate date, LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        this.attendenceID = attendenceID;
        this.employeeID = employeeID;
        this.date = date;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

    public Long getAttendenceID() {
        return attendenceID;
    }

    public void setAttendenceID(Long attendenceID) {
        this.attendenceID = attendenceID;
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

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
}
