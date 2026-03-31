package com.macspace.gestiondestock.config;

import com.macspace.gestiondestock.services.auth.ApplicationUserDetailsService;
import com.macspace.gestiondestock.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtre JWT pour MacSpace.
 * Intercepte chaque requête HTTP, extrait et valide le token JWT,
 * authentifie l'utilisateur et injecte l'idEntreprise dans le MDC
 * pour le support multi-tenant.
 */
@Component
public class ApplicationRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ApplicationUserDetailsService userDetailsService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param jwtUtil            L'utilitaire JWT.
     * @param userDetailsService Le service de détails utilisateur.
     */
    @Autowired
    public ApplicationRequestFilter(JwtUtil jwtUtil,
                                    ApplicationUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * {@inheritDoc}
     * Extrait et valide le token JWT de chaque requête HTTP.
     * Met à jour le SecurityContext et le MDC si le token est valide.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String userEmail = null;
        String jwt = null;
        Integer idEntreprise = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            userEmail = jwtUtil.extractUsername(jwt);
            idEntreprise = jwtUtil.extractIdEntreprise(jwt);
        }

        if (userEmail != null
                && SecurityContextHolder.getContext()
                .getAuthentication() == null) {
            UserDetails userDetails = userDetailsService
                    .loadUserByUsername(userEmail);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }

        MDC.put("idEntreprise",
                idEntreprise != null ? idEntreprise.toString() : null);

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}