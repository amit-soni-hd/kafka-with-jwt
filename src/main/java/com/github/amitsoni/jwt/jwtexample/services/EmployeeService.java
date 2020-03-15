package com.github.amitsoni.jwt.jwtexample.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.amitsoni.jwt.jwtexample.dto.EmployeeDto;
import com.github.amitsoni.jwt.jwtexample.module.Employee;
import com.github.amitsoni.jwt.jwtexample.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@Slf4j
public class EmployeeService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EmployeeDto registerEmployee(Employee employee)  {
        EmployeeDto employeeDto = null;

        try {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            employee.setRePassword(passwordEncoder.encode(employee.getRePassword()));

            Employee employeeDetails = employeeRepository.save(employee);
            log.info("Successfully saved employee with details {}", objectMapper.writeValueAsString(employeeDetails));
            employeeDto = modelMapper.map(employeeDetails, EmployeeDto.class);
            log.info("Convert employee to employee dto with details {}", objectMapper.writeValueAsString(employeeDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return employeeDto;
    }

    public EmployeeDto getEmployeeById(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if( employee.get() != null ) {
            log.info("Employee found with name {}", employee.get().getEmployeeName());
            return modelMapper.map(employee.get(), EmployeeDto.class);
        }
        log.info("Employee doesn't exist with id {}", employeeId);
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Finding user by emailId :"+ username);
        Employee employee =  employeeRepository.findByEmail(username).get();
        if(employee == null) {
            throw new UsernameNotFoundException("User does not exist, please enter valid user emailId");
        }
        return new User(employee.getEmail(), employee.getPassword(),true, true, true, true, emptyList());
    }
}
