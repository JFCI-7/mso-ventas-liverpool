package com.liverpool.msoventas.order.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Codigo unico del producto en el catalogo", example = "SKU-001")
    @NotBlank(message = "productCode is required")
    private String productCode;

    @Schema(description = "Nombre del producto tal como se muestra al cliente", example = "Televisor Samsung 55\"")
    @NotBlank(message = "displayName is required")
    private String displayName;

    @Schema(description = "Cantidad de unidades solicitadas", example = "2")
    @Min(value = 1, message = "quantity must be at least 1")
    private int quantity;

    @Schema(description = "Precio unitario del producto", example = "12999.99")
    @NotNull(message = "price is required")
    private BigDecimal price;
}
