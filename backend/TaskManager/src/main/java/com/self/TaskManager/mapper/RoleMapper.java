package com.self.TaskManager.mapper;

import com.self.TaskManager.dto.RoleDTO;
import com.self.TaskManager.model.Role;
import com.self.TaskManager.model.RoleType;

public class RoleMapper {

    public static RoleDTO toDTO(Role role) {
        if (role == null) return null;

        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setRole(role.getRole());  // Convert enum to String
        return dto;
    }

    public static Role toEntity(RoleDTO dto) {
        if (dto == null) return null;

        Role role = new Role();
        role.setId(dto.getId());
        role.setRole(RoleType.valueOf(dto.getRole().name()));  // Convert String to enum
        return role;
    }
}
