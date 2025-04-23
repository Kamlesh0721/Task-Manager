package com.self.TaskManager.repository;

import com.self.TaskManager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (returns Optional<User> to handle cases where the user might not be found)
    Optional<User> findByEmail(String email);
}
