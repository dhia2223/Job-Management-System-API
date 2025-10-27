package com.ME.job_management_system.service;

import com.ME.job_management_system.dto.JobCreateRequest;
import com.ME.job_management_system.dto.JobResponse;
import com.ME.job_management_system.dto.JobUpdateRequest;
import com.ME.job_management_system.entity.Job;
import com.ME.job_management_system.entity.JobType;
import com.ME.job_management_system.exception.ResourceNotFoundException;
import com.ME.job_management_system.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
        return response;
    }

    // Convert Create Request DTO to Entity
    private Job convertToEntity(JobCreateRequest request) {
        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setJobType(request.getJobType());
        return job;
    }

    // Get all jobs
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Get job by ID
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));
        return convertToResponse(job);
    }

    // Create new job
    public JobResponse createJob(JobCreateRequest jobRequest) {
        Job job = convertToEntity(jobRequest);
        Job savedJob = jobRepository.save(job);
        return convertToResponse(savedJob);
    }

    // Update existing job with partial updates
    public JobResponse updateJob(Long id, JobUpdateRequest jobRequest) {
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));

        // Update only the provided fields (partial update)
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

    // Delete job
    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));
        jobRepository.delete(job);
    }

    // Search jobs by company
    public List<JobResponse> getJobsByCompany(String company) {
        List<Job> jobs = jobRepository.findByCompany(company);
        if (jobs.isEmpty()) {
            throw new ResourceNotFoundException("Jobs", "company", company);
        }
        return jobs.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Search jobs by location
    public List<JobResponse> getJobsByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Search by title
    public List<JobResponse> getJobsByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Search by job type
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
}