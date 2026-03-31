package com.macspace.gestiondestock.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO représentant la réponse d'authentification dans MacSpace.
 * Retournée après une authentification réussie,
 * elle contient le token JWT et les informations de session.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    /**
     * Token JWT d'accès attribué après authentification.
     * Utilisé pour autoriser les requêtes aux endpoints sécurisés.
     */
    private String accessToken;

    /**
     * Type du token — toujours "Bearer" pour JWT.
     */
    @Builder.Default
    private String tokenType = "Bearer";

    /**
     * Identifiant de l'entreprise de l'utilisateur connecté.
     * Utilisé pour le support multi-tenant.
     */
    private Integer idEntreprise;
}