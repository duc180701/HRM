package com.training.hrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personID;

    @Column(name = "full_name")
    @NotEmpty(message = "Please enter a valid full name")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name must contain only uppercase and lowercase letters")
    private String fullName;

    @Column(name = "gender")
    @NotBlank(message = "Please enter a valid gender")
    private String gender;

    @Column(name = "birth_of_date")
    @NotNull(message = "Please enter a valid birth of date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "Date of birth must be a date in the past")
    private LocalDate birthOfDate;

    @Embedded
    private CitizenIdentity citizenIdentity;

    @Embedded
    private PassPort passPort;

    @Column(name = "permanent_address")
    @NotBlank(message = "Please enter a valid permanent address")
    private String permanentAddress;

    @Column(name = "current_address")
    @NotBlank(message = "Please enter a valid current address")
    private String currentAddress;

    @Column(name = "phone_number")
    @NotBlank(message = "Please enter a valid phone number")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only numbers")
    @Pattern(regexp = "^0\\d{9}$", message = "Phone number must be 10 characters long and start with 0")
    private String phoneNumber;

    @Column(name = "email")
    @NotBlank(message = "Please enter a valid email")
    @Email(message = "Please enter a valid email")
    private String email;

    public Person() {
    }

    public Person(Long personID, String fullName, String gender, LocalDate birthOfDate, CitizenIdentity citizenIdentity, PassPort passPort, String permanentAddress, String currentAddress, String phoneNumber, String email) {
        personID = personID;
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
        personID = personID;
    }

    public @NotEmpty(message = "Please enter a valid full name") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name must contain only uppercase and lowercase letters") String getFullName() {
        return fullName;
    }

    public void setFullName(@NotEmpty(message = "Please enter a valid full name") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name must contain only uppercase and lowercase letters") String fullName) {
        this.fullName = fullName;
    }

    public @NotBlank(message = "Please enter a valid gender") String getGender() {
        return gender;
    }

    public void setGender(@NotBlank(message = "Please enter a valid gender") String gender) {
        this.gender = gender;
    }

    public @NotNull(message = "Please enter a valid birth of date") @Past(message = "Date of birth must be a date in the past") LocalDate getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(@NotNull(message = "Please enter a valid birth of date") @Past(message = "Date of birth must be a date in the past") LocalDate birthOfDate) {
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

    public @NotBlank(message = "Please enter a valid permanent address") String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(@NotBlank(message = "Please enter a valid permanent address") String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public @NotBlank(message = "Please enter a valid current address") String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(@NotBlank(message = "Please enter a valid current address") String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public @NotBlank(message = "Please enter a valid phone number") @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only numbers") @Pattern(regexp = "^0\\d{9}$", message = "Phone number must be 10 characters long and start with 0") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotBlank(message = "Please enter a valid phone number") @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only numbers") @Pattern(regexp = "^0\\d{9}$", message = "Phone number must be 10 characters long and start with 0") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @NotBlank(message = "Please enter a valid email") @Email(message = "Please enter a valid email") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Please enter a valid email") @Email(message = "Please enter a valid email") String email) {
        this.email = email;
    }

    @Embeddable
    public static class CitizenIdentity {
        @Column(name = "citizen_identity_id")
        @NotBlank(message = "Please enter a valid citizen identity id")
        @Pattern(regexp = "^0\\d{11}$", message = "Phone number must be 12 characters long and start with 0")
        private String citizenIdentityID;

        @Column(name = "citizen_identity_date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @NotNull(message = "Please enter a valid citizen identity date")
        private LocalDate citizenIdentityDate;

        @Column(name = "citizen_identity_where")
        @NotBlank(message = "Please enter a valid citizen identity where")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Citizen identity where must contain only uppercase and lowercase letters")
        private String citizenIdentityWhere;

        @Column(name = "citizen_identity_out_date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @NotNull(message = "Please enter a valid citizen identity out date")
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

        public void setCitizenIdentityID(@NotBlank(message = "Please enter a valid citizen identity id") @Pattern(regexp = "^0\\d{11}$", message = "Phone number must be 12 characters long and start with 0") String citizenIdentityID) {
            this.citizenIdentityID = citizenIdentityID;
        }

        public @NotNull(message = "Please enter a valid citizen identity date") LocalDate getCitizenIdentityDate() {
            return citizenIdentityDate;
        }

        public void setCitizenIdentityDate(@NotNull(message = "Please enter a valid citizen identity date") LocalDate citizenIdentityDate) {
            this.citizenIdentityDate = citizenIdentityDate;
        }

        public @NotBlank(message = "Please enter a valid citizen identity where") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Citizen identity where must contain only uppercase and lowercase letters") String getCitizenIdentityWhere() {
            return citizenIdentityWhere;
        }

        public void setCitizenIdentityWhere(@NotBlank(message = "Please enter a valid citizen identity where") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Citizen identity where must contain only uppercase and lowercase letters") String citizenIdentityWhere) {
            this.citizenIdentityWhere = citizenIdentityWhere;
        }

        public @NotNull(message = "Please enter a valid citizen identity out date") LocalDate getCitizenIdentityOutDate() {
            return citizenIdentityOutDate;
        }

        public void setCitizenIdentityOutDate(@NotNull(message = "Please enter a valid citizen identity out date") LocalDate citizenIdentityOutDate) {
            this.citizenIdentityOutDate = citizenIdentityOutDate;
        }
    }

    @Embeddable
    public static class PassPort {
        @Column(name = "pass_port_id")
        @NotBlank(message = "Please enter a valid pass port id")
        @Pattern(regexp = "^0\\d{7}$", message = "Phone number must be 8 characters long and start with 0")
        private String passPortID;

        @Column(name = "pass_port_date")
        @NotNull(message = "Please enter a valid pass port date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate passPortDate;

        @Column(name = "pass_port_where")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name must contain only uppercase and lowercase letters")
        @NotBlank(message = "Please enter a valid pass port where")
        private String passPortWhere;

        @Column(name = "pass_port_out_date")
        @NotNull(message = "Please enter a valid pass port out date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate passPortOutDate;

        public PassPort() {
        }

        public PassPort(String passPortID, LocalDate passPortDate, String passPortWhere, LocalDate passPortOutDate) {
            this.passPortID = passPortID;
            this.passPortDate = passPortDate;
            this.passPortWhere = passPortWhere;
            this.passPortOutDate = passPortOutDate;
        }

        public @NotBlank(message = "Please enter a valid pass port id") String getPassPortID() {
            return passPortID;
        }

        public void setPassPortID(@NotBlank(message = "Please enter a valid pass port id") String passPortID) {
            this.passPortID = passPortID;
        }

        public @NotNull(message = "Please enter a valid pass port date") LocalDate getPassPortDate() {
            return passPortDate;
        }

        public void setPassPortDate(@NotNull(message = "Please enter a valid pass port date") LocalDate passPortDate) {
            this.passPortDate = passPortDate;
        }

        public @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name must contain only uppercase and lowercase letters") @NotBlank(message = "Please enter a valid pass port where") String getPassPortWhere() {
            return passPortWhere;
        }

        public void setPassPortWhere(@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name must contain only uppercase and lowercase letters") @NotBlank(message = "Please enter a valid pass port where") String passPortWhere) {
            this.passPortWhere = passPortWhere;
        }

        public @NotNull(message = "Please enter a valid pass port out date") LocalDate getPassPortOutDate() {
            return passPortOutDate;
        }

        public void setPassPortOutDate(@NotNull(message = "Please enter a valid pass port out date") LocalDate passPortOutDate) {
            this.passPortOutDate = passPortOutDate;
        }
    }
}


