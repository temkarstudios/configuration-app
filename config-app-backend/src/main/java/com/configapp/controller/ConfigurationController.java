package com.configapp.controller;

import com.configapp.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Configuration Controller - handles configuration CRUD operations.
 * 
 * Supports:
 * - Create new configurations
 * - Retrieve configurations (with pagination)
 * - View version history
 * - Retrieve statistics
 * - Update configurations (creates new version)
 * - Activate specific version
 * - Delete configurations (OWNER only)
 * - Transfer ownership (OWNER only)
 * 
 * All endpoints require JWT authentication.
 */
@RestController
@RequestMapping("/api/v1/configuration")
@RequiredArgsConstructor
@Slf4j
public class ConfigurationController {

    // TODO: Inject ConfigurationService once created

    /**
     * Create a new configuration.
     * Requires JWT authentication.
     * The authenticated admin becomes the OWNER.
     * 
     * @param createRequest contains configuration details and initial settings
     * @return ConfigurationResponse with created configuration
     */
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ConfigurationResponse> create(
            @Valid @RequestBody CreateConfigurationRequest createRequest) {
        log.info("Creating new configuration: {}", createRequest.getName());
        // TODO: Implement create logic
        // - Verify JWT token
        // - Set OWNER to authenticated user ID
        // - Auto-increment version to 1
        // - Set active = true
        // - Save to MongoDB
        // - Return ConfigurationResponse (200)
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Get all configurations for the authenticated user.
     * Returns only configurations where user is OWNER or in adminIds.
     * Requires JWT authentication.
     * 
     * @param page page number (0-indexed)
     * @param size page size
     * @return PaginatedResponse containing list of ConfigurationResponse
     */
    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginatedResponse<ConfigurationResponse>> getConfigurations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching configurations for authenticated user - page: {}, size: {}", page, size);
        // TODO: Implement list logic
        // - Verify JWT token
        // - Query MongoDB for configurations where userId is OWNER or in adminIds
        // - Apply pagination
        // - Return PaginatedResponse (200)
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Get version history for a configuration.
     * Requires JWT authentication and user must have access to the configuration.
     * 
     * @param configurationId the config GUID
     * @return List of VersionHistoryResponse objects (version, lastModifiedDate, active)
     */
    @GetMapping("/history/{configurationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<VersionHistoryResponse>> getVersionHistory(
            @PathVariable String configurationId) {
        log.info("Fetching version history for configuration: {}", configurationId);
        // TODO: Implement version history logic
        // - Verify JWT token
        // - Check user has access to this configuration
        // - Query MongoDB for all versions of this configurationId
        // - Return list of VersionHistoryResponse (200)
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Get lookup statistics for a configuration.
     * Requires JWT authentication and user must have access to the configuration.
     * 
     * @param configurationId the config GUID
     * @return StatsResponse with totalLookups and lastLookupAt
     */
    @GetMapping("/stats/{configurationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StatsResponse> getStats(
            @PathVariable String configurationId) {
        log.info("Fetching stats for configuration: {}", configurationId);
        // TODO: Implement stats logic
        // - Verify JWT token
        // - Check user has access to this configuration
        // - Query Stats collection for this configurationId
        // - Return StatsResponse (200)
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Update configuration.
     * Creates a new version from the currently active version.
     * Requires JWT authentication and edit permission.
     * User must be OWNER or in adminIds.
     * 
     * @param configurationId the config GUID
     * @param updateRequest contains updated configuration data
     * @return no content response
     */
    @PatchMapping("/{configurationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updateConfiguration(
            @PathVariable String configurationId,
            @Valid @RequestBody UpdateConfigurationRequest updateRequest) {
        log.info("Updating configuration: {}", configurationId);
        // TODO: Implement update logic
        // - Verify JWT token
        // - Check user has OWNER or EDITOR permission
        // - Fetch active version
        // - Create new document with version+1
        // - Set active = true on new version, false on old
        // - Persist to MongoDB
        // - Return 204 No Content
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Activate a specific version of a configuration.
     * Requires JWT authentication and edit permission.
     * 
     * @param version version number to activate
     * @param configurationId the config GUID
     * @return 202 Accepted response
     */
    @PutMapping("/{version}/{configurationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> activateVersion(
            @PathVariable int version,
            @PathVariable String configurationId) {
        log.info("Activating version {} for configuration: {}", version, configurationId);
        // TODO: Implement version activation logic
        // - Verify JWT token
        // - Check user has OWNER or EDITOR permission
        // - Find version document
        // - Deactivate current active version
        // - Activate specified version
        // - Persist to MongoDB
        // - Return 202 Accepted
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * Delete a configuration.
     * Only OWNER can delete.
     * Requires JWT authentication.
     * 
     * @param configurationId the config GUID
     * @return 204 No Content response
     */
    @DeleteMapping("/{configurationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteConfiguration(
            @PathVariable String configurationId) {
        log.info("Deleting configuration: {}", configurationId);
        // TODO: Implement delete logic
        // - Verify JWT token
        // - Check user is OWNER
        // - Delete all versions of this configurationId
        // - Delete stats for this configurationId
        // - Decrement numberOfConfigurationsOwned from admin? (spec says lifetime count, so don't decrement)
        // - Return 204 No Content
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Transfer configuration ownership.
     * Only OWNER can transfer.
     * Requires JWT authentication.
     * 
     * @param configurationId the config GUID
     * @param transferRequest contains target admin GUID (transfer-to field)
     * @return 202 Accepted response
     */
    @PutMapping("/transfer/{configurationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> transferOwnership(
            @PathVariable String configurationId,
            @Valid @RequestBody TransferOwnershipRequest transferRequest) {
        log.info("Transferring ownership of configuration: {} to admin: {}", 
                configurationId, transferRequest.getTransferTo());
        // TODO: Implement transfer logic
        // - Verify JWT token
        // - Check user is OWNER
        // - Verify target admin exists
        // - Update OWNER field to new admin
        // - Remove new admin from adminIds if present
        // - Persist to MongoDB
        // - Return 202 Accepted
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
