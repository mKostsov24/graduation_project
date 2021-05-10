package org.example.model.dto.posts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ModerationPostDTO {

    @JsonProperty(value = "post_id")
    private int postId;
    @JsonProperty(value = "decision")
    private String status;

}
