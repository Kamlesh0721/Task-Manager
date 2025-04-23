package com.self.TaskManager.controller;

import com.self.TaskManager.dto.TaskDTO;
import com.self.TaskManager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<TaskDTO> createTask(@PathVariable Long userId, @RequestBody TaskDTO taskDTO) {
        TaskDTO createdTask = taskService.createTask(userId, taskDTO);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUser(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.getTasksByUser(userId);
        return ResponseEntity.ok(tasks);
    }
}
