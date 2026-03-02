package com.liverpool.msoventas.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String id;
    private String customerId;
    private String deliveryId;
    private List<OrderItem> items;
    private BigDecimal total;
    private OrderStatus status;
    private LocalDate estimatedDeliveryDate;
    private LocalDateTime createdAt;
}
