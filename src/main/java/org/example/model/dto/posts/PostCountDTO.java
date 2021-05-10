package org.example.model.dto.posts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PostCountDTO {
    private int count;
    @JsonProperty("posts")
    private List<PostDTO> postDTOList;

    public PostCountDTO(List<PostDTO> postDTOList, int count) {
        this.count = count;
        this.postDTOList = postDTOList;
    }
}
