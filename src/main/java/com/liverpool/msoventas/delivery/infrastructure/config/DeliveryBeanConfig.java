package com.liverpool.msoventas.delivery.infrastructure.config;

import com.liverpool.msoventas.delivery.application.service.DeliveryService;
import com.liverpool.msoventas.delivery.domain.port.out.DeliveryRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Composition Root del modulo de direcciones de entrega.
 *
 * <p>Registra {@link DeliveryService} como bean de Spring sin anotarlo con
 * {@code @Service}, preservando el desacoplamiento del servicio de aplicacion
 * respecto al framework. Spring inyectara el {@link DeliveryRepositoryPort}
 * implementado por el adaptador de persistencia.</p>
 */
@Configuration
public class DeliveryBeanConfig {

    /**
     * Crea y registra el servicio de aplicacion de direcciones de entrega.
     *
     * @param deliveryRepositoryPort adaptador de persistencia inyectado por Spring
     * @return instancia configurada de {@link DeliveryService}
     */
    @Bean
    public DeliveryService deliveryService(DeliveryRepositoryPort deliveryRepositoryPort) {
        return new DeliveryService(deliveryRepositoryPort);
    }
}
