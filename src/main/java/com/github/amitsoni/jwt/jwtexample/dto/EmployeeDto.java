package com.github.amitsoni.jwt.jwtexample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.amitsoni.jwt.jwtexample.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    @JsonProperty("employee_id")
    private Long employeeId;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("mobile_no")
    private Long mobileNo;

    @JsonProperty("age")
    private Long age;

    @JsonProperty("gender")
    private Gender gender;

    @Override
    public String toString() {
        return "EmployeeDto [ employeeId = " + this.employeeId +
                ", employeeName = " + this.employeeName +
                ", email = " + this.email +
                ", mobileNo = " + this.mobileNo +
                ", age = " + this.age +
                ", gender = " + this.gender + " ]";
    }
}
