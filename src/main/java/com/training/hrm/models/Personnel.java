package com.training.hrm.models;


import jakarta.persistence.*;

@Entity
@Table(name = "personnels")
public class Personnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personnelID;

    @Column(name = "level")
    private String level;

    @Column(name = "education")
    private String education;

    @Column(name = "graduation_major")
    private String graduationMajor;

    @Column(name = "graduation_school")
    private String graduationSchool;

    @Column(name = "graduation_year")
    private int graduationYear;

    @Column(name = "internal_phone_number")
    private String internalPhoneNumber;

    @Column(name = "internal_email")
    private String internalEmail;

    @Column(name = "department")
    private String department;

    @Column(name = "position")
    private String position;

    @Column(name = "direct_management_staff")
    private String directManagementStaff;

    @Column(name = "status")
    private String status;

    public Personnel() {
    }

    public Personnel(Long personnelID, String level, String education, String graduationMajor, String graduationSchool, int graduationYear, String internalPhoneNumber, String internalEmail, String department, String position, String directManagementStaff, String status) {
        this.personnelID = personnelID;
        this.level = level;
        this.education = education;
        this.graduationMajor = graduationMajor;
        this.graduationSchool = graduationSchool;
        this.graduationYear = graduationYear;
        this.internalPhoneNumber = internalPhoneNumber;
        this.internalEmail = internalEmail;
        this.department = department;
        this.position = position;
        this.directManagementStaff = directManagementStaff;
        this.status = status;
    }

    public Long getPersonnelID() {
        return personnelID;
    }

    public void setPersonnelID(Long personnelID) {
        this.personnelID = personnelID;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGraduationMajor() {
        return graduationMajor;
    }

    public void setGraduationMajor(String graduationMajor) {
        this.graduationMajor = graduationMajor;
    }

    public String getGraduationSchool() {
        return graduationSchool;
    }

    public void setGraduationSchool(String graduationSchool) {
        this.graduationSchool = graduationSchool;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getInternalPhoneNumber() {
        return internalPhoneNumber;
    }

    public void setInternalPhoneNumber(String internalPhoneNumber) {
        this.internalPhoneNumber = internalPhoneNumber;
    }

    public String getInternalEmail() {
        return internalEmail;
    }

    public void setInternalEmail(String internalEmail) {
        this.internalEmail = internalEmail;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDirectManagementStaff() {
        return directManagementStaff;
    }

    public void setDirectManagementStaff(String directManagementStaff) {
        this.directManagementStaff = directManagementStaff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
