package com.self.TaskManager.controller;

import com.self.TaskManager.dto.TaskDTO;
import com.self.TaskManager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;


    // Constructor injection of TaskService
    public TaskController(TaskService taskService) {
        this.taskService = taskService;

    }

    // Create task
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        // Retrieve the username from the authentication object
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Delegate the task creation to the service layer
        TaskDTO createdTask = taskService.createTask(username, taskDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    // Get tasks by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUser(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.getTasksByUser(userId);
        return ResponseEntity.ok(tasks);
    }
}
