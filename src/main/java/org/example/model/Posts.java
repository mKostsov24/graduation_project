package org.example.model;

import lombok.Data;
import org.example.enums.ModerationStatus;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(catalog = "Posts")
public class Posts {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private boolean isActive;
    @Column(nullable = false, columnDefinition = "varchar(32) default 'NEW'")
    @Enumerated(EnumType.STRING)
    private ModerationStatus moderationStatus = ModerationStatus.NEW;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "moderator_id", referencedColumnName = "id")
    private Users moderatorId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users user;
    @Column(nullable = false)
    private Instant time;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "text")
    private String text;
    @Column(nullable = false)
    private int viewCount = 0;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostVotes> votesList;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostComments> commentsList;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tag_to_post",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})

    private List<Tags> tagsList;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<TagToPost> tagsToPostList;
}

