package com.macspace.gestiondestock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Point d'entrée principal de l'application MacSpace.
 * Lance le contexte Spring Boot et initialise tous les composants.
 */
@SpringBootApplication
@EnableJpaAuditing
public class GestionDeStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(
				GestionDeStockApplication.class, args);
	}
}