// src/main/java/com/self/TaskManager/dto/UserProfileResponse.java
package com.self.TaskManager.dto.responses;

import java.util.List;

public class UserProfileResponse {
    private Long id;
    private String email;
    private String name;
    private List<String> roles; // List of role names (e.g., "ROLE_USER")
    // Add other relevant non-sensitive info for the current user's profile

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}