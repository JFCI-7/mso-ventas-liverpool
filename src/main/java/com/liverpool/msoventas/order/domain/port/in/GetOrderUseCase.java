package com.liverpool.msoventas.order.domain.port.in;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.shared.domain.model.Result;

import java.util.List;

public interface GetOrderUseCase {
    Result<Order> findById(String id);
    Result<List<Order>> findAll();
    Result<List<Order>> findByDisplayName(String displayName);
}