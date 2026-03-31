package com.macspace.gestiondestock.utils;

import com.macspace.gestiondestock.model.auth.ExtendedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service utilitaire pour la gestion des tokens JWT dans MacSpace.
 * Génère, extrait et valide les tokens JWT pour l'authentification.
 * Compatible avec jjwt 0.12.x.
 */
@Service
public class JwtUtil {

    /**
     * Clé secrète pour signer les tokens JWT.
     */
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    /**
     * Durée d'expiration du token en millisecondes.
     */
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    /**
     * Extrait le nom d'utilisateur (email) du token JWT.
     *
     * @param token Le token JWT.
     * @return L'email de l'utilisateur.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait la date d'expiration du token JWT.
     *
     * @param token Le token JWT.
     * @return La date d'expiration.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrait l'identifiant de l'entreprise du token JWT.
     *
     * @param token Le token JWT.
     * @return L'identifiant de l'entreprise.
     */
    public Integer extractIdEntreprise(String token) {
        final Claims claims = extractAllClaims(token);
        return Integer.parseInt(
                claims.get("idEntreprise", String.class));
    }

    /**
     * Extrait une claim spécifique du token JWT.
     *
     * @param <T>            Le type de la claim.
     * @param token          Le token JWT.
     * @param claimsResolver La fonction d'extraction.
     * @return La claim extraite.
     */
    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrait toutes les claims du token JWT.
     * Utilise l'API jjwt 0.12.x.
     *
     * @param token Le token JWT.
     * @return Toutes les claims.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Vérifie si le token JWT est expiré.
     *
     * @param token Le token JWT.
     * @return true si expiré, false sinon.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Génère un token JWT pour un utilisateur.
     *
     * @param userDetails Les détails de l'utilisateur.
     * @return Le token JWT généré.
     */
    public String generateToken(ExtendedUser userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    /**
     * Crée un token JWT avec les claims et les détails utilisateur.
     * Utilise l'API jjwt 0.12.x.
     *
     * @param claims      Les claims à inclure.
     * @param userDetails Les détails de l'utilisateur.
     * @return Le token JWT.
     */
    private String createToken(Map<String, Object> claims,
                               ExtendedUser userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(
                        System.currentTimeMillis() + jwtExpiration))
                .claim("idEntreprise",
                        userDetails.getIdEntreprise() != null
                                ? userDetails.getIdEntreprise().toString()
                                : "0")
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Valide le token JWT pour un utilisateur.
     *
     * @param token       Le token JWT à valider.
     * @param userDetails Les détails de l'utilisateur.
     * @return true si valide, false sinon.
     */
    public Boolean validateToken(String token,
                                 UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    /**
     * Retourne la clé de signature SecretKey.
     * Compatible jjwt 0.12.x.
     *
     * @return La clé de signature.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}