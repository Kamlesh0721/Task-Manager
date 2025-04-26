package com.self.TaskManager.service;

import com.self.TaskManager.dto.UserDTO;
import com.self.TaskManager.mapper.UserMapper;
import com.self.TaskManager.model.Role;
import com.self.TaskManager.model.RoleType;
import com.self.TaskManager.model.User;
import com.self.TaskManager.repository.RoleRepository;
import com.self.TaskManager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {
        // Check if username already exists
        if (userRepository.findByName(userDTO.getName()).isPresent()) {
            throw new RuntimeException("Username is already taken!");
        }

        User user = UserMapper.toEntity(userDTO);

        // Assign "USER" role by default
        Role userRole = roleRepository.findByRole(RoleType.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(RoleType.ROLE_USER)));

        user.setRoles(Collections.singletonList(userRole));

        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }


    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(UserMapper::toDTO).orElse(null);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        Optional<User> existingUserOpt = userRepository.findById(userId);
        if (existingUserOpt.isPresent()) {
            User user = existingUserOpt.get();
            user.setEmail(userDTO.getEmail());
            user.setName(userDTO.getName());
            user.setPassword(userDTO.getPassword());
            User updated = userRepository.save(user);
            return UserMapper.toDTO(updated);
        }
        return null;
    }
}
