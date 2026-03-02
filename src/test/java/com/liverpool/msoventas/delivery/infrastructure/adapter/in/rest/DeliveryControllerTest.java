package com.liverpool.msoventas.delivery.infrastructure.adapter.in.rest;

import com.liverpool.msoventas.delivery.domain.model.Delivery;
import com.liverpool.msoventas.delivery.domain.port.in.CreateDeliveryUseCase;
import com.liverpool.msoventas.delivery.domain.port.in.DeleteDeliveryUseCase;
import com.liverpool.msoventas.delivery.domain.port.in.GetDeliveryUseCase;
import com.liverpool.msoventas.delivery.domain.port.in.UpdateDeliveryUseCase;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeliveryController.class)
@DisplayName("DeliveryController - Tests de Integracion")
class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateDeliveryUseCase createDeliveryUseCase;

    @MockitoBean
    private GetDeliveryUseCase getDeliveryUseCase;

    @MockitoBean
    private UpdateDeliveryUseCase updateDeliveryUseCase;

    @MockitoBean
    private DeleteDeliveryUseCase deleteDeliveryUseCase;

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

    // ── POST ──────────────────────────────────────────────────

    @Test
    @DisplayName("POST /deliveries: debe retornar 201 cuando la direccion es valida")
    void create_shouldReturn201_whenRequestIsValid() throws Exception {
        when(createDeliveryUseCase.create(any(Delivery.class)))
                .thenReturn(Result.success(delivery));

        String body = """
                {
                  "customerId": "cust_001",
                  "alias": "Casa",
                  "street": "Av. Insurgentes Sur 1234",
                  "colony": "Del Valle",
                  "city": "Ciudad de Mexico",
                  "state": "CDMX",
                  "zipCode": "03100",
                  "country": "Mexico"
                }
                """;

        mockMvc.perform(post("/api/v1/deliveries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("deliv_001"))
                .andExpect(jsonPath("$.alias").value("Casa"));
    }

    @Test
    @DisplayName("POST /deliveries: debe retornar 400 cuando faltan campos requeridos")
    void create_shouldReturn400_whenRequestIsInvalid() throws Exception {
        String body = """
                {
                  "alias": "Casa"
                }
                """;

        mockMvc.perform(post("/api/v1/deliveries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    // ── GET BY ID ─────────────────────────────────────────────

    @Test
    @DisplayName("GET /deliveries/{id}: debe retornar 200 cuando la direccion existe")
    void findById_shouldReturn200_whenDeliveryExists() throws Exception {
        when(getDeliveryUseCase.findById("deliv_001"))
                .thenReturn(Result.success(delivery));

        mockMvc.perform(get("/api/v1/deliveries/deliv_001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("deliv_001"))
                .andExpect(jsonPath("$.customerId").value("cust_001"));
    }

    @Test
    @DisplayName("GET /deliveries/{id}: debe retornar 404 cuando la direccion no existe")
    void findById_shouldReturn404_whenDeliveryDoesNotExist() throws Exception {
        when(getDeliveryUseCase.findById("notexist"))
                .thenReturn(Result.failure("Not found", ErrorType.NOT_FOUND));

        mockMvc.perform(get("/api/v1/deliveries/notexist"))
                .andExpect(status().isNotFound());
    }

    // ── GET BY CUSTOMER ID ────────────────────────────────────

    @Test
    @DisplayName("GET /deliveries?customerId=: debe retornar 200 con lista de direcciones")
    void findByCustomerId_shouldReturn200_withList() throws Exception {
        when(getDeliveryUseCase.findByCustomerId("cust_001"))
                .thenReturn(Result.success(List.of(delivery)));

        mockMvc.perform(get("/api/v1/deliveries").param("customerId", "cust_001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].alias").value("Casa"));
    }

    // ── PUT ───────────────────────────────────────────────────

    @Test
    @DisplayName("PUT /deliveries/{id}: debe retornar 200 cuando la actualizacion es exitosa")
    void update_shouldReturn200_whenDeliveryExists() throws Exception {
        when(updateDeliveryUseCase.update(anyString(), any(Delivery.class)))
                .thenReturn(Result.success(delivery));

        String body = """
                {
                  "alias": "Casa",
                  "street": "Av. Insurgentes Sur 1234",
                  "colony": "Del Valle",
                  "city": "Ciudad de Mexico",
                  "state": "CDMX",
                  "zipCode": "03100",
                  "country": "Mexico"
                }
                """;

        mockMvc.perform(put("/api/v1/deliveries/deliv_001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("deliv_001"));
    }

    @Test
    @DisplayName("PUT /deliveries/{id}: debe retornar 404 cuando la direccion no existe")
    void update_shouldReturn404_whenDeliveryDoesNotExist() throws Exception {
        when(updateDeliveryUseCase.update(anyString(), any(Delivery.class)))
                .thenReturn(Result.failure("Not found", ErrorType.NOT_FOUND));

        String body = """
                {
                  "alias": "Casa",
                  "street": "Av. Insurgentes Sur 1234",
                  "colony": "Del Valle",
                  "city": "Ciudad de Mexico",
                  "state": "CDMX",
                  "zipCode": "03100",
                  "country": "Mexico"
                }
                """;

        mockMvc.perform(put("/api/v1/deliveries/notexist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());
    }

    // ── DELETE ────────────────────────────────────────────────

    @Test
    @DisplayName("DELETE /deliveries/{id}: debe retornar 204 cuando la eliminacion es exitosa")
    void deleteById_shouldReturn204_whenDeliveryExists() throws Exception {
        when(deleteDeliveryUseCase.deleteById("deliv_001"))
                .thenReturn(Result.success(null));

        mockMvc.perform(delete("/api/v1/deliveries/deliv_001"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /deliveries/{id}: debe retornar 404 cuando la direccion no existe")
    void deleteById_shouldReturn404_whenDeliveryDoesNotExist() throws Exception {
        when(deleteDeliveryUseCase.deleteById("notexist"))
                .thenReturn(Result.failure("Not found", ErrorType.NOT_FOUND));

        mockMvc.perform(delete("/api/v1/deliveries/notexist"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("POST /deliveries: debe retornar 409 cuando hay conflicto")
    void create_shouldReturn409_whenConflict() throws Exception {
        when(createDeliveryUseCase.create(any(Delivery.class)))
                .thenReturn(Result.failure("Conflict", ErrorType.CONFLICT));

        String body = """
                {
                  "customerId": "cust_001",
                  "alias": "Casa",
                  "street": "Av. Insurgentes Sur 1234",
                  "colony": "Del Valle",
                  "city": "Ciudad de Mexico",
                  "state": "CDMX",
                  "zipCode": "03100",
                  "country": "Mexico"
                }
                """;

        mockMvc.perform(post("/api/v1/deliveries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("POST /deliveries: debe retornar 500 cuando hay error interno")
    void create_shouldReturn500_whenInternalError() throws Exception {
        when(createDeliveryUseCase.create(any(Delivery.class)))
                .thenReturn(Result.failure("Error", ErrorType.INTERNAL_ERROR));

        String body = """
                {
                  "customerId": "cust_001",
                  "alias": "Casa",
                  "street": "Av. Insurgentes Sur 1234",
                  "colony": "Del Valle",
                  "city": "Ciudad de Mexico",
                  "state": "CDMX",
                  "zipCode": "03100",
                  "country": "Mexico"
                }
                """;

        mockMvc.perform(post("/api/v1/deliveries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isInternalServerError());
    }
    
    @Test
    @DisplayName("GET /deliveries/{id}: debe retornar 400 cuando hay error de validacion")
    void findById_shouldReturn400_whenValidationError() throws Exception {
        when(getDeliveryUseCase.findById("deliv_001"))
                .thenReturn(Result.failure("Validation error", ErrorType.VALIDATION_ERROR));

        mockMvc.perform(get("/api/v1/deliveries/deliv_001"))
                .andExpect(status().isBadRequest());
    }
}
