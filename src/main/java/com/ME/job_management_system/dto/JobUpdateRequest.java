package com.ME.job_management_system.dto;

import com.ME.job_management_system.entity.JobType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JobUpdateRequest {

    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    private String company;

    @Size(min = 2, max = 100, message = "Location must be between 2 and 100 characters")
    private String location;

    @Min(value = 0, message = "Salary must be positive")
    @Max(value = 1000000, message = "Salary must be reasonable")
    private Double salary;

    private JobType jobType;
}