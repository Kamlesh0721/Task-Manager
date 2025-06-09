package com.self.TaskManager.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.Set;

public class AdminUserUpdateRequest {

    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email; // Admin might be able to change email

    private Set<String> roles; // Admin can change roles

    // Admin might also be able to reset password, but that would likely be a separate action/endpoint.
    // Forcing a password change here might not be ideal without user involvement or a temp password system.

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}