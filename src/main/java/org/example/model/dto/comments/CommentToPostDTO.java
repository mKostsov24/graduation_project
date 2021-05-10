package org.example.model.dto.comments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.model.PostComments;
import org.example.model.dto.users.UserCommentAuthorDTO;

@Data
public class CommentToPostDTO {
    private int id;
    @JsonProperty("timestamp")
    private long date;
    private String text;
    private UserCommentAuthorDTO user;

    public CommentToPostDTO(PostComments postComments) {
        this.id = postComments.getId();
        this.date = postComments.getTime().getEpochSecond();
        this.text = postComments.getText();
        this.user = new UserCommentAuthorDTO(postComments.getUser().getId(),
                postComments.getUser().getName(), postComments.getUser().getPhoto());
    }
}
