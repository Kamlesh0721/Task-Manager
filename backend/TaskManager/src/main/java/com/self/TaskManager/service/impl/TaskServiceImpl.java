package com.self.TaskManager.service.impl;

import com.self.TaskManager.dto.requests.TaskCreateRequest;
import com.self.TaskManager.dto.requests.TaskUpdateRequest;
import com.self.TaskManager.model.Task;
import com.self.TaskManager.model.User;
import com.self.TaskManager.exceptions.ResourceNotFoundException;
import com.self.TaskManager.exceptions.UnauthorizedOperationException; // New exception
import com.self.TaskManager.repository.TaskRepository;
import com.self.TaskManager.repository.UserRepository;
import com.self.TaskManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Task createTask(TaskCreateRequest taskCreateRequest, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", ownerId.toString()));

        Task task = new Task();
        task.setTitle(taskCreateRequest.getTitle());
        task.setDescription(taskCreateRequest.getDescription());
        task.setDueDate(taskCreateRequest.getDueDate());
        if (StringUtils.hasText(taskCreateRequest.getStatus())) {
            // Optional: Validate if status is one of the allowed values (e.g., from TaskStatus enum)
            task.setStatus(taskCreateRequest.getStatus());
        } else {
            task.setStatus("TODO"); // Default status
        }
        task.setOwner(owner);
        return taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTaskById(Long taskId, Long currentUserId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId.toString()));

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUserId.toString())); // Should not happen if auth is ok

        // Check if current user is the owner or an admin
        boolean isAdmin = currentUser.getRoles().stream().anyMatch(role -> role.getRole().name().equals("ADMIN"));
        if (!task.getOwner().getId().equals(currentUserId) && !isAdmin) {
            throw new UnauthorizedOperationException("You are not authorized to view this task.");
        }
        return task;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllTasksForUser(Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));
        return taskRepository.findByOwner(owner);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllTasksAdmin() {
        return taskRepository.findAll(); // Admin gets all tasks
    }

    @Override
    @Transactional
    public Task updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest, Long currentUserId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId.toString()));

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUserId.toString()));

        boolean isAdmin = currentUser.getRoles().stream().anyMatch(role -> role.getRole().name().equals("ADMIN"));
        if (!task.getOwner().getId().equals(currentUserId) && !isAdmin) {
            throw new UnauthorizedOperationException("You are not authorized to update this task.");
        }

        if (StringUtils.hasText(taskUpdateRequest.getTitle())) {
            task.setTitle(taskUpdateRequest.getTitle());
        }
        if (taskUpdateRequest.getDescription() != null) { // Allow setting empty description
            task.setDescription(taskUpdateRequest.getDescription());
        }
        if (taskUpdateRequest.getDueDate() != null) {
            task.setDueDate(taskUpdateRequest.getDueDate());
        }
        if (StringUtils.hasText(taskUpdateRequest.getStatus())) {
            // Optional: Validate status update logic (e.g., can't go from DONE to TODO without admin)
            task.setStatus(taskUpdateRequest.getStatus());
        }
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId, Long currentUserId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId.toString()));

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUserId.toString()));

        boolean isAdmin = currentUser.getRoles().stream().anyMatch(role -> role.getRole().name().equals("ADMIN"));
        if (!task.getOwner().getId().equals(currentUserId) && !isAdmin) {
            throw new UnauthorizedOperationException("You are not authorized to delete this task.");
        }
        taskRepository.delete(task);
    }
}