package com.self.TaskManager.dto;


import com.self.TaskManager.model.RoleType;

public class RoleDTO {

    private Integer id;
    private RoleType role;

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
