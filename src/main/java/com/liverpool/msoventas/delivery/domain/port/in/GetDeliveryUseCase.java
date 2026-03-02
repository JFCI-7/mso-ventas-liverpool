package com.liverpool.msoventas.delivery.domain.port.in;

import com.liverpool.msoventas.delivery.domain.model.Delivery;
import com.liverpool.msoventas.shared.domain.model.Result;

import java.util.List;

/**
 * Puerto de entrada (caso de uso) para consultar direcciones de entrega.
 */
public interface GetDeliveryUseCase {

    /**
     * Busca una direccion de entrega por su identificador unico.
     *
     * @param id identificador de la direccion
     * @return {@link Result} con la direccion encontrada, o con
     *         {@link com.liverpool.msoventas.shared.domain.model.ErrorType#NOT_FOUND}
     *         si no existe
     */
    Result<Delivery> findById(String id);

    /**
     * Recupera todas las direcciones de entrega asociadas a un cliente.
     *
     * @param customerId identificador del cliente
     * @return {@link Result} con la lista de direcciones; puede estar vacia
     */
    Result<List<Delivery>> findByCustomerId(String customerId);
}
