package com.github.amitsoni.jwt.jwtexample.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAddress {

    private String addressNo;
    private String landMark;
    private String city;
    private String state;
    private Long pinCode;
    private String country;
    private CompanyType companyType;
}

