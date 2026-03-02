package com.liverpool.msoventas.order.domain.port.out;

import com.liverpool.msoventas.order.domain.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida (repositorio) para la persistencia de pedidos.
 *
 * <p>Define el contrato que la infraestructura de persistencia debe implementar.
 * La interfaz vive en el dominio para garantizar la independencia tecnologica
 * de la logica de negocio.</p>
 */
public interface OrderRepositoryPort {

    /**
     * Persiste o actualiza un pedido.
     *
     * @param order pedido a guardar
     * @return el pedido con el {@code id} generado por la infraestructura
     */
    Order save(Order order);

    /**
     * Busca un pedido por su identificador.
     *
     * @param id identificador unico del pedido
     * @return {@link Optional} con el pedido si existe, o vacio si no
     */
    Optional<Order> findById(String id);

    /**
     * Recupera todos los pedidos almacenados.
     *
     * @return lista de pedidos; puede estar vacia
     */
    List<Order> findAll();

    /**
     * Busca pedidos cuyos articulos contengan el nombre normalizado indicado.
     *
     * @param displayName termino de busqueda normalizado (sin acentos, minusculas)
     * @return lista de pedidos coincidentes; puede estar vacia
     */
    List<Order> findByDisplayName(String displayName);

    /**
     * Verifica si existe un pedido con el identificador dado.
     *
     * @param id identificador a verificar
     * @return {@code true} si el pedido existe
     */
    boolean existsById(String id);

    /**
     * Elimina el pedido con el identificador dado.
     *
     * @param id identificador del pedido a eliminar
     */
    void deleteById(String id);
}
