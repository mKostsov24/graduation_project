package org.example.model.dto.tags;

import lombok.Data;
import org.example.model.Tags;

@Data
public class TagDTO {
    private final String name;
    private final long totalPostsWithTag;
    private double weight;
    private double dWeight;

    public TagDTO(Tags tag, long totalPostsWithTag) {
        this.name = tag.getName();
        this.totalPostsWithTag = totalPostsWithTag;
        this.weight = 0.0;
    }

    public void setDWeight(long totalPosts) {
        this.dWeight = totalPostsWithTag / (double) totalPosts;
    }

    public void setWeight(double maxWeight) {
        this.weight = dWeight * (1 / maxWeight);
    }

    public void setMaxWeight(double maxWeight) {
        this.weight = maxWeight;
    }
}

