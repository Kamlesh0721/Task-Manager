package com.self.TaskManager.controller;

import com.self.TaskManager.dto.requests.TaskCreateRequest;
import com.self.TaskManager.dto.responses.TaskResponse;
import com.self.TaskManager.dto.requests.TaskUpdateRequest;
import com.self.TaskManager.model.Task;
import com.self.TaskManager.mapper.TaskMapper;
import com.self.TaskManager.security.services.UserDetailsImpl;
import com.self.TaskManager.service.TaskService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private UserDetailsImpl getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            return (UserDetailsImpl) authentication.getPrincipal();
        }
        // This indicates an issue if reached in a secured endpoint
        logger.warn("Could not retrieve UserDetailsImpl from security context.");
        return null;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskCreateRequest taskCreateRequest) {
        UserDetailsImpl currentUser = getCurrentUserDetails();
        if (currentUser == null) { // Should be handled by @PreAuthorize
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Task createdTask = taskService.createTask(taskCreateRequest, currentUser.getId());
        logger.info("Task created with ID: {} by user ID: {}", createdTask.getId(), currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(TaskMapper.toTaskResponse(createdTask));
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("isAuthenticated()") // More specific auth handled in service
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long taskId) {
        UserDetailsImpl currentUser = getCurrentUserDetails();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Task task = taskService.getTaskById(taskId, currentUser.getId());
        return ResponseEntity.ok(TaskMapper.toTaskResponse(task));
    }

    @GetMapping("/my-tasks")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TaskResponse>> getMyTasks() {
        UserDetailsImpl currentUser = getCurrentUserDetails();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Task> tasks = taskService.getAllTasksForUser(currentUser.getId());
        return ResponseEntity.ok(TaskMapper.toTaskResponseList(tasks));
    }

    // Admin endpoint to get all tasks in the system
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskResponse>> getAllTasksAdmin() {
        List<Task> tasks = taskService.getAllTasksAdmin();
        return ResponseEntity.ok(TaskMapper.toTaskResponseList(tasks));
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("isAuthenticated()") // Service layer handles ownership/admin check
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long taskId,
                                                   @Valid @RequestBody TaskUpdateRequest taskUpdateRequest) {
        UserDetailsImpl currentUser = getCurrentUserDetails();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Task updatedTask = taskService.updateTask(taskId, taskUpdateRequest, currentUser.getId());
        logger.info("Task with ID: {} updated by user ID: {}", taskId, currentUser.getId());
        return ResponseEntity.ok(TaskMapper.toTaskResponse(updatedTask));
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("isAuthenticated()") // Service layer handles ownership/admin check
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        UserDetailsImpl currentUser = getCurrentUserDetails();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        taskService.deleteTask(taskId, currentUser.getId());
        logger.info("Task with ID: {} deleted by user ID: {}", taskId, currentUser.getId());
        return ResponseEntity.noContent().build();
    }
}