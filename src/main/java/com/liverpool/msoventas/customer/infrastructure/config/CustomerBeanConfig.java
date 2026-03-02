package com.liverpool.msoventas.customer.infrastructure.config;

import com.liverpool.msoventas.customer.application.service.CustomerService;
import com.liverpool.msoventas.customer.domain.port.out.CustomerRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Composition Root del modulo de clientes.
 *
 * <p>Registra {@link CustomerService} como bean de Spring sin anotarlo con
 * {@code @Service}, manteniendo el servicio de aplicacion desacoplado del
 * framework. Spring inyectara automaticamente el {@link CustomerRepositoryPort}
 * implementado por el adaptador de persistencia.</p>
 */
@Configuration
public class CustomerBeanConfig {

    /**
     * Crea y registra el servicio de aplicacion de clientes.
     *
     * @param repositoryPort adaptador de persistencia inyectado por Spring
     * @return instancia configurada de {@link CustomerService}
     */
    @Bean
    public CustomerService customerService(CustomerRepositoryPort repositoryPort) {
        return new CustomerService(repositoryPort);
    }

}
