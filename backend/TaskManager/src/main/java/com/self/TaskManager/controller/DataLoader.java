package com.self.TaskManager.controller;

import com.self.TaskManager.model.Role;
import com.self.TaskManager.model.RoleType;
import com.self.TaskManager.model.User;
import com.self.TaskManager.repository.RoleRepository;
import com.self.TaskManager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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
        // Check if roles exist and create if not
        Role adminRole = roleRepository.findByRole(RoleType.ROLE_ADMIN).orElse(null);
        if (adminRole == null) {
            adminRole = new Role(RoleType.ROLE_ADMIN);
            roleRepository.save(adminRole);
        }

        Role userRole = roleRepository.findByRole(RoleType.ROLE_USER).orElse(null);
        if (userRole == null) {
            userRole = new Role(RoleType.ROLE_USER);
            roleRepository.save(userRole);
        }

        // Add an admin user if not already present
        if (userRepository.findAll().isEmpty()) {
            User admin = new User();
            admin.setName("Super Admin");
            admin.setEmail("admin@taskmanager.com");
            admin.setPassword(bCryptPasswordEncoder.encode("admin123")); // In a real project, make sure to encode the password

            // Assign the "ADMIN" role to the admin user
            admin.getRoles().add(adminRole);  // Make sure roles list is not null

            userRepository.save(admin);
        }
    }
}
