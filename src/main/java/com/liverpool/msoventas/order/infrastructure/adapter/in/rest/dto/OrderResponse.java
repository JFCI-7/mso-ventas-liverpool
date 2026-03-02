package com.liverpool.msoventas.order.infrastructure.adapter.in.rest.dto;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.order.domain.model.OrderItem;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponse {

    private String id;
    private String customerId;
    private String deliveryId;
    private List<OrderItemResponse> items;
    private BigDecimal total;
    private String status;
    private LocalDate estimatedDeliveryDate;
    private LocalDateTime createdAt;

    public static OrderResponse fromDomain(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(OrderItemResponse::fromDomain)
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .deliveryId(order.getDeliveryId())
                .items(itemResponses)
                .total(order.getTotal())
                .status(order.getStatus() != null ? order.getStatus().name() : null)
                .estimatedDeliveryDate(order.getEstimatedDeliveryDate())
                .createdAt(order.getCreatedAt())
                .build();
    }

    @Getter
    @Builder
    public static class OrderItemResponse {
        private String productCode;
        private String displayName;
        private int quantity;
        private BigDecimal price;

        public static OrderItemResponse fromDomain(OrderItem item) {
            return OrderItemResponse.builder()
                    .productCode(item.getProductCode())
                    .displayName(item.getDisplayName())
                    .quantity(item.getQuantity())
                    .price(item.getPrice())
                    .build();
        }
    }
}
