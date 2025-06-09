package com.self.TaskManager.dto.requests;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class TaskCreateRequest {
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDateTime dueDate;

    // Status might be set by default or allow specific initial statuses
    private String status; // e.g., "TODO" or allow user to set if applicable

    // Owner will be set based on the authenticated user, not from the request body
    // private Long ownerId; (No, don't allow client to set owner of new task)

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