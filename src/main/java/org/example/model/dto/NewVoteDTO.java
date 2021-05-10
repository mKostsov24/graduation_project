package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewVoteDTO {
    @JsonProperty("post_id")
    private int id;
}
