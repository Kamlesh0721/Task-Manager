// src/main/java/com/self/TaskManager/model/User.java
package com.self.TaskManager.model; // Or your .entity package

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users") // Explicitly name the table 'users'
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name; // Display name

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Task> tasksOwned = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", // Join table for user-role relationship
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private List<Role> roles = new ArrayList<>();

    // --- Constructors ---
    public User() {}

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User(String email, String name, String password, List<Task> tasksOwned, List<Role> roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.tasksOwned = tasksOwned != null ? tasksOwned : new ArrayList<>();
        this.roles = roles != null ? roles : new ArrayList<>();
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public List<Task> getTasksOwned() { return tasksOwned; }
    public void setTasksOwned(List<Task> tasksOwned) { this.tasksOwned = tasksOwned; }
    public List<Role> getRoles() { return roles; }
    public void setRoles(List<Role> roles) { this.roles = roles; }
}