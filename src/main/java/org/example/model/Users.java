package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Role;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(force = true)
public class Users {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private boolean isModerator;
    @Column(nullable = false)
    private Instant regTime;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String code;
    @Column(columnDefinition = "text")
    private String photo;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Posts> postsList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostVotes> postVotesList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostComments> commentsList;

    public Role getRole() {
        return isModerator ? Role.MODER : Role.USER;
    }

    public static class Builder {
        private Users newUsers;

        public Builder() {
            newUsers = new Users();
        }

        public Builder setName(String name) {
            newUsers.name = name;

            return this;
        }

        public Builder setEmail(String email) {
            newUsers.email = email;

            return this;
        }

        public Builder setPassword(String password) {
            newUsers.password = password;

            return this;
        }

        public Builder setRegTime(Instant regTime) {
            newUsers.regTime = regTime;
            return this;
        }

        public Users build() {
            return newUsers;
        }
    }
}

