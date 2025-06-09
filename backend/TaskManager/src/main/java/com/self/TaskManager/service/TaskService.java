package com.self.TaskManager.service;

import com.self.TaskManager.dto.requests.TaskCreateRequest;
import com.self.TaskManager.dto.requests.TaskUpdateRequest;
import com.self.TaskManager.model.Task; // Return entity for now, controller will map
import java.util.List;

public interface TaskService {
    Task createTask(TaskCreateRequest taskCreateRequest, Long ownerId);
    Task getTaskById(Long taskId, Long userId); // userId to check ownership or admin
    List<Task> getAllTasksForUser(Long userId);
    List<Task> getAllTasksAdmin(); // Admin can see all tasks
    Task updateTask(Long taskId, TaskUpdateRequest taskUpdateRequest, Long userId); // userId for auth check
    void deleteTask(Long taskId, Long userId); // userId for auth check
}