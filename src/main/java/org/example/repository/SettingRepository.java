package org.example.repository;

import lombok.NonNull;
import org.example.model.GlobalSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SettingRepository extends JpaRepository<GlobalSettings, Integer> {
    public @NonNull List<GlobalSettings> findAll();

    @Query("select  g.value  from GlobalSettings g where g.code = 'STATISTICS_IS_PUBLIC'")
    String getStatistics();

    @Modifying
    @Query("UPDATE GlobalSettings g SET g.value = :value WHERE g.code = :code")
    void updateValueByCode(@Param("value") String value, @Param("code") String code);
}

