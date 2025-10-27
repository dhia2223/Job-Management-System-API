package com.ME.job_management_system.dto;

import com.ME.job_management_system.entity.JobType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class JobCreateRequest {

    @NotBlank(message = "Job title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Job description is required")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    private String company;

    @NotBlank(message = "Location is required")
    @Size(min = 2, max = 100, message = "Location must be between 2 and 100 characters")
    private String location;

    @Min(value = 0, message = "Salary must be positive")
    @Max(value = 1000000, message = "Salary must be reasonable")
    private Double salary;

    @NotNull(message = "Job type is required")
    private JobType jobType;
}