package com.liverpool.msoventas.order.infrastructure.adapter.out.persistence;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.order.domain.model.OrderItem;
import com.liverpool.msoventas.order.domain.model.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OrderMongoAdapter - Tests Unitarios")
class OrderMongoAdapterTest {

    @Mock
    private SpringDataOrderRepository repository;

    @InjectMocks
    private OrderMongoAdapter adapter;

    private OrderDocument document;
    private Order order;

    @BeforeEach
    void setUp() {
        OrderItemDocument itemDoc = OrderItemDocument.builder()
                .productCode("LIV-TV-55")
                .displayName("Television Samsung 55")
                .displayNameNormalized("television samsung 55")
                .quantity(2)
                .price(new BigDecimal("12999.00"))
                .build();

        document = OrderDocument.builder()
                .id("order_001")
                .customerId("cust_001")
                .deliveryId("deliv_001")
                .items(List.of(itemDoc))
                .total(new BigDecimal("25998.00"))
                .status("PENDING")
                .estimatedDeliveryDate(LocalDate.of(2026, 3, 10))
                .createdAt(LocalDateTime.now())
                .build();

        OrderItem item = OrderItem.builder()
                .productCode("LIV-TV-55")
                .displayName("Television Samsung 55")
                .quantity(2)
                .price(new BigDecimal("12999.00"))
                .build();

        order = Order.builder()
                .id("order_001")
                .customerId("cust_001")
                .deliveryId("deliv_001")
                .items(List.of(item))
                .total(new BigDecimal("25998.00"))
                .status(OrderStatus.PENDING)
                .estimatedDeliveryDate(LocalDate.of(2026, 3, 10))
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("save: debe guardar y retornar el dominio correctamente")
    void save_shouldReturnDomain_whenDocumentIsSaved() {
        when(repository.save(any(OrderDocument.class))).thenReturn(document);

        Order result = adapter.save(order);

        assertThat(result.getId()).isEqualTo("order_001");
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);
        verify(repository, times(1)).save(any(OrderDocument.class));
    }

    @Test
    @DisplayName("save: debe normalizar el displayName al guardar")
    void save_shouldNormalizeDisplayName_whenSaving() {
        when(repository.save(any(OrderDocument.class))).thenReturn(document);

        OrderItem itemWithAccents = OrderItem.builder()
                .productCode("LIV-TV-55")
                .displayName("Televisión, Samsung")
                .quantity(1)
                .price(new BigDecimal("12999.00"))
                .build();

        Order orderWithAccents = Order.builder()
                .customerId("cust_001")
                .deliveryId("deliv_001")
                .items(List.of(itemWithAccents))
                .status(OrderStatus.PENDING)
                .build();

        adapter.save(orderWithAccents);

        verify(repository).save(argThat(doc ->
                doc.getItems().get(0).getDisplayNameNormalized().equals("television samsung")
        ));
    }

    @Test
    @DisplayName("findById: debe retornar Optional con dominio cuando existe")
    void findById_shouldReturnDomain_whenDocumentExists() {
        when(repository.findById("order_001")).thenReturn(Optional.of(document));

        Optional<Order> result = adapter.findById("order_001");

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo("order_001");
    }

    @Test
    @DisplayName("findById: debe retornar Optional vacio cuando no existe")
    void findById_shouldReturnEmpty_whenDocumentDoesNotExist() {
        when(repository.findById("notexist")).thenReturn(Optional.empty());

        Optional<Order> result = adapter.findById("notexist");

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findByDisplayName: debe normalizar el termino de busqueda")
    void findByDisplayName_shouldNormalizeSearchTerm() {
        when(repository.findByItemsDisplayNameNormalized(anyString()))
                .thenReturn(List.of(document));

        List<Order> result = adapter.findByDisplayName("Televisión,");

        assertThat(result).hasSize(1);
        verify(repository).findByItemsDisplayNameNormalized("television");
    }

    @Test
    @DisplayName("existsById: debe retornar true cuando existe")
    void existsById_shouldReturnTrue_whenExists() {
        when(repository.existsById("order_001")).thenReturn(true);

        assertThat(adapter.existsById("order_001")).isTrue();
    }

    @Test
    @DisplayName("deleteById: debe llamar al repositorio correctamente")
    void deleteById_shouldCallRepository() {
        adapter.deleteById("order_001");

        verify(repository, times(1)).deleteById("order_001");
    }
    
    @Test
    @DisplayName("findByDisplayName: debe manejar displayName null correctamente")
    void findByDisplayName_shouldHandleNullInput() {
        when(repository.findByItemsDisplayNameNormalized(""))
                .thenReturn(List.of());

        List<Order> result = adapter.findByDisplayName(null);

        assertThat(result).isEmpty();
        verify(repository).findByItemsDisplayNameNormalized("");
    }

    @Test
    @DisplayName("findById: debe retornar dominio con status null cuando documento tiene status null")
    void findById_shouldHandleNullStatus_whenDocumentHasNullStatus() {
        OrderDocument docWithNullStatus = OrderDocument.builder()
                .id("order_001")
                .customerId("cust_001")
                .deliveryId("deliv_001")
                .items(List.of())
                .total(new BigDecimal("0.00"))
                .status(null)
                .estimatedDeliveryDate(LocalDate.of(2026, 3, 10))
                .createdAt(LocalDateTime.now())
                .build();

        when(repository.findById("order_001")).thenReturn(Optional.of(docWithNullStatus));

        Optional<Order> result = adapter.findById("order_001");

        assertThat(result).isPresent();
        assertThat(result.get().getStatus()).isNull();
    }

    @Test
    @DisplayName("save: debe manejar status null en la orden correctamente")
    void save_shouldHandleNullStatus_whenOrderHasNullStatus() {
        OrderDocument docWithNullStatus = OrderDocument.builder()
                .id("order_001")
                .customerId("cust_001")
                .deliveryId("deliv_001")
                .items(List.of())
                .total(new BigDecimal("0.00"))
                .status(null)
                .build();

        when(repository.save(any(OrderDocument.class))).thenReturn(docWithNullStatus);

        Order orderWithNullStatus = Order.builder()
                .customerId("cust_001")
                .deliveryId("deliv_001")
                .items(List.of())
                .status(null)
                .build();

        Order result = adapter.save(orderWithNullStatus);

        assertThat(result.getStatus()).isNull();
        verify(repository).save(argThat(doc -> doc.getStatus() == null));
    }
}
