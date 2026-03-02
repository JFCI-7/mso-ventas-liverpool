package com.liverpool.msoventas.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private String productCode;
    private String displayName;
    private int quantity;
    private BigDecimal price;
}
