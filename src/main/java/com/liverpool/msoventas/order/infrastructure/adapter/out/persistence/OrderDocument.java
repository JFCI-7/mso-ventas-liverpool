package com.liverpool.msoventas.order.infrastructure.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Documento MongoDB que representa un pedido en la coleccion {@code orders}.
 *
 * <p>Los campos {@code customerId} y {@code deliveryId} estan indexados para
 * agilizar las consultas por cliente y por direccion de entrega. El estado del
 * pedido se almacena como {@code String} para facilitar la serializacion.</p>
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class OrderDocument {

    @Id
    private String id;

    @Indexed
    private String customerId;

    @Indexed
    private String deliveryId;

    private List<OrderItemDocument> items;
    private BigDecimal total;
    private String status;
    private LocalDate estimatedDeliveryDate;
    private LocalDateTime createdAt;
}
