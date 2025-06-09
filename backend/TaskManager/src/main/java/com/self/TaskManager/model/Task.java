// src/main/java/com/self/TaskManager/model/Task.java
package com.self.TaskManager.model; // Or your .entity package

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private String status; // Or use TaskStatus enum

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Task must have an owner
    private User owner;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null || status.trim().isEmpty()) {
            status = "TODO"; // Default status
        }
    }

    // Constructors
    public Task() {}

    public Task(String title, String description, LocalDateTime dueDate, String status, User owner) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.owner = owner;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}