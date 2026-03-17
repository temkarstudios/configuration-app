package com.configapp.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collection = "configurations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Configuration {

    @Id
    private String id;

    @Indexed
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
