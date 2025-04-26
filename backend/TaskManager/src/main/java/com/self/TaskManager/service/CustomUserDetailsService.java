package com.self.TaskManager.service;

import com.self.TaskManager.model.User;  // <-- YOUR User model here
import com.self.TaskManager.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()   // <-- user.getRoles() (List<Role>)
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole())) // assuming Role has getName()
                        .toList()
        );
    }

}
