package com.liverpool.msoventas.delivery.infrastructure.adapter.out.persistence;

import com.liverpool.msoventas.delivery.domain.model.Delivery;
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
@DisplayName("DeliveryMongoAdapter - Tests Unitarios")
class DeliveryMongoAdapterTest {

    @Mock
    private SpringDataDeliveryRepository repository;

    @InjectMocks
    private DeliveryMongoAdapter adapter;

    private DeliveryDocument document;
    private Delivery delivery;

    @BeforeEach
    void setUp() {
        document = DeliveryDocument.builder()
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

    @Test
    @DisplayName("save: debe guardar y retornar el dominio correctamente")
    void save_shouldReturnDomain_whenDocumentIsSaved() {
        when(repository.save(any(DeliveryDocument.class))).thenReturn(document);

        Delivery result = adapter.save(delivery);

        assertThat(result.getId()).isEqualTo("deliv_001");
        assertThat(result.getAlias()).isEqualTo("Casa");
        verify(repository, times(1)).save(any(DeliveryDocument.class));
    }

    @Test
    @DisplayName("findById: debe retornar Optional con dominio cuando existe")
    void findById_shouldReturnDomain_whenDocumentExists() {
        when(repository.findById("deliv_001")).thenReturn(Optional.of(document));

        Optional<Delivery> result = adapter.findById("deliv_001");

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo("deliv_001");
    }

    @Test
    @DisplayName("findById: debe retornar Optional vacio cuando no existe")
    void findById_shouldReturnEmpty_whenDocumentDoesNotExist() {
        when(repository.findById("notexist")).thenReturn(Optional.empty());

        Optional<Delivery> result = adapter.findById("notexist");

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findByCustomerId: debe retornar lista de dominios del cliente")
    void findByCustomerId_shouldReturnDomainList() {
        when(repository.findByCustomerId("cust_001")).thenReturn(List.of(document));

        List<Delivery> result = adapter.findByCustomerId("cust_001");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCustomerId()).isEqualTo("cust_001");
    }

    @Test
    @DisplayName("existsById: debe retornar true cuando existe")
    void existsById_shouldReturnTrue_whenExists() {
        when(repository.existsById("deliv_001")).thenReturn(true);

        assertThat(adapter.existsById("deliv_001")).isTrue();
    }

    @Test
    @DisplayName("deleteById: debe llamar al repositorio correctamente")
    void deleteById_shouldCallRepository() {
        adapter.deleteById("deliv_001");

        verify(repository, times(1)).deleteById("deliv_001");
    }
}
