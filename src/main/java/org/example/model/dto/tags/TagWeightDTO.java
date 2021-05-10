package org.example.model.dto.tags;

import lombok.Data;

@Data
public class TagWeightDTO {
    private String name;
    private double weight;

    public TagWeightDTO(TagDTO tagDTO) {
        this.name = tagDTO.getName();
        this.weight = tagDTO.getWeight();
    }
}
