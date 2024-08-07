package com.training.hrm.models;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleID;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Role() {
    }

    public Role(Long roleID, String name, String description) {
        this.roleID = roleID;
        this.name = name;
        this.description = description;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
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
