// src/main/java/com/self/TaskManager/repository/TaskRepository.java
package com.self.TaskManager.repository;

import com.self.TaskManager.model.Task;
import com.self.TaskManager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOwner(User owner);
    List<Task> findByOwnerId(Long userId);
    // You can add more custom query methods here, e.g., findByStatus, findByDueDateBefore, etc.
}