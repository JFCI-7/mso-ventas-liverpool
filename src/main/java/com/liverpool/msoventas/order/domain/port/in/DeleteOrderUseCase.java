package com.liverpool.msoventas.order.domain.port.in;

import com.liverpool.msoventas.shared.domain.model.Result;

public interface DeleteOrderUseCase {
    Result<Void> deleteById(String id);
}
