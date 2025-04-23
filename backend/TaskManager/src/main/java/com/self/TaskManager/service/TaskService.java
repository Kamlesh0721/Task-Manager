package com.self.TaskManager.service;

import com.self.TaskManager.dto.TaskDTO;
import com.self.TaskManager.mapper.TaskMapper;
import com.self.TaskManager.model.Task;
import com.self.TaskManager.model.User;
import com.self.TaskManager.repository.TaskRepository;
import com.self.TaskManager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskDTO createTask(Long userId, TaskDTO taskDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        Task task = TaskMapper.toEntity(taskDTO);
        task.setOwner(user);

        Task savedTask = taskRepository.save(task);
        return TaskMapper.toDTO(savedTask);
    }

    public List<TaskDTO> getTasksByUser(Long userId) {
        return taskRepository.findByOwnerId(userId)
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }
}
