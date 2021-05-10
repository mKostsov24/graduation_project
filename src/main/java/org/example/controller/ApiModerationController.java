package org.example.controller;

import org.example.model.dto.posts.ModerationPostDTO;
import org.example.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/api/moderation")
public class ApiModerationController {

    private final PostServiceImpl postsService;

    @Autowired
    public ApiModerationController(PostServiceImpl postsService) {
        this.postsService = postsService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:moder')")
    public ResponseEntity<?> moderationPosts(@RequestBody ModerationPostDTO moderationPostDTO,
                                             Principal principal) {
        return postsService.moderationPosts(moderationPostDTO, principal.getName());
    }
}
