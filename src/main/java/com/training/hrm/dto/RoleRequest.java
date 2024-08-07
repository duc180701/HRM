package com.training.hrm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoleRequest {

    @NotBlank(message = "Please enter a valid name")
    private String name;

    @NotNull(message = "Description cannot be null")
    private String description;

    public RoleRequest() {
    }

    public RoleRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public @NotBlank(message = "Please enter a valid name") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Please enter a valid name") String name) {
        this.name = name;
    }

    public @NotNull(message = "Description cannot be null") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "Description cannot be null") String description) {
        this.description = description;
    }
}
