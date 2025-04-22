package com.self.TaskManager.service;

import com.self.TaskManager.model.Role;
import com.self.TaskManager.model.User;
import com.self.TaskManager.repository.RoleRepository;
import com.self.TaskManager.repository.TaskRepository;
import com.self.TaskManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private static final String ADMIN="ADMIN";
    private static final String USER="USER";

    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                           TaskRepository taskRepository,
                           RoleRepository roleRepository
                         ) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.roleRepository = roleRepository;

    }

    public User createUser(User user) {
        Role userRole = roleRepository.findByRole(USER);
        user.setRoles(new ArrayList<>(Collections.singletonList(userRole)));
        return userRepository.save(user);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isUserEmailPresent(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        User user = userRepository.getOne(id);
        user.getTasksOwned().forEach(task -> task.setOwner(null));
        userRepository.delete(user);
    }
}