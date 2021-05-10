package org.example.model.dto.posts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.model.Posts;
import org.example.model.Tags;
import org.example.model.dto.comments.CommentToPostDTO;
import org.example.model.dto.users.UserPostAuthorDTO;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostByIdDTO {
    private int id;
    @JsonProperty("timestamp")
    private long date;
    private boolean isActive;
    private UserPostAuthorDTO user;
    private String title;
    private String text;
    private long likeCount;
    private long dislikeCount;
    private int viewCount;
    private List<CommentToPostDTO> comments;
    private List<String> tags;

    public PostByIdDTO(Posts posts) {
        this.id = posts.getId();
        this.date = posts.getTime().getEpochSecond();
        this.isActive = posts.isActive();
        this.user = new UserPostAuthorDTO(posts.getUser().getId(), posts.getUser().getName());
        this.title = posts.getTitle();
        this.text = posts.getText();
        this.likeCount = posts.getVotesList()
                .stream()
                .filter(postVotes -> postVotes.getValue() == 1)
                .count();
        this.dislikeCount = posts.getVotesList()
                .stream()
                .filter(postVotes -> postVotes.getValue() == -1)
                .count();
        this.viewCount = posts.getViewCount();
        this.comments = posts.getCommentsList()
                .stream()
                .map(CommentToPostDTO::new)
                .collect(Collectors.toList());
        this.tags = posts.getTagsList()
                .stream()
                .map(Tags::getName)
                .collect(Collectors.toList());
    }
}
