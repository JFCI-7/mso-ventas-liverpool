package com.liverpool.msoventas.customer.domain.port.in;

import java.util.List;

import com.liverpool.msoventas.customer.domain.model.Customer;
import com.liverpool.msoventas.shared.domain.model.Result;

/**
 * Puerto de entrada (caso de uso) para consultar clientes.
 *
 * <p>Expone las operaciones de lectura disponibles sobre la entidad
 * {@link com.liverpool.msoventas.customer.domain.model.Customer}.</p>
 */
public interface GetCustomerUseCase {

    /**
     * Busca un cliente por su identificador unico.
     *
     * @param id identificador del cliente
     * @return {@link Result} con el cliente encontrado, o con
     *         {@link com.liverpool.msoventas.shared.domain.model.ErrorType#NOT_FOUND}
     *         si no existe
     */
    Result<Customer> findById(String id);

    /**
     * Recupera todos los clientes registrados en el sistema.
     *
     * @return {@link Result} con la lista de clientes; la lista puede estar vacia
     */
    Result<List<Customer>> findAll();

}
