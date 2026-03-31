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
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour la gestion de l'authentification dans MacSpace.
 * Vérifie les credentials et génère un token JWT.
 */
@RestController
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationManager authenticationManager;
    private final ApplicationUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    /**
     * Constructeur avec injection de dépendances.
     */
    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            ApplicationUserDetailsService userDetailsService,
            JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(
            AuthenticationRequest request) {

        // Authentification via Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Chargement des détails utilisateur
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(request.getEmail());

        // Génération du token JWT
        final String jwt = jwtUtil.generateToken(
                (ExtendedUser) userDetails);

        // Récupération de l'idEntreprise pour le multi-tenant
        final Integer idEntreprise =
                ((ExtendedUser) userDetails).getIdEntreprise();

        return ResponseEntity.ok(
                AuthenticationResponse.builder()
                        .accessToken(jwt)
                        .idEntreprise(idEntreprise)
                        .build()
        );
    }
}