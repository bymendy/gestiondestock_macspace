package com.macspace.gestiondestock.dto.auth;

import lombok.Builder;
import lombok.Data;

/**
 * Représente la réponse d'authentification retournée après une tentative d'authentification réussie.
 * <p>
 * Cette classe encapsule les détails nécessaires pour que l'utilisateur puisse accéder aux ressources protégées.
 * </p>
 */
@Data
@Builder
public class AuthenticationResponse {

    /**
     * Le jeton d'accès (token) attribué à l'utilisateur après une authentification réussie.
     * <p>
     * Ce jeton est généralement utilisé pour autoriser les requêtes ultérieures aux endpoints sécurisés de l'application.
     * </p>
     */
    private String accessToken;

}
