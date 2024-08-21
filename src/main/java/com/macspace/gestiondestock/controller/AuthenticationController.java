package com.macspace.gestiondestock.controller;

import com.macspace.gestiondestock.controller.api.AuthenticationApi;
import com.macspace.gestiondestock.dto.auth.AuthenticationRequest;
import com.macspace.gestiondestock.dto.auth.AuthenticationResponse;
import com.macspace.gestiondestock.model.auth.ExtendedUser;
import com.macspace.gestiondestock.services.auth.ApplicationUserDetailsService;
import com.macspace.gestiondestock.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour gérer l'authentification des utilisateurs.
 * <p>
 * Ce contrôleur expose une API d'authentification qui permet de vérifier les informations
 * d'identification des utilisateurs et de générer un token JWT pour les sessions authentifiées.
 * </p>
 */
@RestController
public class AuthenticationController implements AuthenticationApi {

    // Injecte l'AuthenticationManager pour gérer l'authentification des utilisateurs
    @Autowired
    private AuthenticationManager authenticationManager;

    // Injecte le service de détails d'utilisateur personnalisé
    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    // Injecte l'utilitaire pour la gestion des tokens JWT
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Authentifie un utilisateur avec les informations fournies dans la requête.
     * <p>
     * Cette méthode valide les informations d'identification de l'utilisateur (login et mot de passe),
     * charge les détails de l'utilisateur, et génère un token JWT si l'authentification réussit.
     * Le token JWT est renvoyé dans la réponse pour être utilisé lors des requêtes suivantes.
     * </p>
     *
     * @param request l'objet {@link AuthenticationRequest} contenant le login et le mot de passe de l'utilisateur
     * @return une réponse {@link ResponseEntity} contenant l'objet {@link AuthenticationResponse} avec le token JWT
     */
    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        // Authentifie l'utilisateur avec son login et mot de passe
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        // Charge les détails de l'utilisateur en utilisant le service de détails d'utilisateur personnalisé
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());

        // Génère un token JWT pour l'utilisateur authentifié
        final String jwt = jwtUtil.generateToken((ExtendedUser) userDetails);

        // Renvoie la réponse avec le token JWT
        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());
    }
}
