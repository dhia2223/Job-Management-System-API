package com.ME.job_management_system.dto;

import com.ME.job_management_system.entity.JobType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobResponse {
    private Long id;
    private String title;
    private String description;
    private String company;
    private String location;
    private Double salary;
    private JobType jobType;
    private String createdBy; // Creator's email
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}