package com.github.amitsoni.jwt.jwtexample.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.amitsoni.jwt.jwtexample.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("employee_id")
    @Column(name = "employee_id", unique = true, updatable = false, nullable = false)
    private Long employeeId;

    @JsonProperty("employee_name")
    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @JsonProperty("email")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @JsonProperty("password")
    @Column(name = "password", nullable = false)
    private String password;

    @JsonProperty("re_password")
    @Column(name = "re_password", nullable = false)
    private String rePassword;

    @JsonProperty("mobile_no")
    @Column(name = "mobile_no", length = 10)
    private Long mobileNo;

    @JsonProperty("age")
    @Column(name = "age", nullable = false)
    private Long age;

    @JsonProperty("gender")
    @Column(name = "gender", nullable = false)
    private Gender gender;

//    @JsonProperty("address")
//    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Address.class)
//    private Set<Address> address;

//    @Embedded
//    @JsonProperty(value = "company_address", required = true)
//    @Column(name = "company_address", nullable = false)
//    @AttributeOverrides({
//            @AttributeOverride(name = "addressNo", column = @Column(name = "company_address_no", nullable = false)),
//            @AttributeOverride(name = "landMark", column = @Column(name = "landmark", nullable = false)),
//            @AttributeOverride(name = "city", column = @Column(name = "city", nullable = false)),
//            @AttributeOverride(name = "state", column = @Column(name = "state", nullable = false)),
//            @AttributeOverride(name = "pinCode", column = @Column(name = "pin_code", nullable = false, length = 6)),
//            @AttributeOverride(name = "country", column = @Column(name = "country", nullable = false)),
//            @AttributeOverride(name = "companyType", column = @Column(name = "company_type", nullable = false))
//    })
//    private CompanyAddress companyAddress;
}
