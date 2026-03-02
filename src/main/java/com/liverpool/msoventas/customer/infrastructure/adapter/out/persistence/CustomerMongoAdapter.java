package com.liverpool.msoventas.customer.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.liverpool.msoventas.customer.domain.model.Customer;
import com.liverpool.msoventas.customer.domain.port.out.CustomerRepositoryPort;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador de salida que implementa {@link CustomerRepositoryPort} usando MongoDB.
 *
 * <p>Traduce entre el modelo de dominio {@link Customer} y el documento
 * de persistencia {@link CustomerDocument}, manteniendo la capa de dominio
 * libre de dependencias de Spring Data.</p>
 */
@Component
@RequiredArgsConstructor
public class CustomerMongoAdapter implements CustomerRepositoryPort {

    private final SpringDataCustomerRepository repository;

    @Override
    public Customer save(Customer customer) {
        CustomerDocument document = toDocument(customer);
        CustomerDocument saved = repository.save(document);
        return toDomain(saved);
    }

    @Override
    public Optional<Customer> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }


    private CustomerDocument toDocument(Customer customer) {
        return CustomerDocument.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .motherLastName(customer.getMotherLastName())
                .email(customer.getEmail())
                .build();
    }

    private Customer toDomain(CustomerDocument document) {
        return Customer.builder()
                .id(document.getId())
                .firstName(document.getFirstName())
                .lastName(document.getLastName())
                .motherLastName(document.getMotherLastName())
                .email(document.getEmail())
                .build();
    }

}
