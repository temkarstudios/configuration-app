package com.configapp.controller;

import com.configapp.dto.ConfigurationDto;
import com.configapp.dto.CreateConfigurationRequest;
import com.configapp.dto.UpdateConfigurationRequest;
import com.configapp.dto.TransferOwnershipRequest;
import com.configapp.dto.HistoryEntryDto;
import com.configapp.dto.ConfigurationStatsDto;
import com.configapp.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "*")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @PostMapping("/api/v1/configuration/create")
    public CompletableFuture<ResponseEntity<ConfigurationDto>> createConfiguration(
            @Valid @RequestBody CreateConfigurationRequest request,
            Authentication authentication) {
        
        return configurationService.createConfiguration(authentication.getName(), request)
                .thenApply(dto -> ResponseEntity.status(HttpStatus.OK).body(dto));
    }

    @GetMapping("/api/v1/configurations")
    public CompletableFuture<ResponseEntity<List<ConfigurationDto>>> getConfigurations(
            Authentication authentication) {
        
        return configurationService.getConfigurationsByUser(authentication.getName())
                .thenApply(list -> ResponseEntity.status(HttpStatus.OK).body(list));
    }

    @GetMapping("/api/v1/configurations/history/{configurationId}")
    public CompletableFuture<ResponseEntity<List<HistoryEntryDto>>> getConfigurationHistory(
            @PathVariable String configurationId,
            Authentication authentication) {
        
        return configurationService.getConfigurationHistory(configurationId, authentication.getName())
                .thenApply(list -> ResponseEntity.status(HttpStatus.OK).body(list));
    }

    @GetMapping("/api/v1/configuration/stats/{configurationId}")
    public CompletableFuture<ResponseEntity<ConfigurationStatsDto>> getConfigurationStats(
            @PathVariable String configurationId,
            Authentication authentication) {
        
        return configurationService.getConfigurationStats(configurationId, authentication.getName())
                .thenApply(dto -> ResponseEntity.status(HttpStatus.OK).body(dto));
    }

    @PatchMapping("/api/v1/configuration/{configurationId}")
    public CompletableFuture<ResponseEntity<Void>> updateConfiguration(
            @PathVariable String configurationId,
            @RequestBody UpdateConfigurationRequest request,
            Authentication authentication) {
        
        return configurationService.updateConfiguration(configurationId, authentication.getName(), request)
                .thenApply(dto -> ResponseEntity.status(HttpStatus.NO_CONTENT).<Void>body(null))
                .exceptionally(e -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<Void>body(null));
    }

    @PutMapping("/api/v1/configuration/{version}/{configurationId}")
    public CompletableFuture<ResponseEntity<Void>> setConfigurationVersion(
            @PathVariable String configurationId,
            @PathVariable Long version,
            Authentication authentication) {
        
        return configurationService.setConfigurationVersion(configurationId, version, authentication.getName())
                .thenApply(v -> ResponseEntity.status(HttpStatus.ACCEPTED).<Void>body(null));
    }

    @DeleteMapping("/api/v1/configuration/{configurationId}")
    public CompletableFuture<ResponseEntity<Void>> deleteConfiguration(
            @PathVariable String configurationId,
            Authentication authentication) {
        
        return configurationService.deleteConfiguration(configurationId, authentication.getName())
                .thenApply(v -> ResponseEntity.status(HttpStatus.NO_CONTENT).<Void>body(null));
    }

    @PutMapping("/api/v1/configuration/transfer/{configurationId}")
    public CompletableFuture<ResponseEntity<Void>> transferOwnership(
            @PathVariable String configurationId,
            @RequestBody TransferOwnershipRequest request,
            Authentication authentication) {
        
        return configurationService.transferOwnership(configurationId, authentication.getName(), request)
                .thenApply(v -> ResponseEntity.status(HttpStatus.ACCEPTED).<Void>body(null));
    }
}
