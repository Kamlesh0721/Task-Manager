package com.self.TaskManager.dto.requests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class TaskUpdateRequest {
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title; // Optional: if null, not updated

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description; // Optional

    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDateTime dueDate; // Optional

    private String status; // Optional: e.g., "IN_PROGRESS", "COMPLETED"

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}