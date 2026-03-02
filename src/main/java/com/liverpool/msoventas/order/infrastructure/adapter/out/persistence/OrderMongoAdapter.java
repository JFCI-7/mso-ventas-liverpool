package com.liverpool.msoventas.order.infrastructure.adapter.out.persistence;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.order.domain.model.OrderItem;
import com.liverpool.msoventas.order.domain.model.OrderStatus;
import com.liverpool.msoventas.order.domain.port.out.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

/**
 * Adaptador de salida que implementa {@link OrderRepositoryPort} usando MongoDB.
 *
 * <p>Traduce entre el modelo de dominio {@link Order} y el documento de persistencia
 * {@link OrderDocument}. Incluye la logica de normalizacion de texto para habilitar
 * la busqueda flexible por nombre de articulo: convierte a minusculas, elimina acentos
 * mediante NFD y remueve caracteres de puntuacion.</p>
 */
@Component
@RequiredArgsConstructor
public class OrderMongoAdapter implements OrderRepositoryPort {

    private final SpringDataOrderRepository repository;

    @Override
    public Order save(Order order) {
        OrderDocument doc = toDocument(order);
        OrderDocument saved = repository.save(doc);
        return toDomain(saved);
    }

    @Override
    public Optional<Order> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Order> findByDisplayName(String displayName) {
        String normalized = normalize(displayName);
        return repository.findByItemsDisplayNameNormalized(normalized)
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

    // ── Normalización ─────────────────────────────────────────

    private String normalize(String input) {
        if (input == null) {
            return "";
        }
        String lower = input.toLowerCase();
        String noAccents = Normalizer.normalize(lower, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return noAccents.replaceAll("[,;.!?¿¡\"']", "").trim();
    }

    // ── Conversiones ──────────────────────────────────────────

    private OrderDocument toDocument(Order order) {
        List<OrderItemDocument> itemDocs = order.getItems().stream()
                .map(item -> OrderItemDocument.builder()
                        .productCode(item.getProductCode())
                        .displayName(item.getDisplayName())
                        .displayNameNormalized(normalize(item.getDisplayName()))
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .toList();

        return OrderDocument.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .deliveryId(order.getDeliveryId())
                .items(itemDocs)
                .total(order.getTotal())
                .status(order.getStatus() != null ? order.getStatus().name() : null)
                .estimatedDeliveryDate(order.getEstimatedDeliveryDate())
                .createdAt(order.getCreatedAt())
                .build();
    }

    private Order toDomain(OrderDocument doc) {
        List<OrderItem> items = doc.getItems().stream()
                .map(item -> OrderItem.builder()
                        .productCode(item.getProductCode())
                        .displayName(item.getDisplayName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .toList();

        return Order.builder()
                .id(doc.getId())
                .customerId(doc.getCustomerId())
                .deliveryId(doc.getDeliveryId())
                .items(items)
                .total(doc.getTotal())
                .status(doc.getStatus() != null ? OrderStatus.valueOf(doc.getStatus()) : null)
                .estimatedDeliveryDate(doc.getEstimatedDeliveryDate())
                .createdAt(doc.getCreatedAt())
                .build();
    }
}
