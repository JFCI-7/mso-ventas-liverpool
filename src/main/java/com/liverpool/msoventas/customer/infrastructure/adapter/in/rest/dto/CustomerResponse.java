package com.liverpool.msoventas.customer.infrastructure.adapter.in.rest.dto;

import com.liverpool.msoventas.customer.domain.model.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO de salida que representa la informacion de un cliente en la respuesta HTTP.
 *
 * <p>Expone unicamente los campos relevantes para el consumidor de la API,
 * desacoplando la representacion externa del modelo de dominio interno.</p>
 */
@Getter
@Builder
public class CustomerResponse {

    @Schema(description = "Identificador unico del cliente", example = "abc123")
    private String id;

    @Schema(description = "Nombre(s) del cliente", example = "Juan")
    private String firstName;

    @Schema(description = "Apellido paterno del cliente", example = "Perez")
    private String lastName;

    @Schema(description = "Apellido materno del cliente", example = "Garcia")
    private String motherLastName;

    @Schema(description = "Correo electronico del cliente", example = "juan.perez@email.com")
    private String email;

    public static CustomerResponse fromDomain(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .motherLastName(customer.getMotherLastName())
                .email(customer.getEmail())
                .build();
    }

}
