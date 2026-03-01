package com.liverpool.msoventas.delivery.infrastructure.config;

import com.liverpool.msoventas.delivery.application.service.DeliveryService;
import com.liverpool.msoventas.delivery.domain.port.out.DeliveryRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeliveryBeanConfig {

    @Bean
    public DeliveryService deliveryService(DeliveryRepositoryPort deliveryRepositoryPort) {
        return new DeliveryService(deliveryRepositoryPort);
    }
}
