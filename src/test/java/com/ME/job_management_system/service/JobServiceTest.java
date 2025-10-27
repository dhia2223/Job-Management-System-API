package com.ME.job_management_system.service;

import com.ME.job_management_system.dto.JobCreateRequest;
import com.ME.job_management_system.dto.JobResponse;
import com.ME.job_management_system.entity.Job;
import com.ME.job_management_system.entity.JobType;
import com.ME.job_management_system.entity.User;
import com.ME.job_management_system.entity.UserRole;
import com.ME.job_management_system.exception.ResourceNotFoundException;
import com.ME.job_management_system.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    private User testUser;
    private User otherUser;
    private Job testJob;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setRole(UserRole.EMPLOYER);

        otherUser = new User();
        otherUser.setId(2L);
        otherUser.setRole(UserRole.USER);

        testJob = new Job();
        testJob.setId(1L);
        testJob.setTitle("Test Job");
        testJob.setDescription("Test Description");
        testJob.setCompany("Test Company");
        testJob.setLocation("Test Location");
        testJob.setSalary(50000.0);
        testJob.setJobType(JobType.FULL_TIME);
        testJob.setCreatedBy(testUser);
    }

    @Test
    void getAllJobs_ShouldReturnListOfJobs() {
        // Arrange
        when(jobRepository.findAll()).thenReturn(List.of(testJob));

        // Act
        List<JobResponse> result = jobService.getAllJobs();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Job", result.get(0).getTitle());
    }

    @Test
    void getJobById_WithValidId_ShouldReturnJob() {
        // Arrange
        when(jobRepository.findById(1L)).thenReturn(Optional.of(testJob));

        // Act
        JobResponse result = jobService.getJobById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Job", result.getTitle());
    }

    @Test
    void getJobById_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(jobRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> jobService.getJobById(999L));
    }

    @Test
    void createJob_WithValidRequest_ShouldCreateJob() {
        // Arrange
        JobCreateRequest request = new JobCreateRequest();
        request.setTitle("New Job");
        request.setDescription("New Description");
        request.setCompany("New Company");
        request.setLocation("New Location");
        request.setSalary(60000.0);
        request.setJobType(JobType.FULL_TIME);

        try (MockedStatic<com.ME.job_management_system.util.SecurityUtil> utilities =
                     Mockito.mockStatic(com.ME.job_management_system.util.SecurityUtil.class)) {
            utilities.when(com.ME.job_management_system.util.SecurityUtil::getCurrentUser).thenReturn(testUser);

            when(jobRepository.save(any(Job.class))).thenReturn(testJob);

            // Act
            JobResponse result = jobService.createJob(request);

            // Assert
            assertNotNull(result);
            verify(jobRepository, times(1)).save(any(Job.class));
        }
    }

    @Test
    void deleteJob_WhenUserIsOwner_ShouldDeleteJob() {
        // Arrange
        when(jobRepository.findById(1L)).thenReturn(Optional.of(testJob));

        try (MockedStatic<com.ME.job_management_system.util.SecurityUtil> utilities =
                     Mockito.mockStatic(com.ME.job_management_system.util.SecurityUtil.class)) {
            utilities.when(com.ME.job_management_system.util.SecurityUtil::getCurrentUser).thenReturn(testUser);

            // Act
            jobService.deleteJob(1L);

            // Assert
            verify(jobRepository, times(1)).delete(testJob);
        }
    }

    @Test
    void deleteJob_WhenUserIsNotOwner_ShouldThrowException() {
        // Arrange
        when(jobRepository.findById(1L)).thenReturn(Optional.of(testJob));

        try (MockedStatic<com.ME.job_management_system.util.SecurityUtil> utilities =
                     Mockito.mockStatic(com.ME.job_management_system.util.SecurityUtil.class)) {
            utilities.when(com.ME.job_management_system.util.SecurityUtil::getCurrentUser).thenReturn(otherUser);

            // Act & Assert
            assertThrows(RuntimeException.class, () -> jobService.deleteJob(1L));
            verify(jobRepository, never()).delete(any());
        }
    }
}