package com.configapp.controller;

import com.configapp.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Admin Controller - handles authentication and admin management endpoints.
 * 
 * Supports:
 * - User registration
 * - Login with JWT token generation
 * - Token refresh
 * - Admin profile retrieval and updates
 */
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    // TODO: Inject AdminService once created

    /**
     * Register a new admin user.
     * 
     * @param registerRequest contains username and password
     * @return AdminResponse with user details
     */
    @PostMapping("/register")
    public ResponseEntity<AdminResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("New registration request for username: {}", registerRequest.getUsername());
        // TODO: Implement registration logic
        // - Validate email uniqueness
        // - Hash password with bcrypt
        // - Save to MongoDB
        // - Return AdminResponse (200)
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Authenticate admin and return JWT tokens.
     * 
     * @param loginRequest contains username and password
     * @return LoginResponse with access token, refresh token, expiration, and user details
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login attempt for username: {}", loginRequest.getUsername());
        // TODO: Implement login logic
        // - Validate credentials against stored hash
        // - Generate JWT access token
        // - Generate refresh token
        // - Return LoginResponse (200)
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Refresh access token using refresh token.
     * 
     * @param refreshTokenRequest contains the refresh token
     * @return LoginResponse with new access token
     */
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        log.info("Token refresh request");
        // TODO: Implement token refresh logic
        // - Validate refresh token
        // - Generate new access token
        // - Return LoginResponse (200)
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Get admin user details by ID.
     * Requires JWT authentication.
     * 
     * @param id admin user ID
     * @return AdminResponse with user details
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponse> getAdmin(@PathVariable String id) {
        log.info("Fetching admin details for ID: {}", id);
        // TODO: Implement GET logic
        // - Verify JWT token
        // - Fetch admin by ID from MongoDB
        // - Return AdminResponse (200)
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Update admin user details (username, active status only).
     * Requires JWT authentication.
     * 
     * @param id admin user ID
     * @param updateRequest contains fields to update (username, active)
     * @return AdminResponse with updated user details
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AdminResponse> updateAdmin(
            @PathVariable String id,
            @Valid @RequestBody AdminUpdateRequest updateRequest) {
        log.info("Updating admin details for ID: {}", id);
        // TODO: Implement PATCH logic
        // - Verify JWT token
        // - Update only username and active fields
        // - Persist to MongoDB
        // - Return AdminResponse (200)
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
