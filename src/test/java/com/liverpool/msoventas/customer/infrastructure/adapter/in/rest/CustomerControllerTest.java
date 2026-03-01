package com.liverpool.msoventas.customer.infrastructure.adapter.in.rest;

import com.liverpool.msoventas.customer.domain.model.Customer;
import com.liverpool.msoventas.customer.domain.port.in.CreateCustomerUseCase;
import com.liverpool.msoventas.customer.domain.port.in.DeleteCustomerUseCase;
import com.liverpool.msoventas.customer.domain.port.in.GetCustomerUseCase;
import com.liverpool.msoventas.customer.domain.port.in.UpdateCustomerUseCase;
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

@WebMvcTest(CustomerController.class)
@DisplayName("CustomerController - Tests de Integración")
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateCustomerUseCase createCustomerUseCase;

    @MockitoBean
    private GetCustomerUseCase getCustomerUseCase;

    @MockitoBean
    private UpdateCustomerUseCase updateCustomerUseCase;

    @MockitoBean
    private DeleteCustomerUseCase deleteCustomerUseCase;

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

    // ── POST /api/v1/customers ────────────────────────────────

    @Test
    @DisplayName("POST: debe retornar 201 con el cliente creado")
    void create_shouldReturn201_whenValidRequest() throws Exception {
        when(createCustomerUseCase.create(any(Customer.class)))
                .thenReturn(Result.success(customer));

        String body = """
                {
                    "firstName": "Juan",
                    "lastName": "Pérez",
                    "motherLastName": "García",
                    "email": "juan.perez@email.com"
                }
                """;

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("abc123"))
                .andExpect(jsonPath("$.firstName").value("Juan"));
    }

    @Test
    @DisplayName("POST: debe retornar 400 cuando firstName está vacío")
    void create_shouldReturn400_whenFirstNameIsBlank() throws Exception {
        String body = """
                {
                    "firstName": "",
                    "lastName": "Pérez",
                    "motherLastName": "García",
                    "email": "juan.perez@email.com"
                }
                """;

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST: debe retornar 400 cuando el email es inválido")
    void create_shouldReturn400_whenEmailIsInvalid() throws Exception {
        String body = """
                {
                    "firstName": "Juan",
                    "lastName": "Pérez",
                    "motherLastName": "García",
                    "email": "not-an-email"
                }
                """;

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    // ── GET /api/v1/customers/{id} ────────────────────────────

    @Test
    @DisplayName("GET /{id}: debe retornar 200 con el cliente")
    void findById_shouldReturn200_whenCustomerExists() throws Exception {
        when(getCustomerUseCase.findById("abc123"))
                .thenReturn(Result.success(customer));

        mockMvc.perform(get("/api/v1/customers/abc123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc123"))
                .andExpect(jsonPath("$.email").value("juan.perez@email.com"));
    }

    @Test
    @DisplayName("GET /{id}: debe retornar 404 cuando el cliente no existe")
    void findById_shouldReturn404_whenCustomerNotFound() throws Exception {
        when(getCustomerUseCase.findById("notexist"))
                .thenReturn(Result.failure("Cliente no encontrado", ErrorType.NOT_FOUND));

        mockMvc.perform(get("/api/v1/customers/notexist"))
                .andExpect(status().isNotFound());
    }

    // ── GET /api/v1/customers ─────────────────────────────────

    @Test
    @DisplayName("GET /: debe retornar 200 con lista de clientes")
    void findAll_shouldReturn200_withCustomerList() throws Exception {
        when(getCustomerUseCase.findAll())
                .thenReturn(Result.success(List.of(customer)));

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].firstName").value("Juan"));
    }

    // ── PUT /api/v1/customers/{id} ────────────────────────────

    @Test
    @DisplayName("PUT /{id}: debe retornar 200 con cliente actualizado")
    void update_shouldReturn200_whenCustomerExists() throws Exception {
        when(updateCustomerUseCase.update(anyString(), any(Customer.class)))
                .thenReturn(Result.success(customer));

        String body = """
                {
                    "firstName": "Juan",
                    "lastName": "Pérez",
                    "motherLastName": "García",
                    "email": "juan.perez@email.com"
                }
                """;

        mockMvc.perform(put("/api/v1/customers/abc123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc123"));
    }

    @Test
    @DisplayName("PUT /{id}: debe retornar 404 cuando el cliente no existe")
    void update_shouldReturn404_whenCustomerNotFound() throws Exception {
        when(updateCustomerUseCase.update(anyString(), any(Customer.class)))
                .thenReturn(Result.failure("Cliente no encontrado", ErrorType.NOT_FOUND));

        String body = """
                {
                    "firstName": "Juan",
                    "lastName": "Pérez",
                    "motherLastName": "García",
                    "email": "juan.perez@email.com"
                }
                """;

        mockMvc.perform(put("/api/v1/customers/notexist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());
    }

    // ── DELETE /api/v1/customers/{id} ─────────────────────────

    @Test
    @DisplayName("DELETE /{id}: debe retornar 204 cuando el cliente es eliminado")
    void deleteById_shouldReturn204_whenCustomerDeleted() throws Exception {
        when(deleteCustomerUseCase.deleteById("abc123"))
                .thenReturn(Result.success(null));

        mockMvc.perform(delete("/api/v1/customers/abc123"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /{id}: debe retornar 404 cuando el cliente no existe")
    void deleteById_shouldReturn404_whenCustomerNotFound() throws Exception {
        when(deleteCustomerUseCase.deleteById("notexist"))
                .thenReturn(Result.failure("Cliente no encontrado", ErrorType.NOT_FOUND));

        mockMvc.perform(delete("/api/v1/customers/notexist"))
                .andExpect(status().isNotFound());
    }
}
