package org.example.service.api;

import org.springframework.http.ResponseEntity;

public interface CaptchaCodeService {
    ResponseEntity<?> getCaptcha();
}
