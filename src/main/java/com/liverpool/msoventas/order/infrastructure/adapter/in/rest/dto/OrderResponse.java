package com.liverpool.msoventas.order.infrastructure.adapter.in.rest.dto;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.order.domain.model.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de salida que representa un pedido en la respuesta HTTP.
 *
 * <p>Contiene una clase interna {@link OrderItemResponse} para representar
 * cada articulo del pedido sin exponer los modelos de dominio ni los documentos
 * de persistencia.</p>
 */
@Getter
@Builder
public class OrderResponse {

    @Schema(description = "Identificador unico del pedido", example = "order_001")
    private String id;

    @Schema(description = "ID del cliente que realizo el pedido", example = "cust_001")
    private String customerId;

    @Schema(description = "ID de la direccion de entrega", example = "deliv_001")
    private String deliveryId;

    @Schema(description = "Lista de articulos del pedido")
    private List<OrderItemResponse> items;

    @Schema(description = "Total calculado automaticamente (suma de precio x cantidad)", example = "25999.98")
    private BigDecimal total;

    @Schema(description = "Estado actual del pedido", example = "PENDING",
            allowableValues = {"PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED"})
    private String status;

    @Schema(description = "Fecha estimada de entrega", example = "2026-03-15")
    private LocalDate estimatedDeliveryDate;

    @Schema(description = "Fecha y hora de creacion del pedido", example = "2026-03-01T10:30:00")
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
