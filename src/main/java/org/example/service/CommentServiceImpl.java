package org.example.service;

import org.example.model.PostComments;
import org.example.model.dto.ErrorDTO;
import org.example.model.dto.comments.NewCommentDTO;
import org.example.repository.CommentRepository;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.example.service.api.CommentService;
import org.example.utils.ErrorsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> addComment(NewCommentDTO commentDTO, String user) {
        Map<String, Object> errors = validateCommentInputAndGetErrors(commentDTO);

        if (postRepository.findPostById(commentDTO.getPostId()) == null) {
            return ResponseEntity.ok(new ErrorDTO(false, errors));
        } else if (errors.size() > 0) {
            return ResponseEntity.badRequest().body(new ErrorDTO(false, errors));
        }
        PostComments postComments = new PostComments();
        postComments.setUser(userRepository.findByEmail(user));
        if (commentDTO.getParentId() != null) {
            if (commentRepository.findById(Integer.parseInt(commentDTO.getParentId())) == null) {
                return ResponseEntity.ok(new ErrorDTO(false, errors));
            } else {
                postComments.setParentId(commentRepository.findById(Integer.parseInt(commentDTO.getParentId())));
            }
        } else {
            postComments.setParentId(null);
        }
        postComments.setPost(postRepository.findPostById(commentDTO.getPostId()));
        postComments.setTime(Instant.now());
        postComments.setText(commentDTO.getText());

        commentRepository.save(postComments);

        Map<String, Integer> successfully = new HashMap<>();
        successfully.put("id", postComments.getId());
        return ResponseEntity.ok(successfully);
    }

    private Map<String, Object> validateCommentInputAndGetErrors(NewCommentDTO commentDTO) {
        Map<String, Object> errorsMap = new HashMap<>();
        if (commentDTO.getParentId() == null) {
            if (commentDTO.getText().isEmpty() || commentDTO.getText().length() < 10) {
                errorsMap.put("email", ErrorsList.STRING_COMMENT_TEXT_SHORT);
            }
        } else {
            if (commentDTO.getText().replaceFirst(".+?(?=strong>,)", "").isEmpty() ||
                    commentDTO.getText().replaceFirst(".+?(?=strong>,)", "").length() < 10) {
                errorsMap.put("email", ErrorsList.STRING_COMMENT_TEXT_SHORT);
            }
        }
        return errorsMap;
    }

}
