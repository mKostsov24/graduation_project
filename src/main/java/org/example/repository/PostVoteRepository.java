package org.example.repository;

import org.example.model.PostVotes;
import org.example.model.Posts;
import org.example.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PostVoteRepository extends JpaRepository<PostVotes, Integer> {
    PostVotes findByPostAndUser(Posts post, Users user);

    @Modifying
    @Query("update PostVotes v set v.value = :value WHERE v.id = :id")
    void setValue(@Param("id") int id,@Param("value") int value);

    @Query("SELECT count(v)  FROM PostVotes v join Users u on v.user = u.id join Posts p on p.id=v.post " +
            "WHERE u.id = :id and v.value = :value and p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time <= NOW() ")
    int getCountOfUserVotes(@Param("id") int id, @Param("value") int value);

    @Query("SELECT count(v)  FROM PostVotes v join Users u on v.user = u.id join Posts p on p.id=v.post " +
            "WHERE v.value = :value and p.isActive = true AND p.moderationStatus = 'ACCEPTED' AND p.time <= NOW()")
    int getCountOfAllVotes(@Param("value") int value);

}
