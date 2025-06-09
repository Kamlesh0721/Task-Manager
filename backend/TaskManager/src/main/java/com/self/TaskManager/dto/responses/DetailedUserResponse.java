package com.self.TaskManager.dto.responses;

import com.self.TaskManager.dto.RoleDTO;   // Import your RoleDTO
import com.self.TaskManager.dto.TaskDTO; // Import your TaskDTO (or TaskSummaryDTO)

import java.time.LocalDateTime; // If you add createdAt/updatedAt fields
import java.util.List;

public class DetailedUserResponse {
    private Long id;
    private String email;
    private String name;
    private List<RoleDTO> roles;      // Now uses a list of RoleDTO objects
    private List<TaskDTO> tasksOwned; // Now uses a list of TaskDTO objects

    // private LocalDateTime createdAt;
    // private LocalDateTime updatedAt;
    // private boolean isEnabled;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<RoleDTO> getRoles() { return roles; }
    public void setRoles(List<RoleDTO> roles) { this.roles = roles; }

    public List<TaskDTO> getTasksOwned() { return tasksOwned; }
    public void setTasksOwned(List<TaskDTO> tasksOwned) { this.tasksOwned = tasksOwned; }

    // public LocalDateTime getCreatedAt() { return createdAt; }
    // public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    // public LocalDateTime getUpdatedAt() { return updatedAt; }
    // public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    // public boolean isEnabled() { return isEnabled; }
    // public void setEnabled(boolean isEnabled) { this.isEnabled = isEnabled; }
}