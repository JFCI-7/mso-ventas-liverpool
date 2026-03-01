package com.liverpool.msoventas.delivery.domain.port.in;

import com.liverpool.msoventas.delivery.domain.model.Delivery;
import com.liverpool.msoventas.shared.domain.model.Result;

public interface CreateDeliveryUseCase {
    Result<Delivery> create(Delivery delivery);
}
