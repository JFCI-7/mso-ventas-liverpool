package com.liverpool.msoventas.customer.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para la creacion de un nuevo cliente.
 *
 * <p>Contiene las validaciones de Bean Validation que se ejecutan
 * antes de que el controlador delegue al caso de uso.</p>
 */
@Getter
@NoArgsConstructor
public class CreateCustomerRequest {
	
    @Schema(description = "Nombre(s) del cliente", example = "Juan")
    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;

    @Schema(description = "Apellido paterno del cliente", example = "Perez")
    @NotBlank(message = "El apellido paterno es obligatorio")
    private String lastName;

    @Schema(description = "Apellido materno del cliente", example = "Garcia")
    @NotBlank(message = "El apellido materno es obligatorio")
    private String motherLastName;

    @Schema(description = "Correo electronico del cliente", example = "juan.perez@email.com")
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo no es válido")
    private String email;

}
