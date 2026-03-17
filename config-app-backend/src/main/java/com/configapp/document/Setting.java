package com.configapp.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setting {

    private String id;

    private String key;

    private Object value;

    private Map<String, Object> type;
}
