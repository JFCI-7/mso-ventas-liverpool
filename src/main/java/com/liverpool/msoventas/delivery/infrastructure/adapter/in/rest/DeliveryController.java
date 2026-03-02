package com.liverpool.msoventas.delivery.infrastructure.adapter.in.rest;

import com.liverpool.msoventas.delivery.domain.model.Delivery;
import com.liverpool.msoventas.delivery.domain.port.in.CreateDeliveryUseCase;
import com.liverpool.msoventas.delivery.domain.port.in.DeleteDeliveryUseCase;
import com.liverpool.msoventas.delivery.domain.port.in.GetDeliveryUseCase;
import com.liverpool.msoventas.delivery.domain.port.in.UpdateDeliveryUseCase;
import com.liverpool.msoventas.delivery.infrastructure.adapter.in.rest.dto.CreateDeliveryRequest;
import com.liverpool.msoventas.delivery.infrastructure.adapter.in.rest.dto.DeliveryResponse;
import com.liverpool.msoventas.delivery.infrastructure.adapter.in.rest.dto.UpdateDeliveryRequest;
import com.liverpool.msoventas.shared.domain.model.ErrorType;
import com.liverpool.msoventas.shared.domain.model.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Adaptador de entrada REST para el modulo de direcciones de entrega.
 *
 * <p>Expone los endpoints bajo {@code /api/v1/deliveries} y delega cada
 * operacion al caso de uso correspondiente. Traduce el resultado del dominio
 * al codigo de estado HTTP apropiado mediante {@code resolveStatus}.</p>
 */
@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
@Tag(name = "Deliveries", description = "Gestión de direcciones de entrega")
public class DeliveryController {

    private final CreateDeliveryUseCase createDeliveryUseCase;
    private final GetDeliveryUseCase getDeliveryUseCase;
    private final UpdateDeliveryUseCase updateDeliveryUseCase;
    private final DeleteDeliveryUseCase deleteDeliveryUseCase;

    @PostMapping
    @Operation(summary = "Crear dirección de entrega")
    @ApiResponse(responseCode = "201", description = "Direccion de entrega creada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    @ApiResponse(responseCode = "409", description = "Conflicto al crear la direccion")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<?> create(@Valid @RequestBody CreateDeliveryRequest request) {
        Delivery delivery = Delivery.builder()
                .customerId(request.getCustomerId())
                .alias(request.getAlias())
                .street(request.getStreet())
                .colony(request.getColony())
                .city(request.getCity())
                .state(request.getState())
                .zipCode(request.getZipCode())
                .country(request.getCountry())
                .build();

        Result<Delivery> result = createDeliveryUseCase.create(delivery);
        if (result.isFailure()) {
            return ResponseEntity.status(resolveStatus(result.getErrorType()))
                    .body(result.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DeliveryResponse.fromDomain(result.getData()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener dirección de entrega por ID")
    @ApiResponse(responseCode = "200", description = "Direccion de entrega encontrada")
    @ApiResponse(responseCode = "400", description = "Error de validacion")
    @ApiResponse(responseCode = "404", description = "Direccion de entrega no encontrada")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Result<Delivery> result = getDeliveryUseCase.findById(id);
        if (result.isFailure()) {
            return ResponseEntity.status(resolveStatus(result.getErrorType()))
                    .body(result.getErrorMessage());
        }
        return ResponseEntity.ok(DeliveryResponse.fromDomain(result.getData()));
    }

    @GetMapping
    @Operation(summary = "Obtener direcciones de entrega por cliente")
    @ApiResponse(responseCode = "200", description = "Lista de direcciones del cliente")
    public ResponseEntity<?> findByCustomerId(@RequestParam String customerId) {
        Result<List<Delivery>> result = getDeliveryUseCase.findByCustomerId(customerId);
        if (result.isFailure()) {
            return ResponseEntity.status(resolveStatus(result.getErrorType()))
                    .body(result.getErrorMessage());
        }
        List<DeliveryResponse> response = result.getData().stream()
                .map(DeliveryResponse::fromDomain)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar dirección de entrega")
    @ApiResponse(responseCode = "200", description = "Direccion actualizada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    @ApiResponse(responseCode = "404", description = "Direccion de entrega no encontrada")
    public ResponseEntity<?> update(@PathVariable String id,
                                    @Valid @RequestBody UpdateDeliveryRequest request) {
        Delivery delivery = Delivery.builder()
                .alias(request.getAlias())
                .street(request.getStreet())
                .colony(request.getColony())
                .city(request.getCity())
                .state(request.getState())
                .zipCode(request.getZipCode())
                .country(request.getCountry())
                .build();

        Result<Delivery> result = updateDeliveryUseCase.update(id, delivery);
        if (result.isFailure()) {
            return ResponseEntity.status(resolveStatus(result.getErrorType()))
                    .body(result.getErrorMessage());
        }
        return ResponseEntity.ok(DeliveryResponse.fromDomain(result.getData()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar dirección de entrega")
    @ApiResponse(responseCode = "204", description = "Direccion eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "Direccion de entrega no encontrada")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        Result<Void> result = deleteDeliveryUseCase.deleteById(id);
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
