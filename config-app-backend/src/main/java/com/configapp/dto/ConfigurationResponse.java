package com.configapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.configapp.document.Setting;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigurationResponse {
    private String id;
    private String configurationId;
    private String name;
    private String description;
    private Long version;
    private Boolean active;
    private String owner;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private List<String> adminIds;
    private List<Setting> settings;
    private Map<String, Object> additionalProperties;
}
