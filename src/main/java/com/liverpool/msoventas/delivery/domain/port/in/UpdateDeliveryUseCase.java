package com.liverpool.msoventas.delivery.domain.port.in;

import com.liverpool.msoventas.delivery.domain.model.Delivery;
import com.liverpool.msoventas.shared.domain.model.Result;

/**
 * Puerto de entrada (caso de uso) para actualizar una direccion de entrega existente.
 */
public interface UpdateDeliveryUseCase {

    /**
     * Actualiza los datos de una direccion de entrega.
     *
     * @param id       identificador de la direccion a actualizar
     * @param delivery objeto con los nuevos datos de la direccion
     * @return {@link Result} con la direccion actualizada, o con
     *         {@link com.liverpool.msoventas.shared.domain.model.ErrorType#NOT_FOUND}
     *         si no existe
     */
    Result<Delivery> update(String id, Delivery delivery);
}
