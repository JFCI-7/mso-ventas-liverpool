package com.liverpool.msoventas.order.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO de entrada que representa un articulo dentro de la solicitud de creacion de pedido.
 *
 * <p>Todos los campos son requeridos; la cantidad debe ser al menos 1 y
 * el precio no puede ser nulo.</p>
 */
@Getter
@NoArgsConstructor
public class OrderItemRequest {

    @NotBlank(message = "productCode is required")
    private String productCode;

    @NotBlank(message = "displayName is required")
    private String displayName;

    @Min(value = 1, message = "quantity must be at least 1")
    private int quantity;

    @NotNull(message = "price is required")
    private BigDecimal price;
}
