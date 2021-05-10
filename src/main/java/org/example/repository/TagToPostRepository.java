package org.example.repository;

import org.example.model.Posts;
import org.example.model.TagToPost;
import org.example.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TagToPostRepository extends JpaRepository<TagToPost, Integer> {

    TagToPost getByTagAndPost(Tags tag, Posts post);

    @Modifying
    @Query("DELETE from TagToPost tp where tp.id = :id")
    void deleteById(@Param("id") int id);

}
