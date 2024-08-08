package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.auth.AuthenticationRequest;
import com.macspace.gestiondestock.dto.auth.AuthenticationResponse;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.macspace.gestiondestock.utils.Constants.AUTHENTICATION_ENDPOINT;

/**
 * Interface définissant les endpoints d'authentification de l'API.
 * <p>
 * Cette interface expose un endpoint pour l'authentification des utilisateurs.
 * Les détails d'authentification sont fournis dans le corps de la requête, et une réponse d'authentification
 * est renvoyée en retour.
 * </p>
 */
@Api("authentication")
public interface AuthenticationApi {

    /**
     * Authentifie un utilisateur en utilisant les informations d'identification fournies.
     * <p>
     * Cette méthode reçoit une requête d'authentification contenant les informations nécessaires
     * (nom d'utilisateur, mot de passe, etc.) et renvoie une réponse contenant les détails de l'authentification,
     * comme un token JWT ou d'autres informations pertinentes.
     * </p>
     *
     * @param request les informations d'authentification fournies par l'utilisateur, encapsulées dans un {@link AuthenticationRequest}
     * @return un {@link ResponseEntity} contenant un {@link AuthenticationResponse} avec les détails de l'authentification
     */

   @PostMapping(AUTHENTICATION_ENDPOINT + "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);

}
