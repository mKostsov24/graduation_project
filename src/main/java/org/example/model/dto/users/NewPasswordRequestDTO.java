package org.example.model.dto.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewPasswordRequestDTO {
    private String code;
    private String password;
    private String captcha;
    @JsonProperty(value = "captcha_secret")
    private String captchaSecret;
}
