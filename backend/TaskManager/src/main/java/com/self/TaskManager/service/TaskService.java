package com.self.TaskManager.service;

import com.self.TaskManager.dto.TaskDTO;
import com.self.TaskManager.mapper.TaskMapper;
import com.self.TaskManager.model.Task;
import com.self.TaskManager.model.User;
import com.self.TaskManager.repository.TaskRepository;
import com.self.TaskManager.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public TaskDTO createTask(String username, TaskDTO taskDTO) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setOwner(user);

        task = taskRepository.save(task);

        return TaskMapper.toDTO(task);
    }

    public List<TaskDTO> getTasksByUser(Long userId) {
        return taskRepository.findByOwnerId(userId)
                .stream()
                .map(TaskMapper::toDTO) // <-- fix here
                .collect(Collectors.toList());
    }
}
