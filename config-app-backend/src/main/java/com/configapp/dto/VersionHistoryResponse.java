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
public class VersionHistoryResponse {
    private Long version;
    private LocalDateTime lastModifiedDate;
    private Boolean active;
}
