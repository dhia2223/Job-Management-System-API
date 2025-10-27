package com.ME.job_management_system.repository;

import com.ME.job_management_system.entity.Job;
import com.ME.job_management_system.entity.JobType;
import com.ME.job_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByCompany(String company);
    List<Job> findByLocationContainingIgnoreCase(String location);
    List<Job> findByTitleContainingIgnoreCase(String keyword);
    List<Job> findByJobType(JobType jobType);

    // User-specific queries
    List<Job> findByCreatedBy(User user);
    long countByCreatedBy(User user);

    // Count methods for statistics
    long count();
    long countByCompany(String company);
    long countByJobType(JobType jobType);
}