package org.example.repository;

import org.example.enums.ModerationStatus;
import org.example.model.Posts;
import org.example.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Posts, Integer> {

    @Query("SELECT p FROM Posts p WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time <= NOW()")
    Page<Posts> findAllActive(Pageable pageable);

    @Query(value = "SELECT p.* FROM Posts p join post_comments pc on p.id = pc.post_id where is_active = true " +
            "AND moderation_status = 'ACCEPTED' AND p.time <= NOW() group by p.id order by count(pc.id) desc", nativeQuery = true)
    Page<Posts> findAllActiveByComments(Pageable pageable);

    @Query("SELECT p FROM Posts p WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time <= NOW() AND " +
            "(p.title LIKE %:query% OR p.text LIKE %:query%)")
    Page<Posts> findAllPostsByQuery(@Param("query") String query, Pageable pageable);

    @Query("SELECT p FROM Posts p WHERE p.isActive = true AND p.moderationStatus = :status")
    Page<Posts> findAllPostsByModerationStatus(@Param("status") ModerationStatus status, Pageable pageable);

    @Query("SELECT p FROM Posts p WHERE p.isActive = true AND p.moderationStatus = :status AND p.moderatorId = :moder")
    Page<Posts> findAllPostsByModerationStatusAndUser(@Param("status") ModerationStatus status, @Param("moder") Users user, Pageable pageable);

    @Query("SELECT p FROM Posts p join TagToPost tp on tp.post=p.id join Tags t on t.id=tp.tag " +
            "WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time <= NOW() and t.name LIKE :tag group by p.id")
    Page<Posts> findAllActiveByTag(@Param("tag") String tag, Pageable pageable);

    @Query("SELECT p FROM Posts p WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time <= NOW() and p.id = :id")
    Page<Posts> findById(@Param("id") int id, Pageable pageable);

    @Query("select p from Posts p where p.id = :id")
    Page<Posts> findByIdModer(@Param("id") int id, Pageable pageable);

    @Query("SELECT p FROM Posts p WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time <= NOW()" +
            " AND to_char(p.time, 'YYYY-mm-dd') = :date_requested")
    Page<Posts> findAllPostsByDate(@Param("date_requested") String dateRequested, Pageable pageable);

    @Modifying
    @Query("update Posts p set p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void updateCount(@Param("id") int id);

    @Query("SELECT p FROM Posts p WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.user = :user ")
    Page<Posts> findAllPublished(@Param("user") Users user, Pageable pageable);

    @Query("SELECT p FROM Posts p WHERE p.isActive = true AND p.moderationStatus = 'NEW' AND p.user = :user ")
    Page<Posts> findAllPending(@Param("user") Users user, Pageable pageable);

    @Query("SELECT p FROM Posts p WHERE p.isActive = true AND p.moderationStatus = 'DECLINED' AND p.user = :user ")
    Page<Posts> findAllDeclined(@Param("user") Users user, Pageable pageable);

    @Query("SELECT p FROM Posts p WHERE p.isActive = false AND p.user = :user")
    Page<Posts> findAllInactive(@Param("user") Users user, Pageable pageable);

    @Query("SELECT count(p) FROM Posts p WHERE p.moderationStatus = 'NEW' and p.isActive = true")
    int getCountOfNotModeratedPosts();

    @Query("SELECT count(p) FROM Posts p WHERE p.user = :user and p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time <= NOW()")
    int getCountOfUserPosts(@Param("user") Users user);

    @Query("SELECT COALESCE(sum(p.viewCount),0) FROM Posts p WHERE p.user = :user and p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time <= NOW()")
    int getCountOfUserPostsView(@Param("user") Users user);

    @Query("SELECT count(p) FROM Posts p WHERE p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time <= NOW()")
    int getCountOfAllPosts();

    @Query("SELECT COALESCE(sum(p.viewCount),0) FROM Posts p WHERE  p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time <= NOW()")
    int getCountOfAllPostsView();

    @Modifying
    @Query("update Posts p set p.time = :time, p.moderationStatus = :status , p.isActive = :active, p.title = :title, p.text = :text  WHERE p.id = :id ")
    void updatePost(@Param("status") ModerationStatus status, @Param("text") String text, @Param("title") String title, @Param("active") boolean active, @Param("time") Instant time, @Param("id") int id);

    @Modifying
    @Query("update Posts p set p.moderationStatus = :status, p.moderatorId = :moder  WHERE p.id = :id ")
    void updateModerationStatusPost(@Param("status") ModerationStatus status, @Param("moder") Users moder, @Param("id") int id);

    @Query("SELECT p FROM Posts p WHERE p.user = :user and p.isActive = true AND p.moderationStatus = 'ACCEPTED' order by p.time  asc")
    List<Posts> getFirstPostOfUser(@Param("user") Users user);

    @Query(value = "SELECT  p.* FROM Posts p WHERE p.is_active = true AND p.moderation_status = 'ACCEPTED' order by p.time ASC LIMIT 1", nativeQuery = true)
    Posts getFirstPostDataOfAll();

}
