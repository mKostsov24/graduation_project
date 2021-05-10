package org.example.service;

import org.example.model.CaptchaCodes;
import org.example.model.dto.CaptchaCodesDTO;
import org.example.repository.CaptchaCodeRepository;
import org.example.service.api.CaptchaCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CaptchaCodeServiceImpl implements CaptchaCodeService {

    private final CaptchaCodeRepository captchaCodeRepository;

    @Autowired
    public CaptchaCodeServiceImpl(CaptchaCodeRepository captchaCodeRepository) {
        this.captchaCodeRepository = captchaCodeRepository;
    }

    public ResponseEntity<?> getCaptcha() {
        deleteOutDatedCaptchas();
        return ResponseEntity.ok(
                new CaptchaCodesDTO(captchaCodeRepository.save(new CaptchaCodes(6))));
    }

    private void deleteOutDatedCaptchas() {
        captchaCodeRepository.deleteByTimeBefore(Instant.now().minusSeconds(3600));
    }

}
