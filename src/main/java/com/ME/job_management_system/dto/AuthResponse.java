package com.ME.job_management_system.dto;

import com.ME.job_management_system.entity.UserRole;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;

    public AuthResponse(String token, String email, String firstName, String lastName, UserRole role) {
        this.token = token;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}