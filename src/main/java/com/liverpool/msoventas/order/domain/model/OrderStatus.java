package com.liverpool.msoventas.order.domain.model;

/**
 * Ciclo de vida de un pedido dentro del sistema.
 *
 * <p>Los estados avanzan secuencialmente segun el proceso logistico.
 * Solo el estado {@code CANCELLED} puede alcanzarse desde cualquier estado previo
 * a {@code DELIVERED}.</p>
 */
public enum OrderStatus {

    /** Pedido creado pero aun no confirmado por el sistema. */
    PENDING,

    /** Pedido confirmado y en proceso de preparacion. */
    CONFIRMED,

    /** Pedido enviado al transportista. */
    SHIPPED,

    /** Pedido entregado satisfactoriamente al cliente. */
    DELIVERED,

    /** Pedido cancelado antes de la entrega. */
    CANCELLED
}
