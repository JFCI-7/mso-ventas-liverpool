package com.liverpool.msoventas.delivery.domain.port.in;

import com.liverpool.msoventas.shared.domain.model.Result;

/**
 * Puerto de entrada (caso de uso) para eliminar una direccion de entrega.
 */
public interface DeleteDeliveryUseCase {

    /**
     * Elimina la direccion de entrega con el identificador dado.
     *
     * @param id identificador de la direccion a eliminar
     * @return {@link Result} vacio en caso de exito, o con
     *         {@link com.liverpool.msoventas.shared.domain.model.ErrorType#NOT_FOUND}
     *         si no existe
     */
    Result<Void> deleteById(String id);
}