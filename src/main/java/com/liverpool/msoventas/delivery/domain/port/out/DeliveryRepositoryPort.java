package com.liverpool.msoventas.delivery.domain.port.out;

import com.liverpool.msoventas.delivery.domain.model.Delivery;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida (repositorio) para la persistencia de direcciones de entrega.
 *
 * <p>Define el contrato que la infraestructura de persistencia debe implementar,
 * manteniendo el dominio desacoplado de cualquier tecnologia de base de datos.</p>
 */
public interface DeliveryRepositoryPort {

    /**
     * Persiste o actualiza una direccion de entrega.
     *
     * @param delivery direccion a guardar
     * @return la direccion tal como quedo almacenada (con {@code id} generado si es nueva)
     */
    Delivery save(Delivery delivery);

    /**
     * Busca una direccion de entrega por su identificador.
     *
     * @param id identificador unico de la direccion
     * @return {@link Optional} con la direccion si existe, o vacio si no
     */
    Optional<Delivery> findById(String id);

    /**
     * Recupera todas las direcciones asociadas al cliente dado.
     *
     * @param customerId identificador del cliente
     * @return lista de direcciones; puede estar vacia
     */
    List<Delivery> findByCustomerId(String customerId);

    /**
     * Verifica si existe una direccion con el identificador dado.
     *
     * @param id identificador a verificar
     * @return {@code true} si la direccion existe
     */
    boolean existsById(String id);

    /**
     * Elimina la direccion con el identificador dado.
     *
     * @param id identificador de la direccion a eliminar
     */
    void deleteById(String id);
}
