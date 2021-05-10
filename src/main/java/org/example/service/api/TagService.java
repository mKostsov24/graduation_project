package org.example.service.api;

import org.example.model.dto.tags.TagCountDTO;
import org.example.model.dto.tags.TagDTO;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface TagService {
    TagCountDTO getWeightsTags(String queryName);

    List<TagDTO> getWeightsTagsAll(String queryName);
}
