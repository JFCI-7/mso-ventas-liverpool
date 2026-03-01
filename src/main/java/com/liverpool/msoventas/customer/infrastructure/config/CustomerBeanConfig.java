package com.liverpool.msoventas.customer.infrastructure.config;

import com.liverpool.msoventas.customer.application.service.CustomerService;
import com.liverpool.msoventas.customer.domain.port.out.CustomerRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerBeanConfig {
	
	@Bean
    public CustomerService customerService(CustomerRepositoryPort repositoryPort) {
        return new CustomerService(repositoryPort);
    }

}
