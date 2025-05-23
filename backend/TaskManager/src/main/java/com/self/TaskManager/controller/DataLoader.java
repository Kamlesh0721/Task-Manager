package com.self.TaskManager.controller;

import com.self.TaskManager.model.Role;
import com.self.TaskManager.model.RoleType;
import com.self.TaskManager.model.User;
import com.self.TaskManager.repository.RoleRepository;
import com.self.TaskManager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Ensure roles exist
        Role adminRole = roleRepository.findByRole(RoleType.ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(RoleType.ADMIN)));

        Role userRole = roleRepository.findByRole(RoleType.USER)
                .orElseGet(() -> roleRepository.save(new Role(RoleType.USER)));

        // Create admin user if users table is empty
        if (userRepository.findAll().isEmpty()) {
            User admin = new User();
            admin.setName("Super Admin");
            admin.setEmail("admin@taskmanager.com");
            admin.setPassword(bCryptPasswordEncoder.encode("admin123"));

           List<Role> roles = new ArrayList<>();
            roles.add(adminRole);
            roles.add(userRole); // Add both roles to admin
            admin.setRoles(roles);

            userRepository.save(admin);
        }
    }
}
