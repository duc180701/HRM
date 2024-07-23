package com.training.hrm.dto;

public class EmployeeResponse {
    private Long employeeID;
    private String fullName;
    private String position;
    private String department;
    private String status;

    public EmployeeResponse() {
    }

    public EmployeeResponse(Long employeeID, String fullName, String position, String department, String status) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.position = position;
        this.department = department;
        this.status = status;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
