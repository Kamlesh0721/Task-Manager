// src/main/java/com/self/TaskManager/dto/UserSummaryResponse.java
package com.self.TaskManager.dto.responses;

import java.util.List;

public class UserSummaryResponse {
    private Long id;
    private String name;
    private String email;
    private List<String> roles; // Simple list of role names

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}