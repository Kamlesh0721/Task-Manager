// src/main/java/com/self/TaskManager/service/impl/AuthServiceImpl.java
package com.self.TaskManager.service.impl;

import com.self.TaskManager.dto.requests.AuthRequest;
import com.self.TaskManager.dto.responses.AuthResponse;
import com.self.TaskManager.dto.requests.UserRegistrationRequest;
import com.self.TaskManager.dto.responses.UserProfileResponse;
import com.self.TaskManager.model.User;
import com.self.TaskManager.mapper.UserMapper; // Required for mapping
import com.self.TaskManager.security.JWTUtil;
import com.self.TaskManager.security.services.UserDetailsImpl;
import com.self.TaskManager.service.AuthService;
import com.self.TaskManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserService userService,
                           JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse loginUser(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetailsPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetailsPrincipal);

        List<String> roles = userDetailsPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new AuthResponse(
                jwt,
                userDetailsPrincipal.getId(),
                userDetailsPrincipal.getUsername(), // Email
                userDetailsPrincipal.getName(),     // Display name
                roles
        );
    }

    @Override
    @Transactional
    public UserProfileResponse registerUser(UserRegistrationRequest registrationRequest) {
        User registeredUser = userService.registerUser(registrationRequest);
        return UserMapper.toUserProfileResponse(registeredUser);
    }
}