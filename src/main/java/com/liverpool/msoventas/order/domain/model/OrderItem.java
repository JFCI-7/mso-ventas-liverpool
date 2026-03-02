package com.liverpool.msoventas.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Objeto de valor que representa un articulo dentro de un pedido.
 *
 * <p>Incluye el codigo de producto, nombre para mostrar, cantidad y precio
 * unitario. El total de la linea se calcula multiplicando {@code price} por
 * {@code quantity} en la capa de aplicacion.</p>
 */
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
