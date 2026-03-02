package com.liverpool.msoventas.order.infrastructure.adapter.in.rest.dto;

import com.liverpool.msoventas.order.domain.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Nuevo estado del pedido", example = "CONFIRMED",
            allowableValues = {"PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED"})
    @NotNull(message = "status is required")
    private OrderStatus status;
}
