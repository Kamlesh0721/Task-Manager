package com.self.TaskManager.dto;

import java.time.LocalDateTime;

public class TaskDTO { // You could also name this TaskSummaryDTO
    private Long id;
    private String title;
    private String status;
    private LocalDateTime dueDate;
    // Add other fields if needed for this summary, e.g., priority
    // Avoid including the full description if it can be very long, unless necessary for the summary.

    // Constructors
    public TaskDTO() {}

    public TaskDTO(Long id, String title, String status, LocalDateTime dueDate) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
}
