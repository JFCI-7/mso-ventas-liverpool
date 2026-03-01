package com.liverpool.msoventas.customer.infrastructure.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpringDataCustomerRepository extends MongoRepository<CustomerDocument, String> {
	
	Optional<CustomerDocument> findByEmail(String email);

    boolean existsByEmail(String email);

}
