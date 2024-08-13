package com.training.hrm.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "approve_attendance")
public class ApproveAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approveAttendanceID;

    @Column(name = "attendance_id")
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

    @Column(name = "approve_first_by")
    private String approveFirstBy;

    @Column(name = "approve_final_by")
    private String approveFinalBy;

    @Column(name = "approve_status")
    private String approveStatus;

    @Column(name = "approve_first_date")
    private LocalDate approveFirstDate;

    @Column(name = "approve_second_date")
    private LocalDate approveSecondDate;

    public ApproveAttendance() {
    }

    public ApproveAttendance(Long approveAttendanceID, Long attendanceID, Long employeeID, LocalDate date, LocalTime workHour, LocalTime checkInTime, LocalTime checkOutTime, String note, String approveFirstBy, String approveFinalBy, String approveStatus, LocalDate approveFirstDate, LocalDate approveSecondDate) {
        this.approveAttendanceID = approveAttendanceID;
        this.attendanceID = attendanceID;
        this.employeeID = employeeID;
        this.date = date;
        this.workHour = workHour;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.note = note;
        this.approveFirstBy = approveFirstBy;
        this.approveFinalBy = approveFinalBy;
        this.approveStatus = approveStatus;
        this.approveFirstDate = approveFirstDate;
        this.approveSecondDate = approveSecondDate;
    }

    public Long getApproveAttendanceID() {
        return approveAttendanceID;
    }

    public void setApproveAttendanceID(Long approveAttendanceID) {
        this.approveAttendanceID = approveAttendanceID;
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

    public String getApproveFirstBy() {
        return approveFirstBy;
    }

    public void setApproveFirstBy(String approveFirstBy) {
        this.approveFirstBy = approveFirstBy;
    }

    public String getApproveFinalBy() {
        return approveFinalBy;
    }

    public void setApproveFinalBy(String approveFinalBy) {
        this.approveFinalBy = approveFinalBy;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public LocalDate getApproveFirstDate() {
        return approveFirstDate;
    }

    public void setApproveFirstDate(LocalDate approveFirstDate) {
        this.approveFirstDate = approveFirstDate;
    }

    public LocalDate getApproveSecondDate() {
        return approveSecondDate;
    }

    public void setApproveSecondDate(LocalDate approveSecondDate) {
        this.approveSecondDate = approveSecondDate;
    }
}
