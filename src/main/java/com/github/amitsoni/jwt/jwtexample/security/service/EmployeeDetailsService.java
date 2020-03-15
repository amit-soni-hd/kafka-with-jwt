package com.github.amitsoni.jwt.jwtexample.security.service;

import com.github.amitsoni.jwt.jwtexample.module.Employee;
import com.github.amitsoni.jwt.jwtexample.repository.EmployeeRepository;
import com.github.amitsoni.jwt.jwtexample.security.module.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeDetails employeeDetails;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findByEmail(userName)
                .orElseThrow(() -> {
                    throw  new UsernameNotFoundException("Employee not found");
                });
        return employeeDetails.create(employee);
    }
}
