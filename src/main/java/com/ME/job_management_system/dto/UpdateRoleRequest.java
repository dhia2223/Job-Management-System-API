package com.ME.job_management_system.dto;

import com.ME.job_management_system.entity.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRoleRequest {
    @NotNull(message = "Role is required")
    private UserRole role;
}