package com.liverpool.msoventas.order.infrastructure.config;

import com.liverpool.msoventas.order.application.service.OrderService;
import com.liverpool.msoventas.order.domain.port.out.OrderRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Composition Root del modulo de pedidos.
 *
 * <p>Registra {@link OrderService} como bean de Spring sin anotarlo con
 * {@code @Service}, preservando el desacoplamiento de la capa de aplicacion
 * respecto al framework. Spring inyectara el {@link OrderRepositoryPort}
 * implementado por el adaptador de persistencia.</p>
 */
@Configuration
public class OrderBeanConfig {

    /**
     * Crea y registra el servicio de aplicacion de pedidos.
     *
     * @param orderRepositoryPort adaptador de persistencia inyectado por Spring
     * @return instancia configurada de {@link OrderService}
     */
    @Bean
    public OrderService orderService(OrderRepositoryPort orderRepositoryPort) {
        return new OrderService(orderRepositoryPort);
    }
}
