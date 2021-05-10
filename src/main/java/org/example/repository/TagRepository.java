package org.example.repository;

import org.example.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TagRepository extends JpaRepository<Tags, Integer> {

    @Query(value = "Select t.*  from tags t join tag_to_post ttp on t.id = ttp.tag_id join posts p" +
            " on ttp.post_id = p.id where p.is_Active = '1' AND p.moderation_status = 'ACCEPTED' AND p.time <= NOW()", nativeQuery = true)
    List<Tags> getAllTagsByActivePosts();

    Tags findByName(String name);
}
