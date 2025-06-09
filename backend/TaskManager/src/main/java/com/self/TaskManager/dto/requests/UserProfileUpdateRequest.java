package com.self.TaskManager.dto.requests;
import jakarta.validation.constraints.Size;

public class UserProfileUpdateRequest {

    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name; // User can update their name

    // Add other fields a user can update themselves, e.g., preferences
    // Email changes often require a separate, more secure process (e.g., verification)
    // and might not be part of a simple profile update.

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}