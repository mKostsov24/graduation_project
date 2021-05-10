package org.example.model.dto;

import lombok.Data;
import org.example.model.CaptchaCodes;

@Data
public class CaptchaCodesDTO {
    private String secret;
    private String image;

    public CaptchaCodesDTO(CaptchaCodes captchaCodes) {
        this.secret = captchaCodes.getCode();
        this.image = "data:image/png;charset=utf-8;base64, " + captchaCodes.getSecretCode();
    }
}
