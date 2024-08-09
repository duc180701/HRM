package com.training.hrm.models;

import jakarta.persistence.*;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "change_personnel_position")
public class ApproveBackupPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approveBackupPositionID;

    @Column(name = "personnel_id")
    private Long personnelID;

    @Column(name = "old_position")
    private String oldPosition;

    @Column(name = "new_position")
    private String newPosition;

    @Column(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Column(name = "reason")
    private String reason;

    @Column(name = "approve")
    private Boolean approve;

    public ApproveBackupPosition() {
    }

    public ApproveBackupPosition(Long approveBackupPositionID, Long personnelID, String oldPosition, String newPosition, LocalDate date, String reason, Boolean approve) {
        this.approveBackupPositionID = approveBackupPositionID;
        this.personnelID = personnelID;
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.date = date;
        this.reason = reason;
        this.approve = approve;
    }

    public Long getApproveBackupPositionID() {
        return approveBackupPositionID;
    }

    public void setApproveBackupPositionID(Long approveBackupPositionID) {
        this.approveBackupPositionID = approveBackupPositionID;
    }

    public Long getPersonnelID() {
        return personnelID;
    }

    public void setPersonnelID(Long personnelID) {
        this.personnelID = personnelID;
    }

    public String getOldPosition() {
        return oldPosition;
    }

    public void setOldPosition(String oldPosition) {
        this.oldPosition = oldPosition;
    }

    public String getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(String newPosition) {
        this.newPosition = newPosition;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getApprove() {
        return approve;
    }

    public void setApprove(Boolean approve) {
        this.approve = approve;
    }
}


