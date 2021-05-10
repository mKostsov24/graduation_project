package org.example.service.api;

import org.example.model.dto.comments.NewCommentDTO;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    public ResponseEntity<?> addComment(NewCommentDTO commentDTO, String user);
}
