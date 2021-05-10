package org.example.model.dto.tags;

import lombok.Data;
import org.example.model.Tags;

@Data
public class TagNameDTO {
    private String name;

    public TagNameDTO(Tags tags) {
        this.name = tags.getName();
    }
}
