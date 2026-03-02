package com.liverpool.msoventas.delivery.infrastructure.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repositorio Spring Data MongoDB para la coleccion {@code deliveries}.
 *
 * <p>Extiende {@link MongoRepository} para heredar las operaciones CRUD basicas.
 * Los metodos adicionales son resueltos automaticamente por Spring Data mediante
 * las convenciones de nomenclatura.</p>
 */
public interface SpringDataDeliveryRepository
        extends MongoRepository<DeliveryDocument, String> {

    /**
     * Recupera todas las direcciones de entrega del cliente dado.
     *
     * @param customerId identificador del cliente
     * @return lista de documentos correspondientes al cliente
     */
    List<DeliveryDocument> findByCustomerId(String customerId);
}
