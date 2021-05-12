package org.example.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private boolean result;
    @JsonProperty("user")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserLoginResponse userLoginResponse;
}
