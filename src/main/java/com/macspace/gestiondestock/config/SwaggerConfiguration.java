package com.macspace.gestiondestock.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Springdoc OpenAPI pour MacSpace.
 * Génère la documentation de l'API REST et configure
 * l'authentification JWT dans Swagger UI.
 */
@Configuration
public class SwaggerConfiguration {

    /**
     * Nom du schéma de sécurité JWT.
     */
    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    /**
     * Configure et retourne le bean OpenAPI pour Springdoc.
     * Définit les informations de l'API et le schéma
     * de sécurité JWT Bearer.
     *
     * @return La configuration OpenAPI complète.
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(SECURITY_SCHEME_NAME))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        SECURITY_SCHEME_NAME,
                                        securityScheme()));
    }

    /**
     * Définit les informations générales de l'API MacSpace.
     *
     * @return Les informations de l'API.
     */
    private Info apiInfo() {
        return new Info()
                .title("MacSpace API")
                .description(
                        "API REST pour la gestion des installations "
                                + "de sécurité MacSpace. "
                                + "Gestion des interventions, produits, "
                                + "clients et fournisseurs.")
                .version("v1.0")
                .contact(new Contact()
                        .name("Mac Sécurité")
                        .email("contact@macsecurite.fr"))
                .license(new License()
                        .name("Propriétaire")
                        .url("https://macsecurite.fr"));
    }

    /**
     * Configure le schéma de sécurité JWT Bearer pour Swagger.
     * Permet de tester les endpoints sécurisés directement
     * depuis Swagger UI.
     *
     * @return Le schéma de sécurité JWT.
     */
    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description(
                        "Entrez votre token JWT : Bearer {token}");
    }
}