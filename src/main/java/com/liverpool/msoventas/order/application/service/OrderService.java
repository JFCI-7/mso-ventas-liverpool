package com.liverpool.msoventas.order.application.service;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.order.domain.model.OrderStatus;
import com.liverpool.msoventas.order.domain.port.in.CreateOrderUseCase;
import com.liverpool.msoventas.order.domain.port.in.DeleteOrderUseCase;
import com.liverpool.msoventas.order.domain.port.in.GetOrderUseCase;
import com.liverpool.msoventas.order.domain.port.in.UpdateOrderUseCase;
import com.liverpool.msoventas.order.domain.port.out.OrderRepositoryPort;
import com.liverpool.msoventas.shared.domain.model.ErrorType;
import com.liverpool.msoventas.shared.domain.model.Result;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de aplicacion que implementa todos los casos de uso del modulo de pedidos.
 *
 * <p>No esta anotado con {@code @Service} para mantener el desacoplamiento del framework.
 * Su instancia es registrada como bean en
 * {@link com.liverpool.msoventas.order.infrastructure.config.OrderBeanConfig}
 * siguiendo el patron de Composition Root.</p>
 *
 * <p>En {@link #create(Order)} se calcula el total sumando {@code price * quantity}
 * de cada articulo, y se asigna el estado inicial
 * {@link com.liverpool.msoventas.order.domain.model.OrderStatus#PENDING}.</p>
 */
@RequiredArgsConstructor
public class OrderService implements
        CreateOrderUseCase,
        GetOrderUseCase,
        UpdateOrderUseCase,
        DeleteOrderUseCase {

    private final OrderRepositoryPort repositoryPort;

    @Override
    public Result<Order> create(Order order) {
        BigDecimal total = order.getItems().stream()
                .map(item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order toSave = Order.builder()
                .customerId(order.getCustomerId())
                .deliveryId(order.getDeliveryId())
                .items(order.getItems())
                .total(total)
                .status(OrderStatus.PENDING)
                .estimatedDeliveryDate(order.getEstimatedDeliveryDate())
                .createdAt(LocalDateTime.now())
                .build();

        Order saved = repositoryPort.save(toSave);
        return Result.success(saved);
    }

    @Override
    public Result<Order> findById(String id) {
        return repositoryPort.findById(id)
                .map(Result::success)
                .orElse(Result.failure("Order not found with id: " + id, ErrorType.NOT_FOUND));
    }

    @Override
    public Result<List<Order>> findAll() {
        return Result.success(repositoryPort.findAll());
    }

    @Override
    public Result<List<Order>> findByDisplayName(String displayName) {
        List<Order> orders = repositoryPort.findByDisplayName(displayName);
        return Result.success(orders);
    }

    @Override
    public Result<Order> updateStatus(String id, OrderStatus status) {
        return repositoryPort.findById(id)
                .map(existing -> {
                    Order updated = Order.builder()
                            .id(existing.getId())
                            .customerId(existing.getCustomerId())
                            .deliveryId(existing.getDeliveryId())
                            .items(existing.getItems())
                            .total(existing.getTotal())
                            .status(status)
                            .estimatedDeliveryDate(existing.getEstimatedDeliveryDate())
                            .createdAt(existing.getCreatedAt())
                            .build();
                    return Result.success(repositoryPort.save(updated));
                })
                .orElse(Result.failure("Order not found with id: " + id, ErrorType.NOT_FOUND));
    }

    @Override
    public Result<Void> deleteById(String id) {
        if (!repositoryPort.existsById(id)) {
            return Result.failure("Order not found with id: " + id, ErrorType.NOT_FOUND);
        }
        repositoryPort.deleteById(id);
        return Result.success(null);
    }
}
