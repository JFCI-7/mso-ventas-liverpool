package com.liverpool.msoventas.customer.infrastructure.adapter.in.rest.dto;

import com.liverpool.msoventas.customer.domain.model.Customer;

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
	
	private String id;
    private String firstName;
    private String lastName;
    private String motherLastName;
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
