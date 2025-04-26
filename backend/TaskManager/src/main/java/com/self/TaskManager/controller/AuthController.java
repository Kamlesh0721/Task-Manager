package com.self.TaskManager.controller;

import com.self.TaskManager.dto.AuthRequest;
import com.self.TaskManager.security.JWTUtil;
import com.self.TaskManager.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthController(JWTUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestBody AuthRequest loginRequest) {
        // Load full UserDetails by username
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        // Generate token with full UserDetails
        return jwtUtil.generateToken(userDetails);
    }
}

