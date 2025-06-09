// src/main/java/com/self/TaskManager/service/UserService.java
package com.self.TaskManager.service;

import com.self.TaskManager.dto.requests.AdminUserUpdateRequest;
import com.self.TaskManager.dto.requests.UserProfileUpdateRequest;
import com.self.TaskManager.dto.requests.UserRegistrationRequest;
import com.self.TaskManager.dto.requests.UserPasswordUpdateRequest;
import com.self.TaskManager.model.User;

import java.util.List;

public interface UserService {

    User registerUser(UserRegistrationRequest registrationRequest); // Called by AuthService

    User getUserById(Long id);

    User getUserByEmail(String email);

    List<User> findAllUsers();

    User updateUserProfile(Long userId, UserProfileUpdateRequest updateRequest);

    User updateUserByAdmin(Long userId, AdminUserUpdateRequest updateRequest);

    void changeUserPassword(Long userId, UserPasswordUpdateRequest passwordUpdateRequest);

    void deleteUser(Long userId);
}