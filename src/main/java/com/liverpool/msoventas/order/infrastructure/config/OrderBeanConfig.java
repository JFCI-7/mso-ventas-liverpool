package com.liverpool.msoventas.order.infrastructure.config;

import com.liverpool.msoventas.order.application.service.OrderService;
import com.liverpool.msoventas.order.domain.port.out.OrderRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderBeanConfig {

    @Bean
    public OrderService orderService(OrderRepositoryPort orderRepositoryPort) {
        return new OrderService(orderRepositoryPort);
    }
}
