package com.macspace.gestiondestock.config;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.macspace.gestiondestock.services.auth.ApplicationUserDetailsService;
import com.macspace.gestiondestock.utils.JwtUtil;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtre personnalisé pour gérer l'authentification basée sur JWT dans les requêtes HTTP.
 * <p>
 * Ce filtre extrait le token JWT de l'en-tête d'autorisation, valide le token,
 * et met à jour le contexte de sécurité de Spring avec les informations d'authentification
 * de l'utilisateur. De plus, il ajoute l'identifiant de l'entreprise au contexte MDC pour la journalisation.
 * </p>
 */
@Component
public class ApplicationRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    /**
     * Filtre les requêtes HTTP pour extraire et valider le token JWT.
     * <p>
     * Si un token JWT valide est présent, l'utilisateur est authentifié
     * et le contexte de sécurité est mis à jour. L'identifiant de l'entreprise
     * est également ajouté au contexte MDC pour faciliter le suivi des requêtes.
     * </p>
     *
     * @param request  la requête HTTP entrante
     * @param response la réponse HTTP sortante
     * @param chain    le filtre suivant dans la chaîne
     * @throws ServletException si une erreur survient pendant le traitement de la requête
     * @throws IOException      si une erreur d'entrée/sortie survient pendant le traitement de la requête
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Extraction de l'en-tête Authorization de la requête
        final String authHeader = request.getHeader("Authorization");
        String userEmail = null;
        String jwt = null;
        String idEntreprise = null;

        // Vérifie si l'en-tête Authorization contient un token JWT Bearer
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);  // Extrait le token JWT après "Bearer "
            userEmail = jwtUtil.extractUsername(jwt);  // Extrait le nom d'utilisateur du token
            idEntreprise = jwtUtil.extractIdEntreprise(jwt);  // Extrait l'identifiant de l'entreprise du token
        }

        // Valide le token JWT et authentifie l'utilisateur si nécessaire
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        // Ajoute l'identifiant de l'entreprise au contexte MDC pour la journalisation
        MDC.put("idEntreprise", idEntreprise);

        // Continue la chaîne de filtres
        chain.doFilter(request, response);
    }
}
