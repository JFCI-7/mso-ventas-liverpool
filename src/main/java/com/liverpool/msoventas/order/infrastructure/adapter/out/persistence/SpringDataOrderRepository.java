package com.liverpool.msoventas.order.infrastructure.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SpringDataOrderRepository
        extends MongoRepository<OrderDocument, String> {

    @Query("{ 'items.displayNameNormalized': { $regex: ?0, $options: 'i' } }")
    List<OrderDocument> findByItemsDisplayNameNormalized(String displayNameNormalized);
}
