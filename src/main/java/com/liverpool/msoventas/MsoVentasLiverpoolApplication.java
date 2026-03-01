package com.liverpool.msoventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
