package com.liverpool.msoventas.delivery.infrastructure.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Nombre descriptivo de la direccion", example = "Trabajo")
    @NotBlank(message = "alias is required")
    private String alias;

    @Schema(description = "Calle y numero", example = "Av. Reforma 500")
    @NotBlank(message = "street is required")
    private String street;

    @Schema(description = "Colonia o fraccionamiento", example = "Juarez")
    @NotBlank(message = "colony is required")
    private String colony;

    @Schema(description = "Ciudad", example = "Ciudad de Mexico")
    @NotBlank(message = "city is required")
    private String city;

    @Schema(description = "Estado o entidad federativa", example = "CDMX")
    @NotBlank(message = "state is required")
    private String state;

    @Schema(description = "Codigo postal", example = "06600")
    @NotBlank(message = "zipCode is required")
    private String zipCode;

    @Schema(description = "Pais", example = "Mexico")
    @NotBlank(message = "country is required")
    private String country;
}
