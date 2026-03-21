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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/configuration")
@CrossOrigin(origins = "*")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    private ResponseEntity<?> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", message);
        return new ResponseEntity<>(response, status);
    }

    private HttpStatus getStatusForException(Throwable e) {
        if (e != null && e.getCause() instanceof IllegalArgumentException) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String getMessageForException(Throwable e) {
        if (e != null && e.getCause() instanceof IllegalArgumentException) {
            return e.getCause().getMessage();
        }
        return "Internal server error";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createConfiguration(
            @Valid @RequestBody CreateConfigurationRequest request,
            Authentication authentication) {
        
        try {
            ConfigurationDto dto = configurationService.createConfiguration(authentication.getName(), request).get();
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return buildErrorResponse("Failed to create configuration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/configurations")
    public ResponseEntity<?> getConfigurations(Authentication authentication) {
        try {
            List<ConfigurationDto> list = configurationService.getConfigurationsByUser(authentication.getName()).get();
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            return buildErrorResponse("Failed to fetch configurations", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/history/{configurationId}")
    public ResponseEntity<?> getConfigurationHistory(
            @PathVariable String configurationId,
            Authentication authentication) {
        try {
            List<HistoryEntryDto> list = configurationService.getConfigurationHistory(configurationId, authentication.getName()).get();
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            return buildErrorResponse("Failed to fetch history", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stats/{configurationId}")
    public ResponseEntity<?> getConfigurationStats(
            @PathVariable String configurationId,
            Authentication authentication) {
        try {
            ConfigurationStatsDto dto = configurationService.getConfigurationStats(configurationId, authentication.getName()).get();
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception e) {
            return buildErrorResponse("Failed to fetch stats", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{configurationId}")
    public ResponseEntity<?> updateConfiguration(
            @PathVariable String configurationId,
            @RequestBody UpdateConfigurationRequest request,
            Authentication authentication) {
        try {
            configurationService.updateConfiguration(configurationId, authentication.getName(), request).get();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return buildErrorResponse("Failed to update configuration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{version}/{configurationId}")
    public ResponseEntity<?> setConfigurationVersion(
            @PathVariable String configurationId,
            @PathVariable Long version,
            Authentication authentication) {
        try {
            configurationService.setConfigurationVersion(configurationId, version, authentication.getName()).get();
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return buildErrorResponse("Failed to set version", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{configurationId}")
    public ResponseEntity<?> deleteConfiguration(
            @PathVariable String configurationId,
            Authentication authentication) {
        try {
            configurationService.deleteConfiguration(configurationId, authentication.getName()).get();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return buildErrorResponse("Failed to delete configuration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/transfer/{configurationId}")
    public ResponseEntity<?> transferOwnership(
            @PathVariable String configurationId,
            @RequestBody TransferOwnershipRequest request,
            Authentication authentication) {
        try {
            configurationService.transferOwnership(configurationId, authentication.getName(), request).get();
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return buildErrorResponse("Failed to transfer ownership", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
