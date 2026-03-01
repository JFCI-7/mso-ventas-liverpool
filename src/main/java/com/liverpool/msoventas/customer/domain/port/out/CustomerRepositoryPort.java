package com.liverpool.msoventas.customer.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.liverpool.msoventas.customer.domain.model.Customer;

public interface CustomerRepositoryPort {
	
	Customer save(Customer customer);

    Optional<Customer> findById(String id);

    List<Customer> findAll();

    boolean existsById(String id);

    void deleteById(String id);

}
