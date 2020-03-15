package com.github.amitsoni.jwt.jwtexample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    @JsonProperty("employeeId")
    private Long id;

    @JsonProperty("emailId")
    private String email;

    @JsonProperty("employeeName")
    private String employeeName;

    @JsonProperty("mobileNo")
    private Long mobileNo;

    @JsonProperty("type")
    private String type;

    @JsonProperty("token")
    private String token;

}
