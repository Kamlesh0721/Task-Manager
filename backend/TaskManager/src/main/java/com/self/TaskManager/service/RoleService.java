package com.self.TaskManager.service;

import com.self.TaskManager.model.Role;
import com.self.TaskManager.model.RoleType;
import com.self.TaskManager.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getRoleByType(RoleType roleType) {
        return roleRepository.findByRole(roleType)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleType));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
