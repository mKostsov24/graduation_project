package org.example.model.dto.posts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.model.Posts;
import org.example.model.dto.users.UserPostAuthorDTO;
import org.jsoup.Jsoup;

@Data
public class PostDTO {
    private int id;
    @JsonProperty("timestamp")
    private long date;
    private UserPostAuthorDTO user;
    private String title;
    private String announce;
    private long likeCount;
    private long dislikeCount;
    private int viewCount;
    private int commentCount;

    public PostDTO(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.announce = Jsoup.parse(posts.getText()).text();
        this.user = new UserPostAuthorDTO(posts.getUser().getId(), posts.getUser().getName());
        this.viewCount = posts.getViewCount();
        this.likeCount = posts.getVotesList()
                .stream()
                .filter(postVotes -> postVotes.getValue() == 1)
                .count();
        this.dislikeCount = posts.getVotesList()
                .stream()
                .filter(postVotes -> postVotes.getValue() == -1)
                .count();
        this.date = posts.getTime().getEpochSecond();
        this.commentCount = posts.getCommentsList().size();
    }
}
