package com.github.amitsoni.jwt.jwtexample.repository;

import com.github.amitsoni.jwt.jwtexample.module.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
