package org.example.repository;

import org.example.model.CaptchaCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.Instant;

@Repository
@Transactional
public interface CaptchaCodeRepository extends JpaRepository<CaptchaCodes, Integer> {

    @Modifying
    void deleteByTimeBefore(Instant date);

    CaptchaCodes findBySecretCode(String secretCode);
}
