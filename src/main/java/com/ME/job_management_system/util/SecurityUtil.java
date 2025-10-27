package com.ME.job_management_system.util;

import com.ME.job_management_system.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        // For testing, return a mock user if no authentication
        if (isTestEnvironment()) {
            return createMockUser();
        }
        throw new RuntimeException("No authenticated user found");
    }

    public static String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static boolean isAdmin() {
        return getCurrentUser().getRole().name().equals("ADMIN");
    }

    public static boolean isEmployer() {
        return getCurrentUser().getRole().name().equals("EMPLOYER");
    }

    private static boolean isTestEnvironment() {
        return System.getProperty("spring.profiles.active", "").contains("test") ||
                System.getProperty("TEST_ENV") != null;
    }

    private static User createMockUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setRole(com.ME.job_management_system.entity.UserRole.USER);
        return user;
    }
}