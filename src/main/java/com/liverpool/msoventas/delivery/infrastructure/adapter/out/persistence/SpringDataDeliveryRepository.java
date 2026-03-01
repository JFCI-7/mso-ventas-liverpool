package com.liverpool.msoventas.delivery.infrastructure.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataDeliveryRepository
        extends MongoRepository<DeliveryDocument, String> {

    List<DeliveryDocument> findByCustomerId(String customerId);
}
