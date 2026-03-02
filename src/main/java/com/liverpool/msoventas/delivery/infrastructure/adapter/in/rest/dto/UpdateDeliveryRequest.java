package com.liverpool.msoventas.delivery.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para la actualizacion de una direccion de entrega existente.
 *
 * <p>No incluye {@code customerId} ya que el cliente propietario de la direccion
 * no puede modificarse durante una actualizacion (operacion PUT).</p>
 */
@Getter
@NoArgsConstructor
public class UpdateDeliveryRequest {

    @NotBlank(message = "alias is required")
    private String alias;

    @NotBlank(message = "street is required")
    private String street;

    @NotBlank(message = "colony is required")
    private String colony;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "state is required")
    private String state;

    @NotBlank(message = "zipCode is required")
    private String zipCode;

    @NotBlank(message = "country is required")
    private String country;
}
