package org.example.service;

import org.example.model.Tags;
import org.example.model.dto.tags.TagCountDTO;
import org.example.model.dto.tags.TagDTO;
import org.example.model.dto.tags.TagWeightDTO;
import org.example.repository.PostRepository;
import org.example.repository.TagRepository;
import org.example.service.api.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final PostRepository postRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, PostRepository postRepository) {
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
    }

    public List<TagDTO> getWeightsTagsAll(String queryName) {
        final int COUNT_ACTIVE_POST =
                postRepository.findAllActive(
                        Pageable.unpaged()).getSize();
        List<TagDTO> tagDTOList = new ArrayList<>();
        Map<String, Long> tagsMap =
                tagRepository.getAllTagsByActivePosts()
                        .stream()
                        .collect(Collectors.groupingBy(Tags::getName, Collectors.counting()));
        tagRepository.getAllTagsByActivePosts()
                .stream()
                .distinct()
                .forEach(tags -> tagDTOList.add(
                        new TagDTO(
                                tags, tagsMap.get(tags.getName()))));
        tagDTOList.sort((one, two) -> Long.compare(
                two.getTotalPostsWithTag(), one.getTotalPostsWithTag()));
        TagDTO maxValue = tagDTOList.get(0);
        maxValue.setDWeight(COUNT_ACTIVE_POST);
        maxValue.setMaxWeight(1);
        tagDTOList
                .stream()
                .filter(tagDTO -> !(tagDTO.equals(tagDTOList.get(0))))
                .forEach(tagDTO -> {
                    tagDTO.setDWeight(COUNT_ACTIVE_POST);
                    tagDTO.setWeight(maxValue.getDWeight());
                });
        if (queryName.equals("")) {
            return tagDTOList;
        }
        return tagDTOList
                .stream()
                .filter(tagDTO -> tagDTO.getName().equals(queryName)).collect(Collectors.toList());

    }

    public TagCountDTO getWeightsTags(String queryName) {
        List<TagWeightDTO> tagWeightDTO = new ArrayList<>();
        getWeightsTagsAll(queryName)
                .forEach(tags -> tagWeightDTO.add(new TagWeightDTO(tags)));
        return new TagCountDTO(tagWeightDTO);
    }

}
