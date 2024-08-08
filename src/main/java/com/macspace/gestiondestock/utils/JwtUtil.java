package com.macspace.gestiondestock.utils;

import com.macspace.gestiondestock.model.auth.ExtendedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service utilitaire pour la gestion des tokens JWT dans l'application.
 * <p>
 * Ce service permet de générer, extraire, et valider les tokens JWT utilisés
 * pour l'authentification et l'autorisation des utilisateurs.
 * </p>
 */
@Service
public class JwtUtil {

    // Clé secrète pour signer les tokens JWT, doit être d'une longueur suffisante pour la sécurité.
    private static final String SECRET_KEY = "wzUpGa9k4LTV3QHuY8qVrt6wOENkvdes5vLHVc1ex6581IiQ";

    // Clé générée à partir de la clé secrète pour signer les JWT
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Extrait le nom d'utilisateur (subject) du token JWT.
     *
     * @param token le token JWT
     * @return le nom d'utilisateur contenu dans le token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait la date d'expiration du token JWT.
     *
     * @param token le token JWT
     * @return la date d'expiration du token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrait l'identifiant de l'entreprise du token JWT.
     *
     * @param token le token JWT
     * @return l'identifiant de l'entreprise contenu dans le token
     */
    public String extractIdEntreprise(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("idEntreprise", String.class);
    }

    /**
     * Extrait une réclamation spécifique du token JWT en utilisant la fonction fournie.
     *
     * @param <T>            le type de la réclamation
     * @param token          le token JWT
     * @param claimsResolver la fonction utilisée pour extraire la réclamation
     * @return la réclamation extraite
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrait toutes les réclamations (claims) du token JWT.
     *
     * @param token le token JWT
     * @return toutes les réclamations contenues dans le token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Vérifie si le token JWT est expiré.
     *
     * @param token le token JWT
     * @return true si le token est expiré, sinon false
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Génère un token JWT pour l'utilisateur fourni.
     *
     * @param userDetails les détails de l'utilisateur pour lequel le token est généré
     * @return le token JWT généré
     */
    public String generateToken(ExtendedUser userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    /**
     * Crée un token JWT avec les réclamations fournies et les détails de l'utilisateur.
     *
     * @param claims      les réclamations à inclure dans le token
     * @param userDetails les détails de l'utilisateur pour lequel le token est créé
     * @return le token JWT généré
     */
    private String createToken(Map<String, Object> claims, ExtendedUser userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 heures
                .claim("idEntreprise", userDetails.getIdEntreprise().toString())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valide le token JWT pour l'utilisateur fourni.
     *
     * @param token       le token JWT à valider
     * @param userDetails les détails de l'utilisateur à valider
     * @return true si le token est valide et correspond à l'utilisateur, sinon false
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
