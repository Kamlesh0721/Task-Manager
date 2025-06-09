// src/main/java/com/self/TaskManager/repository/UserRepository.java
package com.self.TaskManager.repository;

import com.self.TaskManager.model.User; // Or .entity.User
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    // Optional<User> findByUsername(String username); // << REMOVE THIS LINE
    Boolean existsByEmail(String email);
    // Boolean existsByUsername(String username);    // << REMOVE THIS LINE (if it was there)
}