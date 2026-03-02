package com.liverpool.msoventas.order.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
