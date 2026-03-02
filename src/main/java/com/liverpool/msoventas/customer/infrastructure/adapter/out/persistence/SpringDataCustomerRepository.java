package com.liverpool.msoventas.customer.infrastructure.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repositorio Spring Data MongoDB para la coleccion {@code customers}.
 *
 * <p>Extiende {@link MongoRepository} para obtener las operaciones CRUD basicas.
 * Los metodos adicionales son generados automaticamente por Spring Data
 * a partir de las convenciones de nomenclatura.</p>
 */
public interface SpringDataCustomerRepository extends MongoRepository<CustomerDocument, String> {

    /**
     * Busca un cliente por su correo electronico.
     *
     * @param email correo electronico a buscar
     * @return {@link Optional} con el documento si existe
     */
    Optional<CustomerDocument> findByEmail(String email);

    /**
     * Verifica si existe un cliente con el correo electronico dado.
     *
     * @param email correo electronico a verificar
     * @return {@code true} si el correo ya esta registrado
     */
    boolean existsByEmail(String email);

}
