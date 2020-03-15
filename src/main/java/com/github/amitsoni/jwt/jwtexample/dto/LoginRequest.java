package com.github.amitsoni.jwt.jwtexample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @JsonProperty("email_id")
    private String email;

    @JsonProperty("password")
    private String password;
}
