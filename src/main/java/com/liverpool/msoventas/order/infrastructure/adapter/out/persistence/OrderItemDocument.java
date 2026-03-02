package com.liverpool.msoventas.order.infrastructure.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Subdocumento MongoDB embebido en {@link OrderDocument} que representa un articulo del pedido.
 *
 * <p>El campo {@code displayNameNormalized} almacena el nombre del articulo en minusculas
 * y sin acentos, habilitando la busqueda flexible mediante expresiones regulares en MongoDB.</p>
 */
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
