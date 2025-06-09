// src/main/java/com/self/TaskManager/mapper/UserMapper.java
package com.self.TaskManager.mapper;

import com.self.TaskManager.dto.RoleDTO;        // Your RoleDTO
import com.self.TaskManager.dto.TaskDTO;  // Your TaskDTO
import com.self.TaskManager.dto.responses.DetailedUserResponse;
import com.self.TaskManager.dto.responses.UserProfileResponse;
import com.self.TaskManager.dto.responses.UserSummaryResponse;
import com.self.TaskManager.model.Role;
import com.self.TaskManager.model.Task;
import com.self.TaskManager.model.User;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    // --- User Entity to UserProfileResponse ---
    public static UserProfileResponse toUserProfileResponse(User user) {
        if (user == null) return null;
        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        if (user.getRoles() != null) {
            response.setRoles(user.getRoles().stream()
                    .map(role -> role.getRole().name()) // RoleType enum to String
                    .collect(Collectors.toList()));
        } else {
            response.setRoles(Collections.emptyList());
        }
        return response;
    }

    // --- User Entity to DetailedUserResponse (Updated) ---
    public static DetailedUserResponse toDetailedUserResponse(User user) {
        if (user == null) return null;
        DetailedUserResponse response = new DetailedUserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());

        if (user.getRoles() != null) {
            response.setRoles(user.getRoles().stream()
                    .map(UserMapper::toRoleDTOInternal) // Use helper
                    .collect(Collectors.toList()));
        } else {
            response.setRoles(Collections.emptyList());
        }

        if (user.getTasksOwned() != null) {
            // Be mindful of LAZY loading. Ensure tasks are fetched if needed.
            // This might require a EAGER fetch strategy on User.tasksOwned,
            // or fetching them explicitly in the service before calling the mapper,
            // or a @Transactional context that extends to the mapper call.
            response.setTasksOwned(user.getTasksOwned().stream()
                    .map(UserMapper::toTaskDTOInternal) // Use helper
                    .collect(Collectors.toList()));
        } else {
            response.setTasksOwned(Collections.emptyList());
        }

        // Map other fields like createdAt, isEnabled if they exist on User and DetailedUserResponse
        // response.setCreatedAt(user.getCreatedAt());
        // response.setEnabled(user.isEnabled());

        return response;
    }

    // --- User Entity to UserSummaryResponse ---
    public static UserSummaryResponse toUserSummaryResponse(User user) {
        if (user == null) return null;
        UserSummaryResponse summary = new UserSummaryResponse();
        summary.setId(user.getId());
        summary.setName(user.getName());
        summary.setEmail(user.getEmail());
        if (user.getRoles() != null) {
            summary.setRoles(user.getRoles().stream()
                    .map(role -> role.getRole().name()) // RoleType enum to String
                    .collect(Collectors.toList()));
        } else {
            summary.setRoles(Collections.emptyList());
        }
        return summary;
    }

    public static List<UserSummaryResponse> toUserSummaryResponseList(List<User> users) {
        if (users == null) return Collections.emptyList();
        return users.stream()
                .map(UserMapper::toUserSummaryResponse)
                .collect(Collectors.toList());
    }


    // --- Internal Helper: Role Entity to RoleDTO ---
    private static RoleDTO toRoleDTOInternal(Role roleEntity) {
        if (roleEntity == null) return null;
        RoleDTO dto = new RoleDTO();
        dto.setId(roleEntity.getId());
        dto.setRole(roleEntity.getRole()); // Assumes Role entity has getRole() -> RoleType
        return dto;
    }

    // --- Internal Helper: Task Entity to TaskDTO ---
    private static TaskDTO toTaskDTOInternal(Task taskEntity) {
        if (taskEntity == null) return null;
        TaskDTO dto = new TaskDTO();
        dto.setId(taskEntity.getId());
        dto.setTitle(taskEntity.getTitle());
        dto.setStatus(taskEntity.getStatus());
        dto.setDueDate(taskEntity.getDueDate());
        // Add other fields from Task entity to TaskDTO as needed
        return dto;
    }
}