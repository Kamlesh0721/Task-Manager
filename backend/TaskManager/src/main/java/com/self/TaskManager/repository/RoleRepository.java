// src/main/java/com/self/TaskManager/repository/RoleRepository.java
package com.self.TaskManager.repository;

import com.self.TaskManager.model.Role;    // Or .entity.Role
import com.self.TaskManager.model.RoleType; // Or .enums.RoleType
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Finds a role by its RoleType.
     * The method name 'findByRole' matches the 'role' field in the Role entity.
     *
     * @param roleType The RoleType enum representing the role.
     * @return An Optional containing the Role if found, or an empty Optional otherwise.
     */
    Optional<Role> findByRole(RoleType roleType); // <<<< CHANGED from findByName

    /**
     * Checks if a role with the given RoleType exists.
     *
     * @param roleType The RoleType enum representing the role.
     * @return true if a role with the RoleType exists, false otherwise.
     */
    boolean existsByRole(RoleType roleType); // <<<< CHANGED from existsByName
}