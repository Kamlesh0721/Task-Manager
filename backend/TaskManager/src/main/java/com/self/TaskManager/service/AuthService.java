// src/main/java/com/self/TaskManager/service/AuthService.java
package com.self.TaskManager.service;

// Assuming your DTOs are structured like this based on previous discussions:
import com.self.TaskManager.dto.requests.AuthRequest;
import com.self.TaskManager.dto.responses.AuthResponse; // Ensure this import is correct
import com.self.TaskManager.dto.requests.UserRegistrationRequest;
import com.self.TaskManager.dto.responses.UserProfileResponse;

public interface AuthService {

    /**
     * Authenticates a user based on the provided login request.
     *
     * @param authRequest DTO containing email and password.
     * @return AuthResponse DTO containing JWT token and user details.
     * @throws org.springframework.security.core.AuthenticationException if authentication fails.
     * @throws com.self.TaskManager.exceptions.ResourceNotFoundException if the user doesn't exist (can be thrown by UserDetailsService).
     */
    AuthResponse loginUser(AuthRequest authRequest); // <<<< FIXED: Removed "requests."

    /**
     * Registers a new user based on the provided registration request.
     *
     * @param registrationRequest DTO containing user details for registration.
     * @return UserProfileResponse DTO representing the newly registered user.
     * @throws com.self.TaskManager.exceptions.UserAlreadyExistsException if email is already taken.
     * @throws com.self.TaskManager.exceptions.ResourceNotFoundException if a specified role doesn't exist.
     * @throws com.self.TaskManager.exceptions.BadRequestException if the request is malformed (e.g., invalid role name).
     */
    UserProfileResponse registerUser(UserRegistrationRequest registrationRequest);
}