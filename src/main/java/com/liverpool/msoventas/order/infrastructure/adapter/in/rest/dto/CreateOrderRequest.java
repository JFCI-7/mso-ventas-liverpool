package com.liverpool.msoventas.order.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "ID del cliente que realiza el pedido", example = "cust_001")
    @NotBlank(message = "customerId is required")
    private String customerId;

    @Schema(description = "ID de la direccion de entrega seleccionada", example = "deliv_001")
    @NotBlank(message = "deliveryId is required")
    private String deliveryId;

    @Schema(description = "Lista de articulos del pedido; debe contener al menos uno")
    @NotEmpty(message = "items must not be empty")
    @Valid
    private List<OrderItemRequest> items;

    @Schema(description = "Fecha estimada de entrega (formato ISO: yyyy-MM-dd)", example = "2026-03-15")
    @NotNull(message = "estimatedDeliveryDate is required")
    private LocalDate estimatedDeliveryDate;
}
