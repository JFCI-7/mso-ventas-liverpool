package com.liverpool.msoventas.delivery.domain.port.in;

import com.liverpool.msoventas.delivery.domain.model.Delivery;
import com.liverpool.msoventas.shared.domain.model.Result;

/**
 * Puerto de entrada (caso de uso) para registrar una nueva direccion de entrega.
 */
public interface CreateDeliveryUseCase {

    /**
     * Registra una nueva direccion de entrega para un cliente.
     *
     * @param delivery datos de la direccion a registrar
     * @return {@link Result} con la direccion creada en caso de exito
     */
    Result<Delivery> create(Delivery delivery);
}
