package com.liverpool.msoventas.customer.domain.port.in;

import com.liverpool.msoventas.customer.domain.model.Customer;
import com.liverpool.msoventas.shared.domain.model.Result;

/**
 * Puerto de entrada (caso de uso) para actualizar los datos de un cliente existente.
 */
public interface UpdateCustomerUseCase {

    /**
     * Actualiza los datos de un cliente identificado por su {@code id}.
     *
     * @param id       identificador del cliente a actualizar
     * @param customer objeto con los nuevos datos del cliente
     * @return {@link Result} con el cliente actualizado, o con
     *         {@link com.liverpool.msoventas.shared.domain.model.ErrorType#NOT_FOUND}
     *         si no existe
     */
    Result<Customer> update(String id, Customer customer);

}
