package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.utils.CaptchaUtils;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor(force = true)
public class CaptchaCodes {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private Instant time;
    @Column(nullable = false, columnDefinition = "text")
    private String code;
    @Column(nullable = false, columnDefinition = "text")
    private String secretCode;

    public CaptchaCodes(int codeLength) {
        this.time = Instant.now();
        this.code = CaptchaUtils.generateRandomString(codeLength);
        this.secretCode = CaptchaUtils.getImageBase64(getCode());
    }
}
