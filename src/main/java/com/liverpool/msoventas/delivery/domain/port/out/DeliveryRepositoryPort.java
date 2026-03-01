package com.liverpool.msoventas.delivery.domain.port.out;

import com.liverpool.msoventas.delivery.domain.model.Delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepositoryPort {
    Delivery save(Delivery delivery);
    Optional<Delivery> findById(String id);
    List<Delivery> findByCustomerId(String customerId);
    boolean existsById(String id);
    void deleteById(String id);
}
