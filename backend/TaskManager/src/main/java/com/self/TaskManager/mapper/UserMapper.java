package com.self.TaskManager.mapper;

import com.self.TaskManager.dto.UserDTO;
import com.self.TaskManager.model.User;
import com.self.TaskManager.model.Role;
import com.self.TaskManager.model.Task;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPassword(user.getPassword()); // Include password if needed

        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream()
                    .map(RoleMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        if (user.getTasksOwned() != null) {
            dto.setTasksOwned(user.getTasksOwned().stream()
                    .map(TaskMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPassword(dto.getPassword()); // Set password from DTO

        if (dto.getRoles() != null) {
            user.setRoles(dto.getRoles().stream()
                    .map(RoleMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        if (dto.getTasksOwned() != null) {
            user.setTasksOwned(dto.getTasksOwned().stream()
                    .map(TaskMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        return user;
    }
}
