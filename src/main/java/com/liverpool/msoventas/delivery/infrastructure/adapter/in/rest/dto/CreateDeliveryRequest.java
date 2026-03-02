package com.liverpool.msoventas.delivery.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "ID del cliente propietario de la direccion", example = "cust_001")
    @NotBlank(message = "customerId is required")
    private String customerId;

    @Schema(description = "Nombre descriptivo de la direccion", example = "Casa")
    @NotBlank(message = "alias is required")
    private String alias;

    @Schema(description = "Calle y numero", example = "Av. Insurgentes Sur 1234")
    @NotBlank(message = "street is required")
    private String street;

    @Schema(description = "Colonia o fraccionamiento", example = "Del Valle")
    @NotBlank(message = "colony is required")
    private String colony;

    @Schema(description = "Ciudad", example = "Ciudad de Mexico")
    @NotBlank(message = "city is required")
    private String city;

    @Schema(description = "Estado o entidad federativa", example = "CDMX")
    @NotBlank(message = "state is required")
    private String state;

    @Schema(description = "Codigo postal", example = "03100")
    @NotBlank(message = "zipCode is required")
    private String zipCode;

    @Schema(description = "Pais", example = "Mexico")
    @NotBlank(message = "country is required")
    private String country;
}
