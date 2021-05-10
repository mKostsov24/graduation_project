package org.example.controller;

import org.example.model.dto.tags.TagCountDTO;
import org.example.service.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tag")
public class ApiTagController {

    private TagServiceImpl tagsService;

    @Autowired
    public ApiTagController(TagServiceImpl tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping
    public TagCountDTO getTagsWithName(@RequestParam(name = "query", defaultValue = "") String query) {
        return tagsService.getWeightsTags(query);
    }
}
