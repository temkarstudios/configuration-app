package com.configapp.controller;

import com.configapp.dto.LookupRequest;
import com.configapp.service.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/lookup")
@CrossOrigin(origins = "*")
public class LookupController {

    @Autowired
    private LookupService lookupService;

    @PostMapping("/{configurationId}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> lookup(
            @PathVariable String configurationId,
            @RequestBody LookupRequest request) {
        
        return lookupService.lookup(configurationId, request)
                .thenApply(result -> ResponseEntity.status(HttpStatus.OK).body(result));
    }
}
