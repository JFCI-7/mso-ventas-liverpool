package com.liverpool.msoventas.customer.infrastructure.adapter.in.rest;

import com.liverpool.msoventas.customer.domain.model.Customer;
import com.liverpool.msoventas.customer.domain.port.in.CreateCustomerUseCase;
import com.liverpool.msoventas.customer.domain.port.in.DeleteCustomerUseCase;
import com.liverpool.msoventas.customer.domain.port.in.GetCustomerUseCase;
import com.liverpool.msoventas.customer.domain.port.in.UpdateCustomerUseCase;
import com.liverpool.msoventas.customer.infrastructure.adapter.in.rest.dto.CreateCustomerRequest;
import com.liverpool.msoventas.customer.infrastructure.adapter.in.rest.dto.CustomerResponse;
import com.liverpool.msoventas.customer.infrastructure.adapter.in.rest.dto.UpdateCustomerRequest;
import com.liverpool.msoventas.shared.domain.model.ErrorType;
import com.liverpool.msoventas.shared.domain.model.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Adaptador de entrada REST para el modulo de clientes.
 *
 * <p>Expone los endpoints bajo {@code /api/v1/customers} y delega cada
 * operacion al caso de uso correspondiente. Traduce el resultado del dominio
 * ({@link com.liverpool.msoventas.shared.domain.model.Result}) al codigo
 * de estado HTTP apropiado mediante {@code resolveStatus}.</p>
 */
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "API para gestión de clientes")
public class CustomerController {
	
	private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    
    @PostMapping
    @Operation(summary = "Crear un nuevo cliente")
    public ResponseEntity<?> create(@Valid @RequestBody CreateCustomerRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .motherLastName(request.getMotherLastName())
                .email(request.getEmail())
                .build();

        Result<Customer> result = createCustomerUseCase.create(customer);

        if (result.isFailure()) {
            return ResponseEntity.status(resolveStatus(result.getErrorType()))
                    .body(result.getErrorMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CustomerResponse.fromDomain(result.getData()));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Result<Customer> result = getCustomerUseCase.findById(id);

        if (result.isFailure()) {
            return ResponseEntity.status(resolveStatus(result.getErrorType()))
                    .body(result.getErrorMessage());
        }

        return ResponseEntity.ok(CustomerResponse.fromDomain(result.getData()));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los clientes")
    public ResponseEntity<List<CustomerResponse>> findAll() {
        Result<List<Customer>> result = getCustomerUseCase.findAll();

        List<CustomerResponse> response = result.getData().stream()
                .map(CustomerResponse::fromDomain)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cliente")
    public ResponseEntity<?> update(@PathVariable String id,
                                    @Valid @RequestBody UpdateCustomerRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .motherLastName(request.getMotherLastName())
                .email(request.getEmail())
                .build();

        Result<Customer> result = updateCustomerUseCase.update(id, customer);

        if (result.isFailure()) {
            return ResponseEntity.status(resolveStatus(result.getErrorType()))
                    .body(result.getErrorMessage());
        }

        return ResponseEntity.ok(CustomerResponse.fromDomain(result.getData()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        Result<Void> result = deleteCustomerUseCase.deleteById(id);

        if (result.isFailure()) {
            return ResponseEntity.status(resolveStatus(result.getErrorType()))
                    .body(result.getErrorMessage());
        }

        return ResponseEntity.noContent().build();
    }


    private HttpStatus resolveStatus(ErrorType errorType) {
        return switch (errorType) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case CONFLICT -> HttpStatus.CONFLICT;
            case VALIDATION_ERROR -> HttpStatus.BAD_REQUEST;
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

}
