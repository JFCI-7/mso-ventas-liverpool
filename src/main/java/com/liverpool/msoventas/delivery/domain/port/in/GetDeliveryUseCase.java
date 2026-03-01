package com.liverpool.msoventas.delivery.domain.port.in;

import com.liverpool.msoventas.delivery.domain.model.Delivery;
import com.liverpool.msoventas.shared.domain.model.Result;

import java.util.List;

public interface GetDeliveryUseCase {
    Result<Delivery> findById(String id);
    Result<List<Delivery>> findByCustomerId(String customerId);
}
