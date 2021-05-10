package org.example.model.dto.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class NewUserDTO {
    @JsonProperty(value = "e_mail")
    private final String email;
    private final String password;
    private final String name;
    private final String captcha;
    @JsonProperty(value = "captcha_secret")
    private final String captchaSecret;
}
