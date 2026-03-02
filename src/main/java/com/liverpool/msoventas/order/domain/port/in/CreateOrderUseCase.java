package com.liverpool.msoventas.order.domain.port.in;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.shared.domain.model.Result;

/**
 * Puerto de entrada (caso de uso) para crear un nuevo pedido.
 */
public interface CreateOrderUseCase {

    /**
     * Crea un nuevo pedido a partir de los datos proporcionados.
     *
     * <p>La capa de aplicacion calculara el total automaticamente
     * y asignara el estado inicial {@link com.liverpool.msoventas.order.domain.model.OrderStatus#PENDING}.</p>
     *
     * @param order datos del pedido a crear (sin total ni estado)
     * @return {@link Result} con el pedido creado y persistido
     */
    Result<Order> create(Order order);
}
