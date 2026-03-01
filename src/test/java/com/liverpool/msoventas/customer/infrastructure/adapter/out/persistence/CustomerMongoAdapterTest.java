package com.liverpool.msoventas.customer.infrastructure.adapter.out.persistence;

import com.liverpool.msoventas.customer.domain.model.Customer;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerMongoAdapter - Tests Unitarios")
class CustomerMongoAdapterTest {

    @Mock
    private SpringDataCustomerRepository repository;

    @InjectMocks
    private CustomerMongoAdapter adapter;

    private Customer customer;
    private CustomerDocument customerDocument;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id("abc123")
                .firstName("Juan")
                .lastName("Pérez")
                .motherLastName("García")
                .email("juan.perez@email.com")
                .build();

        customerDocument = CustomerDocument.builder()
                .id("abc123")
                .firstName("Juan")
                .lastName("Pérez")
                .motherLastName("García")
                .email("juan.perez@email.com")
                .build();
    }

    // ── SAVE ──────────────────────────────────────────────────

    @Test
    @DisplayName("save: debe convertir Customer a Document, guardar y retornar Customer")
    void save_shouldConvertAndReturnSavedCustomer() {
        when(repository.save(any(CustomerDocument.class))).thenReturn(customerDocument);

        Customer result = adapter.save(customer);

        assertThat(result.getId()).isEqualTo("abc123");
        assertThat(result.getFirstName()).isEqualTo("Juan");
        assertThat(result.getEmail()).isEqualTo("juan.perez@email.com");
        verify(repository, times(1)).save(any(CustomerDocument.class));
    }

    // ── FIND BY ID ────────────────────────────────────────────

    @Test
    @DisplayName("findById: debe retornar Optional con Customer cuando el documento existe")
    void findById_shouldReturnOptionalWithCustomer_whenDocumentExists() {
        when(repository.findById("abc123")).thenReturn(Optional.of(customerDocument));

        Optional<Customer> result = adapter.findById("abc123");

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo("abc123");
        assertThat(result.get().getFirstName()).isEqualTo("Juan");
    }

    @Test
    @DisplayName("findById: debe retornar Optional vacío cuando el documento no existe")
    void findById_shouldReturnEmptyOptional_whenDocumentDoesNotExist() {
        when(repository.findById("notexist")).thenReturn(Optional.empty());

        Optional<Customer> result = adapter.findById("notexist");

        assertThat(result).isEmpty();
    }

    // ── FIND ALL ──────────────────────────────────────────────

    @Test
    @DisplayName("findAll: debe retornar lista de Customer convertidos desde documentos")
    void findAll_shouldReturnConvertedCustomerList() {
        when(repository.findAll()).thenReturn(List.of(customerDocument));

        List<Customer> result = adapter.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("Juan");
        assertThat(result.get(0).getEmail()).isEqualTo("juan.perez@email.com");
    }

    // ── EXISTS BY ID ──────────────────────────────────────────

    @Test
    @DisplayName("existsById: debe retornar true cuando el cliente existe")
    void existsById_shouldReturnTrue_whenCustomerExists() {
        when(repository.existsById("abc123")).thenReturn(true);

        boolean result = adapter.existsById("abc123");

        assertThat(result).isTrue();
        verify(repository, times(1)).existsById("abc123");
    }

    @Test
    @DisplayName("existsById: debe retornar false cuando el cliente no existe")
    void existsById_shouldReturnFalse_whenCustomerDoesNotExist() {
        when(repository.existsById("notexist")).thenReturn(false);

        boolean result = adapter.existsById("notexist");

        assertThat(result).isFalse();
    }

    // ── DELETE ────────────────────────────────────────────────

    @Test
    @DisplayName("deleteById: debe llamar al repositorio con el id correcto")
    void deleteById_shouldDelegateToRepository_withCorrectId() {
        doNothing().when(repository).deleteById("abc123");

        adapter.deleteById("abc123");

        verify(repository, times(1)).deleteById("abc123");
    }
}
