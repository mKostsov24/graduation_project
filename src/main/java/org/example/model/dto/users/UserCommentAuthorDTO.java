package org.example.model.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserCommentAuthorDTO {
    private int id;
    private String name;
    private String photo;
}
