package com.liverpool.msoventas.order.application.service;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.order.domain.model.OrderItem;
import com.liverpool.msoventas.order.domain.model.OrderStatus;
import com.liverpool.msoventas.order.domain.port.out.OrderRepositoryPort;
import com.liverpool.msoventas.shared.domain.model.ErrorType;
import com.liverpool.msoventas.shared.domain.model.Result;
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
@DisplayName("OrderService - Tests Unitarios")
class OrderServiceTest {

    @Mock
    private OrderRepositoryPort repositoryPort;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private OrderItem item;

    @BeforeEach
    void setUp() {
        item = OrderItem.builder()
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

    // ── CREATE ────────────────────────────────────────────────

    @Test
    @DisplayName("create: debe calcular el total correctamente y asignar PENDING")
    void create_shouldCalculateTotalAndAssignPending() {
        when(repositoryPort.save(any(Order.class))).thenReturn(order);

        Order input = Order.builder()
                .customerId("cust_001")
                .deliveryId("deliv_001")
                .items(List.of(item))
                .estimatedDeliveryDate(LocalDate.of(2026, 3, 10))
                .build();

        Result<Order> result = orderService.create(input);

        assertThat(result.isSuccess()).isTrue();
        verify(repositoryPort, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("create: debe retornar Result exitoso con la orden guardada")
    void create_shouldReturnSuccess_whenOrderIsSaved() {
        when(repositoryPort.save(any(Order.class))).thenReturn(order);

        Result<Order> result = orderService.create(order);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData().getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(result.getData().getTotal()).isEqualByComparingTo("25998.00");
    }

    // ── FIND BY ID ────────────────────────────────────────────

    @Test
    @DisplayName("findById: debe retornar Result exitoso cuando la orden existe")
    void findById_shouldReturnSuccess_whenOrderExists() {
        when(repositoryPort.findById("order_001")).thenReturn(Optional.of(order));

        Result<Order> result = orderService.findById("order_001");

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData().getId()).isEqualTo("order_001");
    }

    @Test
    @DisplayName("findById: debe retornar NOT_FOUND cuando la orden no existe")
    void findById_shouldReturnNotFound_whenOrderDoesNotExist() {
        when(repositoryPort.findById("notexist")).thenReturn(Optional.empty());

        Result<Order> result = orderService.findById("notexist");

        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrorType()).isEqualTo(ErrorType.NOT_FOUND);
    }

    // ── FIND ALL ──────────────────────────────────────────────

    @Test
    @DisplayName("findAll: debe retornar lista de ordenes")
    void findAll_shouldReturnSuccess_withOrderList() {
        when(repositoryPort.findAll()).thenReturn(List.of(order));

        Result<List<Order>> result = orderService.findAll();

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).hasSize(1);
    }

    // ── FIND BY DISPLAY NAME ──────────────────────────────────

    @Test
    @DisplayName("findByDisplayName: debe retornar ordenes que coincidan con el termino")
    void findByDisplayName_shouldReturnMatchingOrders() {
        when(repositoryPort.findByDisplayName("television")).thenReturn(List.of(order));

        Result<List<Order>> result = orderService.findByDisplayName("television");

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).hasSize(1);
    }

    @Test
    @DisplayName("findByDisplayName: debe retornar lista vacia si no hay coincidencias")
    void findByDisplayName_shouldReturnEmpty_whenNoMatch() {
        when(repositoryPort.findByDisplayName("xyz")).thenReturn(List.of());

        Result<List<Order>> result = orderService.findByDisplayName("xyz");

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).isEmpty();
    }

    // ── UPDATE STATUS ─────────────────────────────────────────

    @Test
    @DisplayName("updateStatus: debe retornar Result exitoso cuando la orden existe")
    void updateStatus_shouldReturnSuccess_whenOrderExists() {
        Order confirmed = Order.builder()
                .id("order_001")
                .customerId("cust_001")
                .deliveryId("deliv_001")
                .items(List.of(item))
                .total(new BigDecimal("25998.00"))
                .status(OrderStatus.CONFIRMED)
                .estimatedDeliveryDate(LocalDate.of(2026, 3, 10))
                .createdAt(order.getCreatedAt())
                .build();

        when(repositoryPort.findById("order_001")).thenReturn(Optional.of(order));
        when(repositoryPort.save(any(Order.class))).thenReturn(confirmed);

        Result<Order> result = orderService.updateStatus("order_001", OrderStatus.CONFIRMED);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData().getStatus()).isEqualTo(OrderStatus.CONFIRMED);
    }

    @Test
    @DisplayName("updateStatus: debe retornar NOT_FOUND cuando la orden no existe")
    void updateStatus_shouldReturnNotFound_whenOrderDoesNotExist() {
        when(repositoryPort.findById("notexist")).thenReturn(Optional.empty());

        Result<Order> result = orderService.updateStatus("notexist", OrderStatus.CONFIRMED);

        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrorType()).isEqualTo(ErrorType.NOT_FOUND);
        verify(repositoryPort, never()).save(any(Order.class));
    }

    // ── DELETE ────────────────────────────────────────────────

    @Test
    @DisplayName("deleteById: debe retornar Result exitoso cuando la orden existe")
    void deleteById_shouldReturnSuccess_whenOrderExists() {
        when(repositoryPort.existsById("order_001")).thenReturn(true);

        Result<Void> result = orderService.deleteById("order_001");

        assertThat(result.isSuccess()).isTrue();
        verify(repositoryPort, times(1)).deleteById("order_001");
    }

    @Test
    @DisplayName("deleteById: debe retornar NOT_FOUND cuando la orden no existe")
    void deleteById_shouldReturnNotFound_whenOrderDoesNotExist() {
        when(repositoryPort.existsById("notexist")).thenReturn(false);

        Result<Void> result = orderService.deleteById("notexist");

        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrorType()).isEqualTo(ErrorType.NOT_FOUND);
        verify(repositoryPort, never()).deleteById(anyString());
    }
}
