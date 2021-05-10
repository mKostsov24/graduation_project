package org.example.model.dto.comments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class NewCommentDTO {
    @Nullable
    @JsonProperty("parent_id")
    private String parentId;

    @JsonProperty("post_id")
    private Integer postId;

    private String text;
}
