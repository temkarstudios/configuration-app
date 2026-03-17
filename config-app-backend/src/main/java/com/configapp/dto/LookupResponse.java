package com.configapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.configapp.document.Setting;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LookupResponse {
    private List<Setting> settings;
}
