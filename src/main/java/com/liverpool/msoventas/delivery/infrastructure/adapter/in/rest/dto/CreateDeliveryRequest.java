package com.liverpool.msoventas.delivery.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para la creacion de una nueva direccion de entrega.
 *
 * <p>Todos los campos son obligatorios. Las validaciones se ejecutan mediante
 * Bean Validation antes de que el controlador delegue al caso de uso.</p>
 */
@Getter
@NoArgsConstructor
public class CreateDeliveryRequest {

    @NotBlank(message = "customerId is required")
    private String customerId;

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
