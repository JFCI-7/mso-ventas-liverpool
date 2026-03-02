package com.liverpool.msoventas.customer.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.liverpool.msoventas.customer.domain.model.Customer;

/**
 * Puerto de salida (repositorio) para la persistencia de clientes.
 *
 * <p>Define el contrato que la infraestructura de persistencia debe implementar.
 * Al ser una interfaz en el dominio, mantiene la capa de negocio desacoplada
 * de cualquier tecnologia de base de datos.</p>
 */
public interface CustomerRepositoryPort {

    /**
     * Persiste o actualiza un cliente.
     *
     * @param customer cliente a guardar
     * @return el cliente tal como quedo almacenado (con el {@code id} generado si es nuevo)
     */
    Customer save(Customer customer);

    /**
     * Busca un cliente por su identificador.
     *
     * @param id identificador unico del cliente
     * @return {@link Optional} con el cliente si existe, o vacio si no
     */
    Optional<Customer> findById(String id);

    /**
     * Recupera todos los clientes almacenados.
     *
     * @return lista de clientes; puede estar vacia
     */
    List<Customer> findAll();

    /**
     * Verifica si existe un cliente con el identificador dado.
     *
     * @param id identificador a verificar
     * @return {@code true} si el cliente existe
     */
    boolean existsById(String id);

    /**
     * Elimina el cliente con el identificador dado.
     *
     * @param id identificador del cliente a eliminar
     */
    void deleteById(String id);

}
