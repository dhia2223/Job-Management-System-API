package com.ME.job_management_system.repository;


import com.ME.job_management_system.entity.Job;
import com.ME.job_management_system.entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Spring Data JPA automatically implements these methods!
    List<Job> findByCompany(String company);
    List<Job> findByLocationContainingIgnoreCase(String location);
    List<Job> findByTitleContainingIgnoreCase(String title);

    List<Job> findByJobType(JobType jobType);


    // Count methods for statistics
    long count();
    long countByCompany(String company);
    long countByJobType(JobType jobType);
}