package com.liverpool.msoventas.delivery.application.service;

import com.liverpool.msoventas.delivery.domain.model.Delivery;
import com.liverpool.msoventas.delivery.domain.port.out.DeliveryRepositoryPort;
import com.liverpool.msoventas.shared.domain.model.ErrorType;
import com.liverpool.msoventas.shared.domain.model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeliveryService - Tests Unitarios")
class DeliveryServiceTest {

    @Mock
    private DeliveryRepositoryPort repositoryPort;

    @InjectMocks
    private DeliveryService deliveryService;

    private Delivery delivery;

    @BeforeEach
    void setUp() {
        delivery = Delivery.builder()
                .id("deliv_001")
                .customerId("cust_001")
                .alias("Casa")
                .street("Av. Insurgentes Sur 1234")
                .colony("Del Valle")
                .city("Ciudad de Mexico")
                .state("CDMX")
                .zipCode("03100")
                .country("Mexico")
                .build();
    }

    // ── CREATE ────────────────────────────────────────────────

    @Test
    @DisplayName("create: debe retornar Result exitoso con la direccion guardada")
    void create_shouldReturnSuccess_whenDeliveryIsSaved() {
        when(repositoryPort.save(any(Delivery.class))).thenReturn(delivery);

        Result<Delivery> result = deliveryService.create(delivery);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData().getAlias()).isEqualTo("Casa");
        verify(repositoryPort, times(1)).save(any(Delivery.class));
    }

    // ── FIND BY ID ────────────────────────────────────────────

    @Test
    @DisplayName("findById: debe retornar Result exitoso cuando la direccion existe")
    void findById_shouldReturnSuccess_whenDeliveryExists() {
        when(repositoryPort.findById("deliv_001")).thenReturn(Optional.of(delivery));

        Result<Delivery> result = deliveryService.findById("deliv_001");

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData().getId()).isEqualTo("deliv_001");
    }

    @Test
    @DisplayName("findById: debe retornar NOT_FOUND cuando la direccion no existe")
    void findById_shouldReturnNotFound_whenDeliveryDoesNotExist() {
        when(repositoryPort.findById("notexist")).thenReturn(Optional.empty());

        Result<Delivery> result = deliveryService.findById("notexist");

        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrorType()).isEqualTo(ErrorType.NOT_FOUND);
    }

    // ── FIND BY CUSTOMER ID ───────────────────────────────────

    @Test
    @DisplayName("findByCustomerId: debe retornar lista de direcciones del cliente")
    void findByCustomerId_shouldReturnSuccess_withDeliveryList() {
        when(repositoryPort.findByCustomerId("cust_001")).thenReturn(List.of(delivery));

        Result<List<Delivery>> result = deliveryService.findByCustomerId("cust_001");

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).hasSize(1);
    }

    @Test
    @DisplayName("findByCustomerId: debe retornar lista vacia cuando el cliente no tiene direcciones")
    void findByCustomerId_shouldReturnSuccess_withEmptyList() {
        when(repositoryPort.findByCustomerId("cust_001")).thenReturn(List.of());

        Result<List<Delivery>> result = deliveryService.findByCustomerId("cust_001");

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).isEmpty();
    }

    // ── UPDATE ────────────────────────────────────────────────

    @Test
    @DisplayName("update: debe retornar Result exitoso cuando la direccion existe")
    void update_shouldReturnSuccess_whenDeliveryExists() {
        Delivery updated = Delivery.builder()
                .id("deliv_001")
                .customerId("cust_001")
                .alias("Oficina")
                .street("Paseo de la Reforma 500")
                .colony("Cuauhtemoc")
                .city("Ciudad de Mexico")
                .state("CDMX")
                .zipCode("06600")
                .country("Mexico")
                .build();

        when(repositoryPort.existsById("deliv_001")).thenReturn(true);
        when(repositoryPort.save(any(Delivery.class))).thenReturn(updated);

        Result<Delivery> result = deliveryService.update("deliv_001", delivery);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData().getAlias()).isEqualTo("Oficina");
        verify(repositoryPort, times(1)).existsById("deliv_001");
        verify(repositoryPort, times(1)).save(any(Delivery.class));
    }

    @Test
    @DisplayName("update: debe retornar NOT_FOUND cuando la direccion no existe")
    void update_shouldReturnNotFound_whenDeliveryDoesNotExist() {
        when(repositoryPort.existsById("notexist")).thenReturn(false);

        Result<Delivery> result = deliveryService.update("notexist", delivery);

        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrorType()).isEqualTo(ErrorType.NOT_FOUND);
        verify(repositoryPort, never()).save(any(Delivery.class));
    }

    // ── DELETE ────────────────────────────────────────────────

    @Test
    @DisplayName("deleteById: debe retornar Result exitoso cuando la direccion existe")
    void deleteById_shouldReturnSuccess_whenDeliveryExists() {
        when(repositoryPort.existsById("deliv_001")).thenReturn(true);

        Result<Void> result = deliveryService.deleteById("deliv_001");

        assertThat(result.isSuccess()).isTrue();
        verify(repositoryPort, times(1)).deleteById("deliv_001");
    }

    @Test
    @DisplayName("deleteById: debe retornar NOT_FOUND cuando la direccion no existe")
    void deleteById_shouldReturnNotFound_whenDeliveryDoesNotExist() {
        when(repositoryPort.existsById("notexist")).thenReturn(false);

        Result<Void> result = deliveryService.deleteById("notexist");

        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrorType()).isEqualTo(ErrorType.NOT_FOUND);
        verify(repositoryPort, never()).deleteById(anyString());
    }
}
