package com.ME.job_management_system.controller;

import com.ME.job_management_system.dto.JobCreateRequest;
import com.ME.job_management_system.dto.JobResponse;
import com.ME.job_management_system.dto.JobUpdateRequest;
import com.ME.job_management_system.entity.JobType;
import com.ME.job_management_system.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public List<JobResponse> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        JobResponse job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @PostMapping
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobCreateRequest jobRequest) {
        JobResponse createdJob = jobService.createJob(jobRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long id, @Valid @RequestBody JobUpdateRequest jobRequest) {
        JobResponse updatedJob = jobService.updateJob(id, jobRequest);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/company")
    public List<JobResponse> getJobsByCompany(@RequestParam String company) {
        return jobService.getJobsByCompany(company);
    }

    @GetMapping("/search/location")
    public List<JobResponse> getJobsByLocation(@RequestParam String location) {
        return jobService.getJobsByLocation(location);
    }

    @GetMapping("/search/title")
    public List<JobResponse> getJobsByTitle(@RequestParam String title) {
        return jobService.getJobsByTitle(title);
    }

    @GetMapping("/search/job-type")
    public List<JobResponse> getJobsByJobType(@RequestParam JobType jobType) {
        return jobService.getJobsByJobType(jobType);
    }

    @GetMapping("/statistics/count")
    public Map<String, Object> getJobsStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalJobs", jobService.getTotalJobsCount());
        stats.put("fullTimeJobs", jobService.getJobsCountByJobType(JobType.FULL_TIME));
        stats.put("partTimeJobs", jobService.getJobsCountByJobType(JobType.PART_TIME));
        stats.put("contractJobs", jobService.getJobsCountByJobType(JobType.CONTRACT));
        stats.put("internshipJobs", jobService.getJobsCountByJobType(JobType.INTERNSHIP));
        stats.put("remoteJobs", jobService.getJobsCountByJobType(JobType.REMOTE));
        return stats;
    }

    @GetMapping("/statistics/company/{company}")
    public Map<String, Object> getCompanyStatistics(@PathVariable String company) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("company", company);
        stats.put("totalJobs", jobService.getJobsCountByCompany(company));
        stats.put("jobs", jobService.getJobsByCompany(company));
        return stats;
    }
}