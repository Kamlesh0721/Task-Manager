package com.self.TaskManager.dto;

import java.util.List;

public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String password; // Include this if needed for creation/login
    private List<RoleDTO> roles;
    private List<TaskDTO> tasksOwned;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<RoleDTO> getRoles() { return roles; }
    public void setRoles(List<RoleDTO> roles) { this.roles = roles; }

    public List<TaskDTO> getTasksOwned() { return tasksOwned; }
    public void setTasksOwned(List<TaskDTO> tasksOwned) { this.tasksOwned = tasksOwned; }
}
