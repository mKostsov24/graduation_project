package org.example.service.api;

import org.example.model.dto.NewVoteDTO;
import org.example.model.dto.posts.PostCountDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PostService {

    PostCountDTO getAllPostsWithConditions(int offset, int limit, String mode);

    PostCountDTO getAllPostsByQuery(int offset, int limit, String query);

    PostCountDTO getAllPostsByTag(int offset, int limit, String tag);

    ResponseEntity<?> getById(int id);

    PostCountDTO getAllPostsByDate(int offset, int limit, String date);

    PostCountDTO getAllPostsByEmail(int offset, int limit, String status, String email);

    Map<String, Boolean> addLikeAndDislike(NewVoteDTO newVoteDTO, String user, int value);
}
