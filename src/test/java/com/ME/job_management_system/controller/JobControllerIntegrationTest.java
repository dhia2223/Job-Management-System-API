package com.ME.job_management_system.controller;

import com.ME.job_management_system.dto.JobCreateRequest;
import com.ME.job_management_system.entity.JobType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class JobControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllJobs_WithoutAuth_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/jobs"))
                .andExpect(status().isForbidden());
    }

    @Test
    void createJob_WithoutAuth_ShouldReturnForbidden() throws Exception {
        JobCreateRequest request = new JobCreateRequest();
        request.setTitle("Integration Test Job");
        request.setDescription("This is a test job created during integration testing");
        request.setCompany("Test Company");
        request.setLocation("Test Location");
        request.setSalary(70000.0);
        request.setJobType(JobType.FULL_TIME);

        mockMvc.perform(post("/api/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void healthCheck_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));
    }

    @Test
    void apiInfo_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk());
    }
}