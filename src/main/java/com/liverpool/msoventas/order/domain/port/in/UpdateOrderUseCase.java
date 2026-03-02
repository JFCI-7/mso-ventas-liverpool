package com.liverpool.msoventas.order.domain.port.in;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.order.domain.model.OrderStatus;
import com.liverpool.msoventas.shared.domain.model.Result;

public interface UpdateOrderUseCase {
    Result<Order> updateStatus(String id, OrderStatus status);
}
