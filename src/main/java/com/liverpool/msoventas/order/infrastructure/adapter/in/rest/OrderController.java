package com.liverpool.msoventas.order.infrastructure.adapter.in.rest;

import com.liverpool.msoventas.order.domain.model.Order;
import com.liverpool.msoventas.order.domain.model.OrderItem;
import com.liverpool.msoventas.order.domain.port.in.CreateOrderUseCase;
import com.liverpool.msoventas.order.domain.port.in.DeleteOrderUseCase;
import com.liverpool.msoventas.order.domain.port.in.GetOrderUseCase;
import com.liverpool.msoventas.order.domain.port.in.UpdateOrderUseCase;
import com.liverpool.msoventas.order.infrastructure.adapter.in.rest.dto.CreateOrderRequest;
import com.liverpool.msoventas.order.infrastructure.adapter.in.rest.dto.OrderResponse;
import com.liverpool.msoventas.order.infrastructure.adapter.in.rest.dto.UpdateOrderStatusRequest;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Adaptador de entrada REST para el modulo de pedidos.
 *
 * <p>Expone los endpoints bajo {@code /api/v1/orders}. El endpoint
 * {@code GET /api/v1/orders} acepta el parametro opcional {@code displayName}
 * para realizar busqueda flexible por nombre de articulo; si no se proporciona,
 * retorna todos los pedidos.</p>
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Gestion de pedidos")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;

    @PostMapping
    @Operation(summary = "Crear pedido")
    @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<?> create(@Valid @RequestBody CreateOrderRequest request) {
        List<OrderItem> items = request.getItems().stream()
                .map(item -> OrderItem.builder()
                        .productCode(item.getProductCode())
                        .displayName(item.getDisplayName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .toList();

        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .deliveryId(request.getDeliveryId())
                .items(items)
                .estimatedDeliveryDate(request.getEstimatedDeliveryDate())
                .build();

        Result<Order> result = createOrderUseCase.create(order);
        if (result.isFailure()) {
            return ResponseEntity.status(resolveStatus(result.getErrorType()))
                    .body(result.getErrorMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OrderResponse.fromDomain(result.getData()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado")
    @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Result<Order> result = getOrderUseCase.findById(id);
        if (result.isFailure()) {
            return ResponseEntity.status(resolveStatus(result.getErrorType()))
                    .body(result.getErrorMessage());
        }
        return ResponseEntity.ok(OrderResponse.fromDomain(result.getData()));
    }

    @GetMapping
    @Operation(
        summary = "Listar pedidos o buscar por displayName",
        description = "Si se provee displayName, filtra pedidos por nombre de articulo. Sin parametro retorna todos.")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente")
    public ResponseEntity<?> findOrders(
            @RequestParam(required = false) String displayName) {

        Result<List<Order>> result = (displayName != null && !displayName.isBlank())
                ? getOrderUseCase.findByDisplayName(displayName)
                : getOrderUseCase.findAll();

        if (result.isFailure()) {
            return ResponseEntity.status(resolveStatus(result.getErrorType()))
                    .body(result.getErrorMessage());
        }
        List<OrderResponse> response = result.getData().stream()
                .map(OrderResponse::fromDomain)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Actualizar estatus del pedido")
    @ApiResponse(responseCode = "200", description = "Estatus actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Estatus invalido o ausente")
    @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    public ResponseEntity<?> updateStatus(@PathVariable String id,
                                          @Valid @RequestBody UpdateOrderStatusRequest request) {
        Result<Order> result = updateOrderUseCase.updateStatus(id, request.getStatus());
        if (result.isFailure()) {
            return ResponseEntity.status(resolveStatus(result.getErrorType()))
                    .body(result.getErrorMessage());
        }
        return ResponseEntity.ok(OrderResponse.fromDomain(result.getData()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido")
    @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        Result<Void> result = deleteOrderUseCase.deleteById(id);
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
