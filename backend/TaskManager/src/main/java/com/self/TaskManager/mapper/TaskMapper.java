package com.self.TaskManager.mapper;

import com.self.TaskManager.model.Task;
import com.self.TaskManager.dto.TaskDTO;

public class TaskMapper {

    public static TaskDTO toDTO(Task task) {
        if (task == null) return null;

        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setDate(task.getDate());
        dto.setCompleted(task.isCompleted());
        dto.setCreatorName(task.getCreatorName());
        return dto;
    }

    public static Task toEntity(TaskDTO dto) {
        if (dto == null) return null;

        Task task = new Task();
        task.setId(dto.getId());
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setDate(dto.getDate());
        task.setCompleted(dto.isCompleted());
        task.setCreatorName(dto.getCreatorName());
        return task;
    }
}
