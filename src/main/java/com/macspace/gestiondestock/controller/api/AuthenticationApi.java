package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.auth.AuthenticationRequest;
import com.macspace.gestiondestock.dto.auth.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.macspace.gestiondestock.utils.Constants.AUTHENTICATION_ENDPOINT;

/**
 * Interface définissant les endpoints d'authentification de MacSpace.
 */
@Tag(name = "Authentification",
        description = "API d'authentification MacSpace")
public interface AuthenticationApi {

    /**
     * Authentifie un utilisateur et retourne un token JWT.
     *
     * @param request Les informations d'authentification.
     * @return Un token JWT si l'authentification réussit.
     */
    @Operation(
            summary = "Authentifier un utilisateur",
            description = "Retourne un token JWT si les credentials sont valides"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentification réussie"),
            @ApiResponse(responseCode = "401", description = "Credentials invalides"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping(AUTHENTICATION_ENDPOINT + "/authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request);
}