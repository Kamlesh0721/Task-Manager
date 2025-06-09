// src/main/java/com/self/TaskManager/controller/UserController.java
package com.self.TaskManager.controller;


import com.self.TaskManager.dto.requests.AdminUserUpdateRequest;
import com.self.TaskManager.dto.requests.UserPasswordUpdateRequest;
import com.self.TaskManager.dto.requests.UserProfileUpdateRequest;
import com.self.TaskManager.dto.responses.DetailedUserResponse;
import com.self.TaskManager.dto.responses.UserProfileResponse;
import com.self.TaskManager.dto.responses.UserSummaryResponse;
import com.self.TaskManager.model.User;
import com.self.TaskManager.mapper.UserMapper;
import com.self.TaskManager.security.services.UserDetailsImpl;
import com.self.TaskManager.service.UserService;
import com.self.TaskManager.exceptions.BadRequestException; // For admin self-delete attempt


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private UserDetailsImpl getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            return (UserDetailsImpl) authentication.getPrincipal();
        }
        logger.warn("Could not retrieve UserDetailsImpl from security context. Principal: {}",
                (authentication != null ? authentication.getPrincipal() : "null"));
        // This state might indicate an issue or an unauthenticated request that slipped through.
        // Depending on @PreAuthorize, this might not be hit for secured endpoints.
        return null;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileResponse> getCurrentUser() {
        UserDetailsImpl userDetails = getCurrentUserDetails();
        if (userDetails == null) { // Should be caught by @PreAuthorize if not authenticated
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.getUserById(userDetails.getId());
        logger.info("Fetched profile for user ID: {}", userDetails.getId());
        return ResponseEntity.ok(UserMapper.toUserProfileResponse(user));
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileResponse> updateCurrentUserProfile(@Valid @RequestBody UserProfileUpdateRequest updateRequest) {
        UserDetailsImpl userDetails = getCurrentUserDetails();
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User updatedUser = userService.updateUserProfile(userDetails.getId(), updateRequest);
        logger.info("Updated profile for user ID: {}", userDetails.getId());
        return ResponseEntity.ok(UserMapper.toUserProfileResponse(updatedUser));
    }

    @PutMapping("/me/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changeCurrentUserPassword(@Valid @RequestBody UserPasswordUpdateRequest passwordUpdateRequest) {
        UserDetailsImpl userDetails = getCurrentUserDetails();
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.changeUserPassword(userDetails.getId(), passwordUpdateRequest);
        logger.info("Password changed for user ID: {}", userDetails.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserSummaryResponse>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        logger.info("Admin fetched all users. Count: {}", users.size());
        return ResponseEntity.ok(UserMapper.toUserSummaryResponseList(users));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<DetailedUserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        logger.info("Fetched user details for ID: {}", id);
        return ResponseEntity.ok(UserMapper.toDetailedUserResponse(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DetailedUserResponse> updateUserByAdmin(@PathVariable Long id, @Valid @RequestBody AdminUserUpdateRequest updateRequest) {
        User updatedUser = userService.updateUserByAdmin(id, updateRequest);
        logger.info("Admin updated user details for ID: {}", id);
        return ResponseEntity.ok(UserMapper.toDetailedUserResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        UserDetailsImpl currentUser = getCurrentUserDetails();
        if (currentUser != null && currentUser.getId().equals(id)) {
            // Throwing an exception is cleaner as GlobalExceptionHandler will handle it consistently.
            throw new BadRequestException("Admin users cannot delete their own account using this endpoint.");
        }
        userService.deleteUser(id);
        logger.info("Admin deleted user with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}