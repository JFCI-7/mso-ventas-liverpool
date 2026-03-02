package com.liverpool.msoventas.delivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entidad de dominio que representa una direccion de entrega asociada a un cliente.
 *
 * <p>Un cliente puede tener multiples direcciones de entrega. Cada una se identifica
 * por un {@code alias} elegido por el cliente (p. ej. "Casa", "Trabajo") y contiene
 * los datos postales completos para la logistica de envio.</p>
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    private String id;
    private String customerId;
    private String alias;
    private String street;
    private String colony;
    private String city;
    private String state;
    private String zipCode;
    private String country;

}
