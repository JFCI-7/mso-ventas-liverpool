package com.liverpool.msoventas.delivery.infrastructure.adapter.in.rest.dto;

import com.liverpool.msoventas.delivery.domain.model.Delivery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO de salida que representa una direccion de entrega en la respuesta HTTP.
 *
 * <p>Se construye a partir del modelo de dominio mediante el metodo fabrica
 * {@link #fromDomain(Delivery)}, manteniendo separada la representacion
 * externa del modelo interno.</p>
 */
@Getter
@Builder
public class DeliveryResponse {

    @Schema(description = "Identificador unico de la direccion", example = "deliv_001")
    private String id;

    @Schema(description = "ID del cliente propietario", example = "cust_001")
    private String customerId;

    @Schema(description = "Nombre descriptivo de la direccion", example = "Casa")
    private String alias;

    @Schema(description = "Calle y numero", example = "Av. Insurgentes Sur 1234")
    private String street;

    @Schema(description = "Colonia o fraccionamiento", example = "Del Valle")
    private String colony;

    @Schema(description = "Ciudad", example = "Ciudad de Mexico")
    private String city;

    @Schema(description = "Estado o entidad federativa", example = "CDMX")
    private String state;

    @Schema(description = "Codigo postal", example = "03100")
    private String zipCode;

    @Schema(description = "Pais", example = "Mexico")
    private String country;

    public static DeliveryResponse fromDomain(Delivery delivery) {
        return DeliveryResponse.builder()
                .id(delivery.getId())
                .customerId(delivery.getCustomerId())
                .alias(delivery.getAlias())
                .street(delivery.getStreet())
                .colony(delivery.getColony())
                .city(delivery.getCity())
                .state(delivery.getState())
                .zipCode(delivery.getZipCode())
                .country(delivery.getCountry())
                .build();
    }
}
