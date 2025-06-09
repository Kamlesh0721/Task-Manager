package com.self.TaskManager.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.self.TaskManager.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    private Long id;         // Your application-specific user ID
    private String name;       // User's display name (or full name)
    private String email;      // User's email, which will be used as the "username" for Spring Security

    @JsonIgnore // Prevents password from being serialized if this object is ever sent in a response (shouldn't be)
    private String password;   // User's encoded password

    private Collection<? extends GrantedAuthority> authorities; // User's roles/permissions

    // Constructor
    public UserDetailsImpl(Long id, String name, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Static factory method to create an instance of UserDetailsImpl from your User entity.
     * This is typically called by your UserDetailsService (e.g., CustomUserDetailsService).
     */
    public static UserDetailsImpl build(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null when building UserDetailsImpl");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> {
                    // Assuming your Role entity has a method `getRole()` or `getName()`
                    // that returns your RoleType enum (e.g., RoleType.USER, RoleType.ADMIN).
                    // And your RoleType enum's .name() method returns the string "USER", "ADMIN", etc.
                    String roleNameEnumString = role.getRole().name(); // e.g., from role.getRole() -> RoleType.USER -> "USER"
                    return new SimpleGrantedAuthority("ROLE_" + roleNameEnumString); // Becomes "ROLE_USER"
                })
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getName(),       // From your User entity
                user.getEmail(),      // From your User entity
                user.getPassword(),   // Encoded password from your User entity
                authorities);
    }

    // --- Custom Getters for your application-specific fields ---
    public Long getId() {
        return id;
    }

    public String getName() {
        return name; // Returns the display name
    }

    // You can add an explicit getEmail() if you want, though getUsername() serves a similar purpose for Spring Security.
    public String getEmail() {
        return email;
    }


    // --- Implementation of UserDetails interface methods ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        // Spring Security uses this method to get the "username" for authentication.
        // We are mapping the user's email to be this username.
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // You can make this dynamic based on your User entity if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // You can make this dynamic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // You can make this dynamic
    }

    @Override
    public boolean isEnabled() {
        // Example: if your User entity has an 'enabled' field:
        // return userEntity.isEnabled();
        return true; // Default to true, or make it dynamic
    }

    // --- equals() and hashCode() are important if you store UserDetailsImpl in collections or compare them ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(id, that.id); // Typically, equality is based on the unique ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Hash based on the unique ID
    }
}