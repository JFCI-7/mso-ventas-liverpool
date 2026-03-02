package com.liverpool.msoventas.order.domain.port.out;

import com.liverpool.msoventas.order.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(String id);
    List<Order> findAll();
    List<Order> findByDisplayName(String displayName);
    boolean existsById(String id);
    void deleteById(String id);
}
