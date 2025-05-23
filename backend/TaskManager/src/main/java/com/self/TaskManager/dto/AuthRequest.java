package com.self.TaskManager.dto;


public class AuthRequest {
    private String userEmail;
    private String password;

    // Getters and Setters
    public AuthRequest() {
    }

    public AuthRequest(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String usernameOrEmail) {
        this.userEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
