package com.ME.job_management_system.service;

import com.ME.job_management_system.dto.JobCreateRequest;
import com.ME.job_management_system.dto.JobResponse;
import com.ME.job_management_system.dto.JobUpdateRequest;
import com.ME.job_management_system.entity.Job;
import com.ME.job_management_system.entity.JobType;
import com.ME.job_management_system.entity.User;
import com.ME.job_management_system.entity.UserRole;
import com.ME.job_management_system.exception.ResourceNotFoundException;
import com.ME.job_management_system.repository.JobRepository;
import com.ME.job_management_system.util.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Convert Entity to Response DTO
    private JobResponse convertToResponse(Job job) {
        JobResponse response = new JobResponse();
        response.setId(job.getId());
        response.setTitle(job.getTitle());
        response.setDescription(job.getDescription());
        response.setCompany(job.getCompany());
        response.setLocation(job.getLocation());
        response.setSalary(job.getSalary());
        response.setJobType(job.getJobType());
        response.setCreatedAt(job.getCreatedAt());
        response.setUpdatedAt(job.getUpdatedAt());
        response.setCreatedBy(job.getCreatedBy().getEmail()); // Include creator info
        return response;
    }

    // Convert Create Request DTO to Entity
    private Job convertToEntity(JobCreateRequest request) {
        User currentUser = SecurityUtil.getCurrentUser();
        return new Job(
                request.getTitle(),
                request.getDescription(),
                request.getCompany(),
                request.getLocation(),
                request.getSalary(),
                request.getJobType(),
                currentUser
        );
    }

    // Get all jobs - accessible to all authenticated users
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Get job by ID - accessible to all authenticated users
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));
        return convertToResponse(job);
    }

    // Create new job - only employers and admins can create jobs
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
    public JobResponse createJob(JobCreateRequest jobRequest) {
        Job job = convertToEntity(jobRequest);
        Job savedJob = jobRepository.save(job);
        return convertToResponse(savedJob);
    }

    // Update existing job - only job creator or admin can update
    public JobResponse updateJob(Long id, JobUpdateRequest jobRequest) {
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));

        // Check if current user is the creator or admin
        User currentUser = SecurityUtil.getCurrentUser();
        if (!existingJob.getCreatedBy().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().equals(UserRole.ADMIN)) { // Enhanced admin check
            throw new RuntimeException("You can only update your own jobs");
        }

        // Update only the provided fields
        if (jobRequest.getTitle() != null) {
            existingJob.setTitle(jobRequest.getTitle());
        }
        if (jobRequest.getDescription() != null) {
            existingJob.setDescription(jobRequest.getDescription());
        }
        if (jobRequest.getCompany() != null) {
            existingJob.setCompany(jobRequest.getCompany());
        }
        if (jobRequest.getLocation() != null) {
            existingJob.setLocation(jobRequest.getLocation());
        }
        if (jobRequest.getSalary() != null) {
            existingJob.setSalary(jobRequest.getSalary());
        }
        if (jobRequest.getJobType() != null) {
            existingJob.setJobType(jobRequest.getJobType());
        }

        Job updatedJob = jobRepository.save(existingJob);
        return convertToResponse(updatedJob);
    }

    // Delete job - only job creator or admin can delete
    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));

        // Check if current user is the creator or admin
        User currentUser = SecurityUtil.getCurrentUser();
        if (!job.getCreatedBy().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().equals(UserRole.ADMIN)) { // Enhanced admin check
            throw new RuntimeException("You can only delete your own jobs");
        }

        jobRepository.delete(job);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public JobResponse getAnyJob(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));
        return convertToResponse(job);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAnyJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new ResourceNotFoundException("Job", "id", id);
        }
        jobRepository.deleteById(id);
    }

    // Get jobs created by current user
    public List<JobResponse> getMyJobs() {
        User currentUser = SecurityUtil.getCurrentUser();
        return jobRepository.findByCreatedBy(currentUser)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Search jobs by company - accessible to all
    public List<JobResponse> getJobsByCompany(String company) {
        List<Job> jobs = jobRepository.findByCompany(company);
        if (jobs.isEmpty()) {
            throw new ResourceNotFoundException("Jobs", "company", company);
        }
        return jobs.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Search jobs by location - accessible to all
    public List<JobResponse> getJobsByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Search by title - accessible to all
    public List<JobResponse> getJobsByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Search by job type - accessible to all
    public List<JobResponse> getJobsByJobType(JobType jobType) {
        List<Job> jobs = jobRepository.findByJobType(jobType);
        if (jobs.isEmpty()) {
            throw new ResourceNotFoundException("Jobs", "jobType", jobType);
        }
        return jobs.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Get total jobs count
    public Long getTotalJobsCount() {
        return jobRepository.count();
    }

    // Get jobs count by company
    public long getJobsCountByCompany(String company) {
        return jobRepository.countByCompany(company);
    }

    // Get jobs count by job type
    public long getJobsCountByJobType(JobType jobType) {
        return jobRepository.countByJobType(jobType);
    }

    // Get current user's jobs count
    public long getMyJobsCount() {
        User currentUser = SecurityUtil.getCurrentUser();
        return jobRepository.countByCreatedBy(currentUser);
    }
}