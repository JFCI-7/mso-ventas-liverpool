package com.liverpool.msoventas.delivery.application.service;

import com.liverpool.msoventas.delivery.domain.model.Delivery;
import com.liverpool.msoventas.delivery.domain.port.in.CreateDeliveryUseCase;
import com.liverpool.msoventas.delivery.domain.port.in.DeleteDeliveryUseCase;
import com.liverpool.msoventas.delivery.domain.port.in.GetDeliveryUseCase;
import com.liverpool.msoventas.delivery.domain.port.in.UpdateDeliveryUseCase;
import com.liverpool.msoventas.delivery.domain.port.out.DeliveryRepositoryPort;
import com.liverpool.msoventas.shared.domain.model.ErrorType;
import com.liverpool.msoventas.shared.domain.model.Result;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DeliveryService implements
        CreateDeliveryUseCase,
        GetDeliveryUseCase,
        UpdateDeliveryUseCase,
        DeleteDeliveryUseCase {

    private final DeliveryRepositoryPort repositoryPort;

    @Override
    public Result<Delivery> create(Delivery delivery) {
        Delivery saved = repositoryPort.save(delivery);
        return Result.success(saved);
    }

    @Override
    public Result<Delivery> findById(String id) {
        return repositoryPort.findById(id)
                .map(Result::success)
                .orElse(Result.failure("Delivery not found with id: " + id, ErrorType.NOT_FOUND));
    }

    @Override
    public Result<List<Delivery>> findByCustomerId(String customerId) {
        List<Delivery> deliveries = repositoryPort.findByCustomerId(customerId);
        return Result.success(deliveries);
    }

    @Override
    public Result<Delivery> update(String id, Delivery delivery) {
        if (!repositoryPort.existsById(id)) {
            return Result.failure("Delivery not found with id: " + id, ErrorType.NOT_FOUND);
        }
        Delivery updated = repositoryPort.save(
                Delivery.builder()
                        .id(id)
                        .customerId(delivery.getCustomerId())
                        .alias(delivery.getAlias())
                        .street(delivery.getStreet())
                        .colony(delivery.getColony())
                        .city(delivery.getCity())
                        .state(delivery.getState())
                        .zipCode(delivery.getZipCode())
                        .country(delivery.getCountry())
                        .build()
        );
        return Result.success(updated);
    }

    @Override
    public Result<Void> deleteById(String id) {
        if (!repositoryPort.existsById(id)) {
            return Result.failure("Delivery not found with id: " + id, ErrorType.NOT_FOUND);
        }
        repositoryPort.deleteById(id);
        return Result.success(null);
    }
}
