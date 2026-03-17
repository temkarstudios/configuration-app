package com.configapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateConfigurationRequest {
    private String name;
    private String description;
    private List<SettingRequest> settings;
    private Map<String, Object> additionalProperties;
}
