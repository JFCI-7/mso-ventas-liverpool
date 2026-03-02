package com.liverpool.msoventas.customer.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entidad de dominio que representa a un cliente del sistema.
 *
 * <p>Contiene los datos de identificacion y contacto del cliente.
 * No depende de ningun framework de infraestructura; es un POJO puro
 * gestionado unicamente por la logica de negocio.</p>
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
	private String id;
	private String firstName;
	private String lastName;
	private String motherLastName;
	private String email;
	

}
