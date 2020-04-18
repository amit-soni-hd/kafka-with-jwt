package com.github.amitsoni.jwt.jwtexample.controller;

import com.github.amitsoni.jwt.jwtexample.dto.EmployeeDto;
import com.github.amitsoni.jwt.jwtexample.kafka.service.ProducerService;
import com.github.amitsoni.jwt.jwtexample.module.Employee;
import com.github.amitsoni.jwt.jwtexample.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;
    private ProducerService producerService;
    private ModelMapper modelMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ProducerService producerService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.producerService = producerService;
        this.modelMapper = modelMapper;
    }

    private final String TOPIC = "employee";

    @PostMapping("/register")
    public ResponseEntity<EmployeeDto> register(@RequestBody Employee employee) {

        log.info("Employee name : {}", employee.getEmployeeName());
        EmployeeDto employeeDto = employeeService.registerEmployee(employee);
        producerService.produceRecord(TOPIC, employeeDto);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(employeeDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEmployeeById(@PathVariable Long id) {
//        EmployeeDto employeeDto = employeeService.getEmployeeById(id);
//        return ResponseEntity.status(HttpStatus.OK).body(employeeDto);

        return ResponseEntity.status(HttpStatus.OK).body("employee");
    }
}
