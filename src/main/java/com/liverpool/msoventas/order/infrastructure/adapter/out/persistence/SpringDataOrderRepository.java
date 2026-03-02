package com.liverpool.msoventas.order.infrastructure.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Repositorio Spring Data MongoDB para la coleccion {@code orders}.
 *
 * <p>Hereda las operaciones CRUD de {@link MongoRepository} y agrega
 * una consulta personalizada con {@code $regex} para implementar la
 * busqueda flexible por nombre de articulo normalizado.</p>
 */
public interface SpringDataOrderRepository
        extends MongoRepository<OrderDocument, String> {

    /**
     * Busca pedidos cuyos articulos tengan un nombre normalizado que coincida
     * con la expresion regular proporcionada (insensible a mayusculas).
     *
     * @param displayNameNormalized termino de busqueda ya normalizado
     * @return lista de documentos coincidentes
     */
    @Query("{ 'items.displayNameNormalized': { $regex: ?0, $options: 'i' } }")
    List<OrderDocument> findByItemsDisplayNameNormalized(String displayNameNormalized);
}
