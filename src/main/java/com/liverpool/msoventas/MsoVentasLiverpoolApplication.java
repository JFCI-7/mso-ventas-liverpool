package com.liverpool.msoventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Punto de entrada de la aplicacion {@code mso-ventas-liverpool}.
 *
 * <p>Microservicio de gestion de ventas construido con Spring Boot 4 y
 * Arquitectura Hexagonal. Expone APIs REST para los modulos de Clientes,
 * Direcciones de Entrega y Pedidos.</p>
 */
@SpringBootApplication
@RestController
public class MsoVentasLiverpoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsoVentasLiverpoolApplication.class, args);
    }

    @GetMapping
    public String testMso() {
        return "Recurso de prueba - Vebtas Liberpool";
    }

}
