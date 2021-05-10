package org.example.model.dto.posts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.utils.ErrorsList;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@Data
public class NewPostDTO {
    @JsonProperty(value = "timestamp")
    private Instant time;
    @JsonProperty(value = "active")
    private boolean isActive;
    @NotBlank(message = ErrorsList.STRING_FIELD_CANT_BE_BLANK)
    @Size(min = 3, max = 255, message = ErrorsList.STRING_AUTH_SHORT_PASSWORD)
    private String title;
    private List<String> tags;
    @NotBlank(message = ErrorsList.STRING_FIELD_CANT_BE_BLANK)
    @Size(min = 50, max = 255, message = ErrorsList.STRING_AUTH_SHORT_PASSWORD)
    private String text;

}
