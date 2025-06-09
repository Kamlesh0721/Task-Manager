// src/main/java/com/self/TaskManager/controller/AuthController.java
package com.self.TaskManager.controller;

import com.self.TaskManager.dto.requests.AuthRequest;
import com.self.TaskManager.dto.responses.AuthResponse;
import com.self.TaskManager.dto.requests.UserRegistrationRequest;
import com.self.TaskManager.dto.responses.UserProfileResponse;
import com.self.TaskManager.service.AuthService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody AuthRequest loginRequest) {
        // AuthenticationException thrown by AuthenticationManager (via AuthService)
        // will be handled by GlobalExceptionHandler or AuthEntryPointJwt.
        AuthResponse authResponse = authService.loginUser(loginRequest);
        logger.info("User [{}] logged in successfully.", loginRequest.getEmail());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserProfileResponse> registerUser(@Valid @RequestBody UserRegistrationRequest registrationRequest) {
        // Exceptions like UserAlreadyExistsException, BadRequestException (for invalid role),
        // or ResourceNotFoundException (if a role isn't found) thrown by AuthService/UserService
        // will propagate to GlobalExceptionHandler.
        UserProfileResponse userProfile = authService.registerUser(registrationRequest);
        logger.info("User [{}] registered successfully.", registrationRequest.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfile);
    }
}