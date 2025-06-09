package com.self.TaskManager.mapper;

import com.self.TaskManager.dto.responses.TaskResponse;
import com.self.TaskManager.model.Task;
import com.self.TaskManager.model.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    public static TaskResponse toTaskResponse(Task task) {
        if (task == null) {
            return null;
        }
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setCreatedAt(task.getCreatedAt());
        response.setDueDate(task.getDueDate());
        response.setStatus(task.getStatus());

        User owner = task.getOwner();
        if (owner != null) {
            response.setOwnerId(owner.getId());
            response.setOwnerName(owner.getName()); // Assuming User entity has getName()
        }
        return response;
    }

    public static List<TaskResponse> toTaskResponseList(List<Task> tasks) {
        if (tasks == null) {
            return Collections.emptyList();
        }
        return tasks.stream()
                .map(TaskMapper::toTaskResponse)
                .collect(Collectors.toList());
    }
}