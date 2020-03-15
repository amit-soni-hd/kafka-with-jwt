package com.github.amitsoni.jwt.jwtexample.security.module;

import com.github.amitsoni.jwt.jwtexample.module.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Component
@NoArgsConstructor
@Data
@AllArgsConstructor
public class EmployeeDetails implements UserDetails {

    private String email;
    private String password;
    private String employeeName;
    private Long employeeId;
    private Long mobileNo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public UserDetails create(Employee employee) {
        return new EmployeeDetails(
                employee.getEmail(),
                employee.getPassword(),
                employee.getEmployeeName(),
                employee.getEmployeeId(),
                employee.getMobileNo()
        );
    }
}
