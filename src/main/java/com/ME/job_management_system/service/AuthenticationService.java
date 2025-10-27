package com.ME.job_management_system.service;

import com.ME.job_management_system.dto.AuthResponse;
import com.ME.job_management_system.dto.LoginRequest;
import com.ME.job_management_system.dto.RegisterRequest;
import com.ME.job_management_system.entity.User;
import com.ME.job_management_system.entity.UserRole;
import com.ME.job_management_system.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Create new user
        var user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFirstName(),
                request.getLastName(),
                request.getRole() != null ? request.getRole() : UserRole.USER
        );

        // Save user
        var savedUser = userRepository.save(user);

        // Generate token
        var jwtToken = jwtService.generateToken(user);

        return new AuthResponse(
                jwtToken,
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getRole()
        );
    }

    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get user from database
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        // Generate token
        var jwtToken = jwtService.generateToken(user);

        return new AuthResponse(
                jwtToken,
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }
}