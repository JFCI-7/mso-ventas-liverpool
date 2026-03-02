package com.liverpool.msoventas.order.infrastructure.adapter.in.rest.dto;

import com.liverpool.msoventas.order.domain.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para actualizar el estado de un pedido existente.
 *
 * <p>Solo contiene el nuevo estado ({@link com.liverpool.msoventas.order.domain.model.OrderStatus})
 * ya que es el unico dato modificable despues de la creacion del pedido.</p>
 */
@Getter
@NoArgsConstructor
public class UpdateOrderStatusRequest {

    @NotNull(message = "status is required")
    private OrderStatus status;
}
