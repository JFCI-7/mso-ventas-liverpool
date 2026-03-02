package com.liverpool.msoventas.order.infrastructure.adapter.in.rest;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.order.domain.model.OrderItem;
import com.liverpool.msoventas.order.domain.model.OrderStatus;
import com.liverpool.msoventas.order.domain.port.in.CreateOrderUseCase;
import com.liverpool.msoventas.order.domain.port.in.DeleteOrderUseCase;
import com.liverpool.msoventas.order.domain.port.in.GetOrderUseCase;
import com.liverpool.msoventas.order.domain.port.in.UpdateOrderUseCase;
import com.liverpool.msoventas.shared.domain.model.ErrorType;
import com.liverpool.msoventas.shared.domain.model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@DisplayName("OrderController - Tests de Integracion")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateOrderUseCase createOrderUseCase;

    @MockitoBean
    private GetOrderUseCase getOrderUseCase;

    @MockitoBean
    private UpdateOrderUseCase updateOrderUseCase;

    @MockitoBean
    private DeleteOrderUseCase deleteOrderUseCase;

    private Order order;

    @BeforeEach
    void setUp() {
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

    // ── POST ──────────────────────────────────────────────────

    @Test
    @DisplayName("POST /orders: debe retornar 201 cuando el pedido es valido")
    void create_shouldReturn201_whenRequestIsValid() throws Exception {
        when(createOrderUseCase.create(any(Order.class)))
                .thenReturn(Result.success(order));

        String body = """
                {
                  "customerId": "cust_001",
                  "deliveryId": "deliv_001",
                  "items": [
                    {
                      "productCode": "LIV-TV-55",
                      "displayName": "Television Samsung 55",
                      "quantity": 2,
                      "price": 12999.00
                    }
                  ],
                  "estimatedDeliveryDate": "2026-03-10"
                }
                """;

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("order_001"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @DisplayName("POST /orders: debe retornar 400 cuando faltan campos requeridos")
    void create_shouldReturn400_whenRequestIsInvalid() throws Exception {
        String body = """
                {
                  "customerId": "cust_001"
                }
                """;

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /orders: debe retornar 400 cuando items esta vacio")
    void create_shouldReturn400_whenItemsIsEmpty() throws Exception {
        String body = """
                {
                  "customerId": "cust_001",
                  "deliveryId": "deliv_001",
                  "items": [],
                  "estimatedDeliveryDate": "2026-03-10"
                }
                """;

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    // ── GET BY ID ─────────────────────────────────────────────

    @Test
    @DisplayName("GET /orders/{id}: debe retornar 200 cuando la orden existe")
    void findById_shouldReturn200_whenOrderExists() throws Exception {
        when(getOrderUseCase.findById("order_001"))
                .thenReturn(Result.success(order));

        mockMvc.perform(get("/api/v1/orders/order_001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("order_001"))
                .andExpect(jsonPath("$.customerId").value("cust_001"));
    }

    @Test
    @DisplayName("GET /orders/{id}: debe retornar 404 cuando la orden no existe")
    void findById_shouldReturn404_whenOrderDoesNotExist() throws Exception {
        when(getOrderUseCase.findById("notexist"))
                .thenReturn(Result.failure("Not found", ErrorType.NOT_FOUND));

        mockMvc.perform(get("/api/v1/orders/notexist"))
                .andExpect(status().isNotFound());
    }

    // ── GET ALL / SEARCH ──────────────────────────────────────

    @Test
    @DisplayName("GET /orders: debe retornar 200 con lista de ordenes")
    void findAll_shouldReturn200_withList() throws Exception {
        when(getOrderUseCase.findAll())
                .thenReturn(Result.success(List.of(order)));

        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("order_001"));
    }

    @Test
    @DisplayName("GET /orders?displayName=: debe retornar 200 con ordenes filtradas")
    void findByDisplayName_shouldReturn200_withFilteredList() throws Exception {
        when(getOrderUseCase.findByDisplayName("television"))
                .thenReturn(Result.success(List.of(order)));

        mockMvc.perform(get("/api/v1/orders").param("displayName", "television"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].items[0].displayName")
                        .value("Television Samsung 55"));
    }

    // ── PATCH STATUS ──────────────────────────────────────────

    @Test
    @DisplayName("PATCH /orders/{id}/status: debe retornar 200 cuando la actualizacion es exitosa")
    void updateStatus_shouldReturn200_whenOrderExists() throws Exception {
        when(updateOrderUseCase.updateStatus(anyString(), any(OrderStatus.class)))
                .thenReturn(Result.success(order));

        String body = """
                {
                  "status": "CONFIRMED"
                }
                """;

        mockMvc.perform(patch("/api/v1/orders/order_001/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("order_001"));
    }

    @Test
    @DisplayName("PATCH /orders/{id}/status: debe retornar 404 cuando la orden no existe")
    void updateStatus_shouldReturn404_whenOrderDoesNotExist() throws Exception {
        when(updateOrderUseCase.updateStatus(anyString(), any(OrderStatus.class)))
                .thenReturn(Result.failure("Not found", ErrorType.NOT_FOUND));

        String body = """
                {
                  "status": "CONFIRMED"
                }
                """;

        mockMvc.perform(patch("/api/v1/orders/notexist/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());
    }

    // ── DELETE ────────────────────────────────────────────────

    @Test
    @DisplayName("DELETE /orders/{id}: debe retornar 204 cuando la eliminacion es exitosa")
    void deleteById_shouldReturn204_whenOrderExists() throws Exception {
        when(deleteOrderUseCase.deleteById("order_001"))
                .thenReturn(Result.success(null));

        mockMvc.perform(delete("/api/v1/orders/order_001"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /orders/{id}: debe retornar 404 cuando la orden no existe")
    void deleteById_shouldReturn404_whenOrderDoesNotExist() throws Exception {
        when(deleteOrderUseCase.deleteById("notexist"))
                .thenReturn(Result.failure("Not found", ErrorType.NOT_FOUND));

        mockMvc.perform(delete("/api/v1/orders/notexist"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("POST /orders: debe retornar 409 cuando hay conflicto")
    void create_shouldReturn409_whenConflict() throws Exception {
        when(createOrderUseCase.create(any(Order.class)))
                .thenReturn(Result.failure("Conflict", ErrorType.CONFLICT));

        String body = """
                {
                  "customerId": "cust_001",
                  "deliveryId": "deliv_001",
                  "items": [
                    {
                      "productCode": "LIV-TV-55",
                      "displayName": "Television Samsung 55",
                      "quantity": 2,
                      "price": 12999.00
                    }
                  ],
                  "estimatedDeliveryDate": "2026-03-10"
                }
                """;

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("POST /orders: debe retornar 500 cuando hay error interno")
    void create_shouldReturn500_whenInternalError() throws Exception {
        when(createOrderUseCase.create(any(Order.class)))
                .thenReturn(Result.failure("Error", ErrorType.INTERNAL_ERROR));

        String body = """
                {
                  "customerId": "cust_001",
                  "deliveryId": "deliv_001",
                  "items": [
                    {
                      "productCode": "LIV-TV-55",
                      "displayName": "Television Samsung 55",
                      "quantity": 2,
                      "price": 12999.00
                    }
                  ],
                  "estimatedDeliveryDate": "2026-03-10"
                }
                """;

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("GET /orders?displayName=: debe retornar findAll cuando displayName es blank")
    void findOrders_shouldCallFindAll_whenDisplayNameIsBlank() throws Exception {
        when(getOrderUseCase.findAll())
                .thenReturn(Result.success(List.of(order)));

        mockMvc.perform(get("/api/v1/orders").param("displayName", "  "))
                .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("GET /orders/{id}: debe retornar 400 cuando hay error de validacion")
    void findById_shouldReturn400_whenValidationError() throws Exception {
        when(getOrderUseCase.findById("order_001"))
                .thenReturn(Result.failure("Validation error", ErrorType.VALIDATION_ERROR));

        mockMvc.perform(get("/api/v1/orders/order_001"))
                .andExpect(status().isBadRequest());
    }
}
