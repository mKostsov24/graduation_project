package org.example.model.dto.tags;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TagCountDTO {
    @JsonProperty("tags")
    private List<TagWeightDTO> tagDTOList;

    public TagCountDTO(List<TagWeightDTO> tagDTOList) {
        this.tagDTOList = tagDTOList;
    }
}

