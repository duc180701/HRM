package com.training.hrm.models;

import jakarta.persistence.*;

@Entity
@Table(name = "work_status")
public class WorkStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workStatusID;

    @Column(name = "status_id")
    private String statusID;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public WorkStatus() {
    }

    public WorkStatus(Long workStatusID, String statusID, String name, String description) {
        this.workStatusID = workStatusID;
        this.statusID = statusID;
        this.name = name;
        this.description = description;
    }

    public Long getWorkStatusID() {
        return workStatusID;
    }

    public void setWorkStatusID(Long workStatusID) {
        this.workStatusID = workStatusID;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
