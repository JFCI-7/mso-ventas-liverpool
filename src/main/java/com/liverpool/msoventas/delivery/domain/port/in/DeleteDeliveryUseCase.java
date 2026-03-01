package com.liverpool.msoventas.delivery.domain.port.in;

import com.liverpool.msoventas.shared.domain.model.Result;

public interface DeleteDeliveryUseCase {
    Result<Void> deleteById(String id);
}