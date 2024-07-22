package com.training.hrm.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personID;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_of_date")
    private LocalDate birthOfDate;

    @Embedded
    private CitizenIdentity citizenIdentity;

    @Embedded
    private PassPort passPort;

    @Column(name = "permanent_address")
    private String permanentAddress;

    @Column(name = "current_address")
    private String currentAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Embeddable
    public static class CitizenIdentity {
        @Column(name = "citizen_identity_id")
        private String citizenIdentityID;

        @Column(name = "citizen_identity_date")
        private LocalDate citizenIdentityDate;

        @Column(name = "citizen_identity_where")
        private String citizenIdentityWhere;

        @Column(name = "citizen_identity_out_date")
        private LocalDate citizenIdentityOutDate;

        public CitizenIdentity() {
        }

        public CitizenIdentity(String citizenIdentityID, LocalDate citizenIdentityDate, String citizenIdentityWhere, LocalDate citizenIdentityOutDate) {
            this.citizenIdentityID = citizenIdentityID;
            this.citizenIdentityDate = citizenIdentityDate;
            this.citizenIdentityWhere = citizenIdentityWhere;
            this.citizenIdentityOutDate = citizenIdentityOutDate;
        }

        public String getCitizenIdentityID() {
            return citizenIdentityID;
        }

        public void setCitizenIdentityID(String citizenIdentityID) {
            this.citizenIdentityID = citizenIdentityID;
        }

        public LocalDate getCitizenIdentityDate() {
            return citizenIdentityDate;
        }

        public void setCitizenIdentityDate(LocalDate citizenIdentityDate) {
            this.citizenIdentityDate = citizenIdentityDate;
        }

        public String getCitizenIdentityWhere() {
            return citizenIdentityWhere;
        }

        public void setCitizenIdentityWhere(String citizenIdentityWhere) {
            this.citizenIdentityWhere = citizenIdentityWhere;
        }

        public LocalDate getCitizenIdentityOutDate() {
            return citizenIdentityOutDate;
        }

        public void setCitizenIdentityOutDate(LocalDate citizenIdentityOutDate) {
            this.citizenIdentityOutDate = citizenIdentityOutDate;
        }
    }

    @Embeddable
    public static class PassPort {
        @Column(name = "pass_port_id")
        private String passPortID;

        @Column(name = "pass_port_date")
        private LocalDate passPortDate;

        @Column(name = "pass_port_where")
        private String passPortWhere;

        @Column(name = "pass_port_out_date")
        private LocalDate passPortOutDate;

        public PassPort() {
        }

        public PassPort(String passPortID, LocalDate passPortDate, String passPortWhere, LocalDate passPortOutDate) {
            this.passPortID = passPortID;
            this.passPortDate = passPortDate;
            this.passPortWhere = passPortWhere;
            this.passPortOutDate = passPortOutDate;
        }

        public String getPassPortID() {
            return passPortID;
        }

        public void setPassPortID(String passPortID) {
            this.passPortID = passPortID;
        }

        public LocalDate getPassPortDate() {
            return passPortDate;
        }

        public void setPassPortDate(LocalDate passPortDate) {
            this.passPortDate = passPortDate;
        }

        public String getPassPortWhere() {
            return passPortWhere;
        }

        public void setPassPortWhere(String passPortWhere) {
            this.passPortWhere = passPortWhere;
        }

        public LocalDate getPassPortOutDate() {
            return passPortOutDate;
        }

        public void setPassPortOutDate(LocalDate passPortOutDate) {
            this.passPortOutDate = passPortOutDate;
        }
    }

    public Person() {
    }

    public Person(Long personID, String fullName, String gender, LocalDate birthOfDate, CitizenIdentity citizenIdentity, PassPort passPort, String permanentAddress, String currentAddress, String phoneNumber, String email) {
        this.personID = personID;
        this.fullName = fullName;
        this.gender = gender;
        this.birthOfDate = birthOfDate;
        this.citizenIdentity = citizenIdentity;
        this.passPort = passPort;
        this.permanentAddress = permanentAddress;
        this.currentAddress = currentAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Long getPersonID() {
        return personID;
    }

    public void setPersonID(Long personID) {
        this.personID = personID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(LocalDate birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public CitizenIdentity getCitizenIdentity() {
        return citizenIdentity;
    }

    public void setCitizenIdentity(CitizenIdentity citizenIdentity) {
        this.citizenIdentity = citizenIdentity;
    }

    public PassPort getPassPort() {
        return passPort;
    }

    public void setPassPort(PassPort passPort) {
        this.passPort = passPort;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


