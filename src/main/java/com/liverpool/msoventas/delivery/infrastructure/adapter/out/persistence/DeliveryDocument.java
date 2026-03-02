package com.liverpool.msoventas.delivery.infrastructure.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Documento MongoDB que representa una direccion de entrega en la coleccion {@code deliveries}.
 *
 * <p>El campo {@code customerId} esta indexado para agilizar las consultas
 * por cliente sin necesidad de un scan completo de la coleccion.</p>
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "deliveries")
public class DeliveryDocument {

    @Id
    private String id;

    @Indexed
    private String customerId;

    private String alias;
    private String street;
    private String colony;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}

