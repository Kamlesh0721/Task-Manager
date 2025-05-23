package com.self.TaskManager.controller;

import com.self.TaskManager.dto.AuthRequest;
import com.self.TaskManager.dto.AuthResponse;
import com.self.TaskManager.dto.RegisterRequest;
import com.self.TaskManager.exceptions.UserAlreadyExistsException;
import com.self.TaskManager.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            authService.registerUser(registerRequest);
            return ResponseEntity.ok("User registered successfully!");
        } catch (UserAlreadyExistsException e) {
            // These exceptions are already annotated with @ResponseStatus(HttpStatus.BAD_REQUEST)
            // so Spring will handle the status code. We just return the message.
            // Alternatively, you could use a @ControllerAdvice for more centralized exception handling.
            return ResponseEntity
                    .badRequest() // or .status(HttpStatus.CONFLICT) if you prefer 409
                    .body(e.getMessage());
        } catch (Exception e) { // Catch any other unexpected errors
            // Log the exception e.g. log.error("Unexpected error during registration", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred during registration.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
        // AuthenticationManager will throw Spring Security's AuthenticationException
        // which can be handled by a global exception handler or Spring Security's defaults.
        // If login is successful, authService will return an AuthResponse.
        AuthResponse authResponse = authService.authenticateUser(authRequest);
        return ResponseEntity.ok(authResponse);
    }
}