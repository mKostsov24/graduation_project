package org.example.controller;

import org.example.model.dto.NewVoteDTO;
import org.example.model.dto.posts.NewPostDTO;
import org.example.model.dto.posts.PostCountDTO;
import org.example.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    private final PostServiceImpl postsService;

    @Autowired
    public ApiPostController(PostServiceImpl postsService) {
        this.postsService = postsService;
    }

    @GetMapping
    public PostCountDTO getPosts(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "mode", defaultValue = "recent") String mode) {
        return postsService.getAllPostsWithConditions(offset/limit, limit, mode);
    }

    @GetMapping("/search")
    public PostCountDTO searchPosts(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "query", defaultValue = "") String mode) {
        return postsService.getAllPostsByQuery(offset/limit, limit, mode);
    }

    @GetMapping("/byTag")
    public PostCountDTO searchByTag(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "tag", defaultValue = "") String tag) {
        return postsService.getAllPostsByTag(offset/limit, limit, tag);
    }

    @GetMapping("/byDate")
    public PostCountDTO searchByDate(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "date", defaultValue = "") String date) {
        return postsService.getAllPostsByDate(offset/limit, limit, date);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> searchById(@PathVariable Integer id, Principal principal) {
        if (principal == null) {
            return postsService.getById(id);
        }
        return postsService.getByIdAuth(id, principal.getName());
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('user:write')")
    public PostCountDTO searchMy(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "status", defaultValue = "inactive") String status,
            Principal principal) {
        return postsService.getAllPostsByEmail(offset/limit, limit, status, principal.getName());
    }

    @GetMapping("/moderation")
    @PreAuthorize("hasAuthority('user:moder')")
    public ResponseEntity<?> getModerationPosts(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "status", defaultValue = "new") String status,
            Principal principal) {
        return postsService.getModerationPosts(offset/limit, limit, status, principal.getName());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> addPost(@RequestBody NewPostDTO postDTO, Principal principal) {
        return postsService.addPost(postDTO, principal.getName());
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> updatePost(@PathVariable Integer id, @RequestBody NewPostDTO postDTO, Principal principal) {
        return postsService.updatePost(postDTO, id, principal.getName());
    }

    @PostMapping("/like")
    @PreAuthorize("hasAuthority('user:write')")
    public Map<String, Boolean> like(@RequestBody NewVoteDTO newVoteDTO, Principal principal) {
        return postsService.addLikeAndDislike(newVoteDTO, principal.getName(), 1);
    }

    @PostMapping("/dislike")
    @PreAuthorize("hasAuthority('user:write')")
    public Map<String, Boolean> dislike(@RequestBody NewVoteDTO newVoteDTO, Principal principal) {
        return postsService.addLikeAndDislike(newVoteDTO, principal.getName(), -1);
    }
}
