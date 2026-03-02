package com.liverpool.msoventas.order.domain.port.in;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.shared.domain.model.Result;

import java.util.List;

/**
 * Puerto de entrada (caso de uso) para consultar pedidos.
 *
 * <p>Incluye busqueda flexible por nombre de articulo, que normaliza
 * acentos y caracteres especiales antes de realizar la consulta.</p>
 */
public interface GetOrderUseCase {

    /**
     * Busca un pedido por su identificador unico.
     *
     * @param id identificador del pedido
     * @return {@link Result} con el pedido, o con
     *         {@link com.liverpool.msoventas.shared.domain.model.ErrorType#NOT_FOUND}
     *         si no existe
     */
    Result<Order> findById(String id);

    /**
     * Recupera todos los pedidos registrados.
     *
     * @return {@link Result} con la lista de pedidos; puede estar vacia
     */
    Result<List<Order>> findAll();

    /**
     * Busca pedidos cuyos articulos contengan el nombre indicado.
     *
     * <p>La busqueda es insensible a mayusculas, acentos y caracteres especiales.</p>
     *
     * @param displayName termino de busqueda parcial o completo
     * @return {@link Result} con la lista de pedidos coincidentes; puede estar vacia
     */
    Result<List<Order>> findByDisplayName(String displayName);
}
