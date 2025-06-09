package com.self.TaskManager.dto; // Or a more specific sub-package like dto.shared or dto.role

import com.self.TaskManager.model.RoleType; // Your RoleType enum

public class RoleDTO {
    private Integer id;
    private RoleType role; // Using your RoleType enum directly

    // Constructors
    public RoleDTO() {}

    public RoleDTO(Integer id, RoleType role) {
        this.id = id;
        this.role = role;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}