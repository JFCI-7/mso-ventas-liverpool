package com.liverpool.msoventas.delivery.infrastructure.adapter.out.persistence;

import com.liverpool.msoventas.delivery.domain.model.Delivery;
import com.liverpool.msoventas.delivery.domain.port.out.DeliveryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de salida que implementa {@link DeliveryRepositoryPort} usando MongoDB.
 *
 * <p>Traduce entre el modelo de dominio {@link Delivery} y el documento
 * de persistencia {@link DeliveryDocument}, aislando la capa de dominio
 * de la tecnologia de base de datos.</p>
 */
@Component
@RequiredArgsConstructor
public class DeliveryMongoAdapter implements DeliveryRepositoryPort {

    private final SpringDataDeliveryRepository repository;

    @Override
    public Delivery save(Delivery delivery) {
        DeliveryDocument doc = toDocument(delivery);
        DeliveryDocument saved = repository.save(doc);
        return toDomain(saved);
    }

    @Override
    public Optional<Delivery> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Delivery> findByCustomerId(String customerId) {
        return repository.findByCustomerId(customerId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }


    private DeliveryDocument toDocument(Delivery delivery) {
        return DeliveryDocument.builder()
                .id(delivery.getId())
                .customerId(delivery.getCustomerId())
                .alias(delivery.getAlias())
                .street(delivery.getStreet())
                .colony(delivery.getColony())
                .city(delivery.getCity())
                .state(delivery.getState())
                .zipCode(delivery.getZipCode())
                .country(delivery.getCountry())
                .build();
    }

    private Delivery toDomain(DeliveryDocument doc) {
        return Delivery.builder()
                .id(doc.getId())
                .customerId(doc.getCustomerId())
                .alias(doc.getAlias())
                .street(doc.getStreet())
                .colony(doc.getColony())
                .city(doc.getCity())
                .state(doc.getState())
                .zipCode(doc.getZipCode())
                .country(doc.getCountry())
                .build();
    }
}
