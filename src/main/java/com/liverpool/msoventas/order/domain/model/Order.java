package com.liverpool.msoventas.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad de dominio que representa un pedido de compra.
 *
 * <p>Agrupa los articulos solicitados ({@link OrderItem}), el total calculado
 * automaticamente, la direccion de entrega asociada ({@code deliveryId}) y el
 * estado actual del pedido ({@link OrderStatus}). El campo {@code createdAt}
 * es asignado por el servicio de aplicacion al momento de la creacion.</p>
 */
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
