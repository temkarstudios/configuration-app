package com.configapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminResponse {
    private String id;
    private String username;
    private Long numberOfConfigurationsOwned;
    private LocalDateTime registeredOn;
    private Boolean active;
}
