package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private boolean result;
    @JsonProperty(value = "errors")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> errorsMap;
}
