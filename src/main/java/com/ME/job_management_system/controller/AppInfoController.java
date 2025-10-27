package com.ME.job_management_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AppInfoController {

    @GetMapping("/api/info")
    public Map<String, String> getAppInfo(@RequestParam(required = false) String detail) {
        Map<String, String> info = new HashMap<>();

        info.put("name", "Job Management System");
        info.put("version", "1.0.0");
        info.put("description", "Backend API for managing job postings and applications");

        if ("full".equals(detail)) {
            info.put("developer", "HK");
            info.put("technology", "Spring Boot, PostgreSQL");
            info.put("status", "Development");
        } else if ("brief".equals(detail)) {
            info.remove("description");
        }

        return info;
    }
}