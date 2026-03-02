package com.liverpool.msoventas.order.domain.port.in;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.order.domain.model.OrderStatus;
import com.liverpool.msoventas.shared.domain.model.Result;

/**
 * Puerto de entrada (caso de uso) para actualizar el estado de un pedido.
 *
 * <p>Por diseno, solo se permite modificar el {@link OrderStatus}; el resto
 * de los datos del pedido son inmutables una vez creado.</p>
 */
public interface UpdateOrderUseCase {

    /**
     * Actualiza el estado de un pedido existente.
     *
     * @param id     identificador del pedido
     * @param status nuevo estado a asignar
     * @return {@link Result} con el pedido actualizado, o con
     *         {@link com.liverpool.msoventas.shared.domain.model.ErrorType#NOT_FOUND}
     *         si no existe
     */
    Result<Order> updateStatus(String id, OrderStatus status);
}
