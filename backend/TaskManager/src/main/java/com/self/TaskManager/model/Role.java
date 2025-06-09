// src/main/java/com/self/TaskManager/model/Role.java
package com.self.TaskManager.model; // Or your .entity package

import com.fasterxml.jackson.annotation.JsonIgnore; // Use JsonIgnore instead of JsonBackReference if preferred for simpler handling
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles") // Explicitly name the table 'roles'
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 20, unique = true, nullable = false) // Column name for the enum
    private RoleType role; // Field name is 'role', type is 'RoleType'

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore // Often preferred over JsonBackReference to simply omit during User serialization
    private List<User> users = new ArrayList<>();

    // Constructors
    public Role() {}

    public Role(RoleType roleType) {
        this.role = roleType;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public RoleType getRole() { return role; }
    public void setRole(RoleType role) { this.role = role; }

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }
}