package com.liverpool.msoventas.customer.infrastructure.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento MongoDB que representa a un cliente en la coleccion {@code customers}.
 *
 * <p>Es el objeto de persistencia de la capa de infraestructura; no debe
 * utilizarse fuera del paquete de adaptadores de salida. El campo {@code email}
 * tiene un indice unico para evitar registros duplicados.</p>
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customers")
public class CustomerDocument {
	
	@Id
    private String id;

    private String firstName;
    private String lastName;
    private String motherLastName;

    @Indexed(unique = true)
    private String email;

}
