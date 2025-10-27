package com.ME.job_management_system.controller;

import com.ME.job_management_system.dto.UpdateRoleRequest;
import com.ME.job_management_system.dto.UserResponse;
import com.ME.job_management_system.entity.User;
import com.ME.job_management_system.entity.UserRole;
import com.ME.job_management_system.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Convert User entity to UserResponse DTO
    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return adminService.getAllUsers()
                .stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = adminService.getUserById(id);
        return ResponseEntity.ok(convertToUserResponse(user));
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<UserResponse> updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRoleRequest request) {
        User updatedUser = adminService.updateUserRole(id, request.getRole());
        return ResponseEntity.ok(convertToUserResponse(updatedUser));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/role/{role}")
    public List<UserResponse> getUsersByRole(@PathVariable UserRole role) {
        return adminService.getUsersByRole(role)
                .stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/statistics/users")
    public Map<String, Object> getUserStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", adminService.getUsersCount());
        stats.put("adminUsers", adminService.getUsersCountByRole(UserRole.ADMIN));
        stats.put("employerUsers", adminService.getUsersCountByRole(UserRole.EMPLOYER));
        stats.put("regularUsers", adminService.getUsersCountByRole(UserRole.USER));
        return stats;
    }
}