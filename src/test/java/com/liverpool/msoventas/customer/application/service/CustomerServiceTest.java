package com.liverpool.msoventas.customer.application.service;

import com.liverpool.msoventas.customer.domain.model.Customer;
import com.liverpool.msoventas.customer.domain.port.out.CustomerRepositoryPort;
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
@DisplayName("CustomerService - Tests Unitarios")
class CustomerServiceTest {

    @Mock
    private CustomerRepositoryPort repositoryPort;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id("abc123")
                .firstName("Juan")
                .lastName("Pérez")
                .motherLastName("García")
                .email("juan.perez@email.com")
                .build();
    }

    // ── CREATE ────────────────────────────────────────────────

    @Test
    @DisplayName("create: debe retornar Result exitoso con el cliente guardado")
    void create_shouldReturnSuccess_whenCustomerIsSaved() {
        when(repositoryPort.save(any(Customer.class))).thenReturn(customer);

        Result<Customer> result = customerService.create(customer);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData().getFirstName()).isEqualTo("Juan");
        verify(repositoryPort, times(1)).save(any(Customer.class));
    }

    // ── FIND BY ID ────────────────────────────────────────────

    @Test
    @DisplayName("findById: debe retornar Result exitoso cuando el cliente existe")
    void findById_shouldReturnSuccess_whenCustomerExists() {
        when(repositoryPort.findById("abc123")).thenReturn(Optional.of(customer));

        Result<Customer> result = customerService.findById("abc123");

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData().getId()).isEqualTo("abc123");
    }

    @Test
    @DisplayName("findById: debe retornar NOT_FOUND cuando el cliente no existe")
    void findById_shouldReturnNotFound_whenCustomerDoesNotExist() {
        when(repositoryPort.findById("notexist")).thenReturn(Optional.empty());

        Result<Customer> result = customerService.findById("notexist");

        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrorType()).isEqualTo(ErrorType.NOT_FOUND);
    }

    // ── FIND ALL ──────────────────────────────────────────────

    @Test
    @DisplayName("findAll: debe retornar Result exitoso con la lista de clientes")
    void findAll_shouldReturnSuccess_withCustomerList() {
        when(repositoryPort.findAll()).thenReturn(List.of(customer));

        Result<List<Customer>> result = customerService.findAll();

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).hasSize(1);
    }

    @Test
    @DisplayName("findAll: debe retornar lista vacía cuando no hay clientes")
    void findAll_shouldReturnSuccess_withEmptyList() {
        when(repositoryPort.findAll()).thenReturn(List.of());

        Result<List<Customer>> result = customerService.findAll();

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).isEmpty();
    }

    // ── UPDATE ────────────────────────────────────────────────

    @Test
    @DisplayName("update: debe retornar Result exitoso cuando el cliente existe")
    void update_shouldReturnSuccess_whenCustomerExists() {
        Customer updated = Customer.builder()
                .id("abc123")
                .firstName("Carlos")
                .lastName("López")
                .motherLastName("Martínez")
                .email("carlos.lopez@email.com")
                .build();

        when(repositoryPort.existsById("abc123")).thenReturn(true);
        when(repositoryPort.save(any(Customer.class))).thenReturn(updated);

        Result<Customer> result = customerService.update("abc123", customer);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData().getFirstName()).isEqualTo("Carlos");
        verify(repositoryPort, times(1)).existsById("abc123");
        verify(repositoryPort, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("update: debe retornar NOT_FOUND cuando el cliente no existe")
    void update_shouldReturnNotFound_whenCustomerDoesNotExist() {
        when(repositoryPort.existsById("notexist")).thenReturn(false);

        Result<Customer> result = customerService.update("notexist", customer);

        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrorType()).isEqualTo(ErrorType.NOT_FOUND);
        verify(repositoryPort, never()).save(any(Customer.class));
    }

    // ── DELETE ────────────────────────────────────────────────

    @Test
    @DisplayName("deleteById: debe retornar Result exitoso cuando el cliente existe")
    void deleteById_shouldReturnSuccess_whenCustomerExists() {
        when(repositoryPort.existsById("abc123")).thenReturn(true);

        Result<Void> result = customerService.deleteById("abc123");

        assertThat(result.isSuccess()).isTrue();
        verify(repositoryPort, times(1)).deleteById("abc123");
    }

    @Test
    @DisplayName("deleteById: debe retornar NOT_FOUND cuando el cliente no existe")
    void deleteById_shouldReturnNotFound_whenCustomerDoesNotExist() {
        when(repositoryPort.existsById("notexist")).thenReturn(false);

        Result<Void> result = customerService.deleteById("notexist");

        assertThat(result.isFailure()).isTrue();
        assertThat(result.getErrorType()).isEqualTo(ErrorType.NOT_FOUND);
        verify(repositoryPort, never()).deleteById(anyString());
    }
}
