// src/main/java/com/self/TaskManager/dto/AuthResponse.java
package com.self.TaskManager.dto.responses;

import java.util.List;

public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String name;
    private List<String> roles;

    public AuthResponse(String accessToken, Long id, String email, String name, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.name = name;
        this.roles = roles;
    }
    // Getters and Setters (ensure all are present)
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}