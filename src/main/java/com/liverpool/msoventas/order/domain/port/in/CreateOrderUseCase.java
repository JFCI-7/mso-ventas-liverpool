package com.liverpool.msoventas.order.domain.port.in;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.shared.domain.model.Result;

public interface CreateOrderUseCase {
    Result<Order> create(Order order);
}
