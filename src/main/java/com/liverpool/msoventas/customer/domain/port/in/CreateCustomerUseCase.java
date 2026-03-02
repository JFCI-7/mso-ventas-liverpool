package com.liverpool.msoventas.customer.domain.port.in;

import com.liverpool.msoventas.customer.domain.model.Customer;
import com.liverpool.msoventas.shared.domain.model.Result;

/**
 * Puerto de entrada (caso de uso) para crear un nuevo cliente.
 *
 * <p>Define el contrato que la capa de aplicacion debe cumplir para registrar
 * un cliente en el sistema. Siguiendo el Principio de Segregacion de Interfaces
 * (ISP), cada operacion tiene su propia interfaz.</p>
 */
public interface CreateCustomerUseCase {

    /**
     * Registra un nuevo cliente en el sistema.
     *
     * @param customer datos del cliente a registrar; el {@code id} puede ser nulo,
     *                 ya que la infraestructura lo genera automaticamente
     * @return {@link Result} con el cliente creado en caso de exito,
     *         o con {@link com.liverpool.msoventas.shared.domain.model.ErrorType#CONFLICT}
     *         si el email ya esta registrado
     */
    Result<Customer> create(Customer customer);
}
