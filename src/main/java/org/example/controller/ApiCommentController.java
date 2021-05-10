package org.example.controller;

import org.example.model.dto.comments.NewCommentDTO;
import org.example.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/comment")
public class ApiCommentController {

    private final CommentServiceImpl commentService;

    @Autowired
    public ApiCommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> addComment(@RequestBody NewCommentDTO newCommentDTO, Principal principal) {
        return commentService.addComment(newCommentDTO, principal.getName());
    }
}
