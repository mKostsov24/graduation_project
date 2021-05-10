package org.example.repository;

import org.example.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CalendarRepository extends JpaRepository<Posts, Integer> {

    @Query("SELECT DISTINCT to_char(p.time, 'YYYY') FROM Posts p  WHERE p.isActive = true AND " +
            "p.moderationStatus = 'ACCEPTED' AND p.time <= NOW() order by to_char(p.time, 'YYYY')")
    List<String> findAllYears();

    @Query("SELECT to_char(p.time, 'YYYY-mm-dd') FROM Posts p  WHERE p.isActive = true AND " +
            "p.moderationStatus = 'ACCEPTED' AND p.time <= NOW() AND to_char(p.time, 'YYYY') = :year_d")
    List<String> findAllPostsByYear(@Param("year_d") String year);

}
