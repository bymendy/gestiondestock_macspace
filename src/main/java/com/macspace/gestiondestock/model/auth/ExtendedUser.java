package com.macspace.gestiondestock.model.auth;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Classe représentant un utilisateur étendu dans le système d'authentification.
 * <p>
 * Cette classe étend la classe {@link User} de Spring Security pour inclure
 * un identifiant d'entreprise {@code idEntreprise}. Cela permet de lier
 * un utilisateur à une entreprise spécifique tout en conservant les propriétés
 * d'authentification et d'autorisation fournies par Spring Security.
 * </p>
 */
public class ExtendedUser extends User {

    /**
     * Identifiant de l'entreprise associée à l'utilisateur.
     */
    @Getter
    @Setter
    private Integer idEntreprise;

    /**
     * Constructeur de base pour créer un utilisateur étendu sans l'ID d'entreprise.
     *
     * @param username    le nom d'utilisateur (ou email)
     * @param password    le mot de passe de l'utilisateur
     * @param authorities les autorisations de l'utilisateur
     */
    public ExtendedUser(String username, String password,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    /**
     * Constructeur pour créer un utilisateur étendu avec un ID d'entreprise.
     *
     * @param username    le nom d'utilisateur (ou email)
     * @param password    le mot de passe de l'utilisateur
     * @param idEntreprise l'identifiant de l'entreprise associée à l'utilisateur
     * @param authorities les autorisations de l'utilisateur
     */
    public ExtendedUser(String username, String password, Integer idEntreprise,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.idEntreprise = idEntreprise;
    }
}
