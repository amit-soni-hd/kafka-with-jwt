package com.github.amitsoni.jwt.jwtexample.repository;

import com.github.amitsoni.jwt.jwtexample.module.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

}
