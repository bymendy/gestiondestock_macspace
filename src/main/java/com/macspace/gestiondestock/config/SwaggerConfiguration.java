package com.macspace.gestiondestock.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration pour Swagger, un outil de documentation d'API REST.
 * Cette classe configure Swagger pour générer la documentation de l'API et inclut des mécanismes de sécurité pour l'authentification JWT.
 */
//@Configuration
//EnableSwagger2
public class SwaggerConfiguration {

    /**
     * Nom de l'en-tête HTTP utilisé pour l'authentification.
     */
    //public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Configure et retourne un bean {@link Docket} pour Swagger.
     * Ce bean spécifie les détails de l'API et les paramètres de sécurité.
     *
     * @return Un bean {@link Docket} configuré pour Swagger.
     */
    //@Bean
    /** public Docket api() {
     return new Docket(DocumentationType.SWAGGER_2)
     .apiInfo(
     new ApiInfoBuilder()
     .description("Gestion de stock API documentation")
     .title("Gestion de stock Application REST API")
     .build()
     )
     .groupName("REST API V1")
     .securityContexts(Collections.singletonList(securityContext()))
     .securitySchemes(Collections.singletonList(apiKey()))
     .useDefaultResponseMessages(false)
     .select()
     .apis(RequestHandlerSelectors.basePackage("com.macspace.gestiondestock"))
     .paths(PathSelectors.any())
     .build();
     } */

    /**
     * Configure et retourne un objet {@link ApiKey} pour JWT.
     * Ce bean définit le schéma de sécurité pour l'authentification JWT en utilisant l'en-tête HTTP.
     *
     * @return Un objet {@link ApiKey} configuré pour JWT.
     */
    //private ApiKey apiKey() {
    // return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    // }

    /**
     * Configure et retourne un objet {@link SecurityContext} pour Swagger.
     * Ce bean définit le contexte de sécurité pour l'authentification JWT.
     *
     * @return Un objet {@link SecurityContext} configuré pour Swagger.
     */
    //private SecurityContext securityContext() {
    // return SecurityContext.builder()
    //     .securityReferences(defaultAuth())
    //      .build();
    // }

    /**
     * Retourne la liste des références de sécurité par défaut pour JWT.
     * Cette méthode définit les autorisations globales pour l'accès à l'API.
     *
     * @return Une liste de {@link SecurityReference} pour JWT.
     */
    /**List<SecurityReference> defaultAuth() {
     AuthorizationScope authorizationScope
     = new AuthorizationScope("global", "accessEverything");
     AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
     authorizationScopes[0] = authorizationScope;
     return Collections.singletonList(
     new SecurityReference("JWT", authorizationScopes));
     }*/

}
