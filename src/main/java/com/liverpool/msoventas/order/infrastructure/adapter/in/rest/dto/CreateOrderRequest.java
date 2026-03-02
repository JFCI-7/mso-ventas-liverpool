package com.liverpool.msoventas.order.infrastructure.adapter.in.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO de entrada para la creacion de un nuevo pedido.
 *
 * <p>La lista de articulos debe contener al menos un elemento y cada articulo
 * se valida en cascada mediante {@code @Valid}. El total no se recibe del cliente;
 * es calculado por la capa de aplicacion.</p>
 */
@Getter
@NoArgsConstructor
public class CreateOrderRequest {

    @NotBlank(message = "customerId is required")
    private String customerId;

    @NotBlank(message = "deliveryId is required")
    private String deliveryId;

    @NotEmpty(message = "items must not be empty")
    @Valid
    private List<OrderItemRequest> items;

    @NotNull(message = "estimatedDeliveryDate is required")
    private LocalDate estimatedDeliveryDate;
}
