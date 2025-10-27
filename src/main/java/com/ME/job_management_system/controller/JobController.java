package com.ME.job_management_system.controller;

import com.ME.job_management_system.dto.JobCreateRequest;
import com.ME.job_management_system.dto.JobResponse;
import com.ME.job_management_system.dto.JobUpdateRequest;
import com.ME.job_management_system.entity.JobType;
import com.ME.job_management_system.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/jobs")
@Tag(name = "Jobs", description = "Job management APIs")
@SecurityRequirement(name = "bearerAuth")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @Operation(summary = "Get all jobs", description = "Retrieve a list of all available jobs")
    @GetMapping
    public List<JobResponse> getAllJobs() {
        return jobService.getAllJobs();
    }

    @Operation(summary = "Get job by ID", description = "Retrieve a specific job by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        JobResponse job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @Operation(summary = "Create Job", description = "Create Job")
    @PostMapping
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobCreateRequest jobRequest) {
        JobResponse createdJob = jobService.createJob(jobRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }

    @Operation(summary = "Update Job", description = "Update Job")
    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long id, @Valid @RequestBody JobUpdateRequest jobRequest) {
        JobResponse updatedJob = jobService.updateJob(id, jobRequest);
        return ResponseEntity.ok(updatedJob);
    }


    @Operation(summary = "Delete Job", description = "Delete Job")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    // New endpoint: Get current user's jobs
    @Operation(summary = "Get my jobs", description = "Retrieve a list of all available jobs created by current user")
    @GetMapping("/my-jobs")
    public List<JobResponse> getMyJobs() {
        return jobService.getMyJobs();
    }

    @Operation (summary = "Get jobs count By Company", description = "Retrieve total number of jobs")
    @GetMapping("/search/company")
    public List<JobResponse> getJobsByCompany(@RequestParam String company) {
        return jobService.getJobsByCompany(company);
    }

    @Operation (summary = "Get jobs count By Location", description = "Retrieve total number of jobs")
    @GetMapping("/search/location")
    public List<JobResponse> getJobsByLocation(@RequestParam String location) {
        return jobService.getJobsByLocation(location);
    }

    @Operation (summary = "Get jobs count By Title", description = "Retrieve total number of jobs")
    @GetMapping("/search/title")
    public List<JobResponse> getJobsByTitle(@RequestParam String title) {
        return jobService.getJobsByTitle(title);
    }

    @Operation (summary = "Get jobs count By Job Type", description = "Retrieve total number of jobs")
    @GetMapping("/search/job-type")
    public List<JobResponse> getJobsByJobType(@RequestParam JobType jobType) {
        return jobService.getJobsByJobType(jobType);
    }

    @Operation (summary = "Get jobs JobsStatistics", description = "Get jobs JobsStatistics")
    @GetMapping("/statistics/count")
    public Map<String, Object> getJobsStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalJobs", jobService.getTotalJobsCount());
        stats.put("fullTimeJobs", jobService.getJobsCountByJobType(JobType.FULL_TIME));
        stats.put("partTimeJobs", jobService.getJobsCountByJobType(JobType.PART_TIME));
        stats.put("contractJobs", jobService.getJobsCountByJobType(JobType.CONTRACT));
        stats.put("internshipJobs", jobService.getJobsCountByJobType(JobType.INTERNSHIP));
        stats.put("remoteJobs", jobService.getJobsCountByJobType(JobType.REMOTE));
        stats.put("myJobs", jobService.getMyJobsCount());
        return stats;
    }

    @Operation (summary = "Get Company Statistics", description = "Get Company Statistics")
    @GetMapping("/statistics/company/{company}")
    public Map<String, Object> getCompanyStatistics(@PathVariable String company) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("company", company);
        stats.put("totalJobs", jobService.getJobsCountByCompany(company));
        stats.put("jobs", jobService.getJobsByCompany(company));
        return stats;
    }
}