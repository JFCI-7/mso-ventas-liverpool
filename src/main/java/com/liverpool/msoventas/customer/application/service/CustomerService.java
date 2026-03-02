package com.liverpool.msoventas.customer.application.service;

import java.util.List;
import java.util.Optional;

import com.liverpool.msoventas.customer.domain.port.in.CreateCustomerUseCase;
import com.liverpool.msoventas.customer.domain.port.in.DeleteCustomerUseCase;
import com.liverpool.msoventas.customer.domain.port.in.GetCustomerUseCase;
import com.liverpool.msoventas.customer.domain.port.in.UpdateCustomerUseCase;
import com.liverpool.msoventas.customer.domain.port.out.CustomerRepositoryPort;
import com.liverpool.msoventas.shared.domain.model.ErrorType;
import com.liverpool.msoventas.shared.domain.model.Result;
import com.liverpool.msoventas.customer.domain.model.Customer;

import lombok.RequiredArgsConstructor;

/**
 * Servicio de aplicacion que implementa todos los casos de uso relacionados con clientes.
 *
 * <p>No esta anotado con {@code @Service} de Spring para mantener el desacoplamiento
 * de la capa de dominio/aplicacion respecto al framework. En su lugar, la instancia
 * es registrada como bean en {@link com.liverpool.msoventas.customer.infrastructure.config.CustomerBeanConfig},
 * siguiendo el patron de Composition Root.</p>
 */
@RequiredArgsConstructor
public class CustomerService implements  CreateCustomerUseCase, GetCustomerUseCase,
UpdateCustomerUseCase, DeleteCustomerUseCase {

    private final CustomerRepositoryPort repositoryPort;

    @Override
    public Result<Customer> create(Customer customer) {
        if (repositoryPort.existsByEmail(customer.getEmail())) {
            return Result.failure(
                "El email ya esta registrado: " + customer.getEmail(), ErrorType.CONFLICT);
        }
        Customer saved = repositoryPort.save(customer);
        return Result.success(saved);
    }

    @Override
    public Result<Customer> findById(String id) {
        return repositoryPort.findById(id)
                .map(Result::success)
                .orElse(Result.failure("Cliente no encontrado con id: " + id, ErrorType.NOT_FOUND));
    }

    @Override
    public Result<List<Customer>> findAll() {
        List<Customer> customers = repositoryPort.findAll();
        return Result.success(customers);
    }

    @Override
    public Result<Customer> update(String id, Customer customer) {
        Optional<Customer> existing = repositoryPort.findById(id);
        if (existing.isEmpty()) {
            return Result.failure("Cliente no encontrado con id: " + id, ErrorType.NOT_FOUND);
        }
        boolean emailChanged = !existing.get().getEmail().equals(customer.getEmail());
        if (emailChanged && repositoryPort.existsByEmail(customer.getEmail())) {
            return Result.failure(
                "El email ya esta registrado: " + customer.getEmail(), ErrorType.CONFLICT);
        }
        Customer updated = Customer.builder()
                .id(id)
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .motherLastName(customer.getMotherLastName())
                .email(customer.getEmail())
                .build();
        return Result.success(repositoryPort.save(updated));
    }

    @Override
    public Result<Void> deleteById(String id) {
        if (!repositoryPort.existsById(id)) {
            return Result.failure("Cliente no encontrado con id: " + id, ErrorType.NOT_FOUND);
        }
        repositoryPort.deleteById(id);
        return Result.success(null);
    }

}
