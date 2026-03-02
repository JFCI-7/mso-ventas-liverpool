package com.liverpool.msoventas.order.infrastructure.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDocument {

    private String productCode;
    private String displayName;
    private String displayNameNormalized;   // ← campo para búsqueda flexible
    private int quantity;
    private BigDecimal price;
}
