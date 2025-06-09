package com.self.TaskManager.controller;

import com.self.TaskManager.model.Role;
import com.self.TaskManager.model.RoleType;
import com.self.TaskManager.model.User;
import com.self.TaskManager.repository.RoleRepository;
import com.self.TaskManager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder; // Use PasswordEncoder interface
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional; // Recommended for DB operations

import java.util.ArrayList;
import java.util.List;


@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder; // Changed to PasswordEncoder interface

    public DataLoader(UserRepository userRepository,
                      RoleRepository roleRepository,
                      PasswordEncoder passwordEncoder) { // Inject PasswordEncoder
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional // Good practice to make run method transactional if doing multiple DB ops
    public void run(String... args) throws Exception {
        System.out.println("DataLoader running...");

        // --- Ensure Roles Exist ---
        Role adminRole = roleRepository.findByRole(RoleType.ADMIN)
                .orElseGet(() -> {
                    System.out.println(RoleType.ADMIN + " not found, creating...");
                    return roleRepository.save(new Role(RoleType.ADMIN));
                });

        Role userRole = roleRepository.findByRole(RoleType.USER)
                .orElseGet(() -> {
                    System.out.println(RoleType.USER + " not found, creating...");
                    return roleRepository.save(new Role(RoleType.USER));
                });


        // --- Create Admin User if it doesn't exist ---
        String adminEmail = "admin@taskmanager.com";
        if (!userRepository.existsByEmail(adminEmail)) {
            User adminUser = new User();
            adminUser.setName("Super Admin");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode("Admin@123")); // Use injected encoder

            // Assign roles. User entity expects List<Role>
            List<Role> adminUserRoles = new ArrayList<>();
            adminUserRoles.add(adminRole);
            adminUserRoles.add(userRole); // Admin can also have USER role capabilities
            adminUser.setRoles(adminUserRoles);

            userRepository.save(adminUser);
            System.out.println("Created admin user: " + adminEmail);
        } else {
            System.out.println("Admin user " + adminEmail + " already exists.");
        }

        // --- Create a Sample User if it doesn't exist (Optional) ---
        String sampleUserEmail = "testuser@taskmanager.com";
        if (!userRepository.existsByEmail(sampleUserEmail)) {
            User sampleUser = new User();
            sampleUser.setName("Test User");
            sampleUser.setEmail(sampleUserEmail);
            sampleUser.setPassword(passwordEncoder.encode("testUser@123"));

            List<Role> sampleUserRoles = new ArrayList<>();
            sampleUserRoles.add(userRole);
            sampleUser.setRoles(sampleUserRoles);

            userRepository.save(sampleUser);
            System.out.println("Created sample user: " + sampleUserEmail);
        } else {
            System.out.println("Sample user " + sampleUserEmail + " already exists.");
        }

        System.out.println("DataLoader finished.");
    }
}