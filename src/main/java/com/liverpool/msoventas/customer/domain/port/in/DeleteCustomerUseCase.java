package com.liverpool.msoventas.customer.domain.port.in;

import com.liverpool.msoventas.shared.domain.model.Result;

/**
 * Puerto de entrada (caso de uso) para eliminar un cliente del sistema.
 */
public interface DeleteCustomerUseCase {

    /**
     * Elimina el cliente cuyo identificador coincide con {@code id}.
     *
     * @param id identificador del cliente a eliminar
     * @return {@link Result} vacio en caso de exito, o con
     *         {@link com.liverpool.msoventas.shared.domain.model.ErrorType#NOT_FOUND}
     *         si el cliente no existe
     */
    Result<Void> deleteById(String id);

}
