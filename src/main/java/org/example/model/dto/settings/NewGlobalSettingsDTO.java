package org.example.model.dto.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewGlobalSettingsDTO {
    @JsonProperty("MULTIUSER_MODE")
    private boolean multiuserMode;
    @JsonProperty("POST_PREMODERATION")
    private boolean postPremoderation;
    @JsonProperty("STATISTICS_IS_PUBLIC")
    private boolean statisticsIsPublic;
}
