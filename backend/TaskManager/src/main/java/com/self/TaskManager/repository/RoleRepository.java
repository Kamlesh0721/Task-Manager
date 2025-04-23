package com.self.TaskManager.repository;

import com.self.TaskManager.model.Role;
import com.self.TaskManager.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(RoleType role);
}
