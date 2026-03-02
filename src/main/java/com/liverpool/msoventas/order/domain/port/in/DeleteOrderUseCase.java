package com.liverpool.msoventas.order.domain.port.in;

import com.liverpool.msoventas.shared.domain.model.Result;

/**
 * Puerto de entrada (caso de uso) para eliminar un pedido del sistema.
 */
public interface DeleteOrderUseCase {

    /**
     * Elimina el pedido con el identificador dado.
     *
     * @param id identificador del pedido a eliminar
     * @return {@link Result} vacio en caso de exito, o con
     *         {@link com.liverpool.msoventas.shared.domain.model.ErrorType#NOT_FOUND}
     *         si no existe
     */
    Result<Void> deleteById(String id);
}
