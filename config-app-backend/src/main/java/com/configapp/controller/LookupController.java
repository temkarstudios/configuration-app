package com.configapp.controller;

import com.configapp.dto.LookupRequest;
import com.configapp.dto.LookupResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Lookup Controller - handles consumer (non-human service) configuration lookups.
 * 
 * Supports:
 * - Lookup settings by GUID
 * - Lookup settings by key
 * 
 * All endpoints require JWT authentication (consumer service tokens).
 * Updates statistics on successful lookup (totalLookups, lastLookupAt).
 */
@RestController
@RequestMapping("/api/v1/lookup")
@RequiredArgsConstructor
@Slf4j
public class LookupController {

    // TODO: Inject ConfigurationService and StatsService once created

    /**
     * Lookup configuration settings.
     * 
     * Consumer services call this endpoint to retrieve configuration settings
     * at runtime. Settings can be looked up by GUID or by key.
     * 
     * Requires JWT authentication (consumer service token).
     * Async operation - updates stats asynchronously.
     * 
     * Request body supports two lookup methods:
     * 1. By Setting GUID:
     *    {
     *      "settings": ["<guid1>", "<guid2>", ...]
     *    }
     * 2. By Key:
     *    {
     *      "keys": ["<key1>", "<key2>", ...]
     *    }
     * 
     * @param configurationId the configuration GUID to lookup
     * @param lookupRequest contains settings GUIDs or keys to retrieve
     * @return LookupResponse with requested settings
     */
    @PostMapping("/{configurationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LookupResponse> lookup(
            @PathVariable String configurationId,
            @Valid @RequestBody LookupRequest lookupRequest) {
        log.info("Lookup request for configuration: {} with {} items", 
                configurationId, 
                lookupRequest.getSettings() != null ? lookupRequest.getSettings().size() : 
                                                     lookupRequest.getKeys().size());
        // TODO: Implement lookup logic
        // - Verify JWT token (consumer service)
        // - Fetch active configuration by configurationId
        // - If lookup by GUID: filter settings by id field
        // - If lookup by key: filter settings by key field
        // - Construct LookupResponse with matching settings
        // - Asynchronously update stats (totalLookups++, lastLookupAt=now)
        // - Return LookupResponse (200)
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Async helper to update lookup statistics.
     * Called after successful lookup to track usage metrics.
     * Non-blocking operation.
     * 
     * @param configurationId the configuration that was looked up
     */
    @Async
    // TODO: Make this a private helper method in service layer
    // This is placeholder for the async stats update logic
    public void updateLookupStats(String configurationId) {
        log.info("Updating lookup stats for configuration: {}", configurationId);
        // TODO: Implement async stats update
        // - Query Stats collection for configurationId
        // - Increment totalLookups
        // - Update lastLookupAt to current timestamp
        // - Save to MongoDB
    }
}
