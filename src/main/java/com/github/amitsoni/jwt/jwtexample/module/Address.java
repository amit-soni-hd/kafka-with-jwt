package com.github.amitsoni.jwt.jwtexample.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.amitsoni.jwt.jwtexample.enums.Country;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "address")
public class Address {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true, nullable = false, name = "address_id")
    @JsonProperty("address_id")
    private Long addressId;

    @JsonProperty("house_no")
    @Column(name = "house_no")
    private String houseNo;

    @JsonProperty("landmark")
    @Column(name = "landmark")
    private String landMark;

    @JsonProperty("pin_code")
    @Column(name = "pin_code", length = 6, nullable = false)
    private Integer pinCode;

    @JsonProperty("state")
    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "city", nullable = false)
    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    @Column(name = "county", nullable = false)
    private Country country;

    @JsonProperty("address_type")
    @Column(name = "address_type", nullable = false)
    private AddressType addressType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}
