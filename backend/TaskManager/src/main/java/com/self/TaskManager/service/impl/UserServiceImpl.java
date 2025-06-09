// src/main/java/com/self/TaskManager/service/impl/UserServiceImpl.java
package com.self.TaskManager.service.impl;

// Correct DTO imports based on your package structure (e.g., dto.user.* or dto.requests.*)
import com.self.TaskManager.dto.requests.AdminUserUpdateRequest;
import com.self.TaskManager.dto.requests.UserProfileUpdateRequest;
import com.self.TaskManager.dto.requests.UserRegistrationRequest; // Assumes this DTO no longer has 'roles'
import com.self.TaskManager.dto.requests.UserPasswordUpdateRequest;

// Correct Entity/Model imports
import com.self.TaskManager.model.Role;
import com.self.TaskManager.model.Task;
import com.self.TaskManager.model.User;
import com.self.TaskManager.model.RoleType;

// Correct Exception imports
import com.self.TaskManager.exceptions.BadRequestException;
import com.self.TaskManager.exceptions.ResourceNotFoundException;
import com.self.TaskManager.exceptions.UserAlreadyExistsException;

import com.self.TaskManager.repository.RoleRepository;
import com.self.TaskManager.repository.TaskRepository;
import com.self.TaskManager.repository.UserRepository;
import com.self.TaskManager.service.UserService; // Your UserService interface
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TaskRepository taskRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public User registerUser(UserRegistrationRequest registrationRequest) {
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email '" + registrationRequest.getEmail() + "' is already registered.");
        }

        User user = new User();
        user.setName(registrationRequest.getName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        // Default role assignment
        Role userRole = roleRepository.findByRole(RoleType.USER) // <<<< CHANGED to findByRole
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Default role 'USER' not found. Please ensure it exists in the database.",
                        "RoleType", RoleType.USER.name()
                ));
        user.setRoles(Collections.singletonList(userRole));

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUserProfile(Long userId, UserProfileUpdateRequest updateRequest) {
        User user = getUserById(userId);
        if (StringUtils.hasText(updateRequest.getName())) {
            user.setName(updateRequest.getName());
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUserByAdmin(Long userId, AdminUserUpdateRequest updateRequest) {
        User user = getUserById(userId);
        if (StringUtils.hasText(updateRequest.getName())) {
            user.setName(updateRequest.getName());
        }
        if (StringUtils.hasText(updateRequest.getEmail())) {
            Optional<User> existingUserWithNewEmail = userRepository.findByEmail(updateRequest.getEmail());
            if (existingUserWithNewEmail.isPresent() && !existingUserWithNewEmail.get().getId().equals(userId)) {
                throw new UserAlreadyExistsException("Email '" + updateRequest.getEmail() + "' is already in use by another user.");
            }
            user.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getRoles() != null && !updateRequest.getRoles().isEmpty()) {
            Set<Role> assignedRoles = new HashSet<>();
            for (String roleNameStr : updateRequest.getRoles()) {
                RoleType roleType;
                try {
                    roleType = RoleType.valueOf(roleNameStr.trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new BadRequestException("Invalid role specified: " + roleNameStr);
                }
                Role role = roleRepository.findByRole(roleType) // <<<< CHANGED to findByRole
                        .orElseThrow(() -> new ResourceNotFoundException("Role", "type", roleNameStr));
                assignedRoles.add(role);
            }
            user.setRoles(assignedRoles.stream().collect(Collectors.toList()));
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void changeUserPassword(Long userId, UserPasswordUpdateRequest passwordUpdateRequest) {
        User user = getUserById(userId);
        if (!passwordEncoder.matches(passwordUpdateRequest.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Incorrect current password.");
        }
        if (passwordEncoder.matches(passwordUpdateRequest.getNewPassword(), user.getPassword())) {
            throw new BadRequestException("New password cannot be the same as the current password.");
        }
        user.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        List<Task> tasksOwned = taskRepository.findByOwner(user);
        if (tasksOwned != null && !tasksOwned.isEmpty()) {
            tasksOwned.forEach(task -> task.setOwner(null));
            taskRepository.saveAll(tasksOwned);
        }
        userRepository.delete(user);
    }
}