package com.macspace.gestiondestock.model.auth;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Utilisateur étendu pour l'authentification MacSpace.
 * <p>
 * Étend {@link User} de Spring Security pour inclure
 * l'identifiant de l'entreprise associée à l'utilisateur.
 * </p>
 */
public class ExtendedUser extends User {

    private static final long serialVersionUID = 1L;

    /**
     * Identifiant de l'entreprise associée à l'utilisateur.
     */
    @Getter @Setter
    private Integer idEntreprise;

    /**
     * Constructeur sans ID d'entreprise.
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
     * Constructeur avec ID d'entreprise.
     *
     * @param username     le nom d'utilisateur (ou email)
     * @param password     le mot de passe de l'utilisateur
     * @param idEntreprise l'identifiant de l'entreprise associée
     * @param authorities  les autorisations de l'utilisateur
     */
    public ExtendedUser(String username, String password, Integer idEntreprise,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.idEntreprise = idEntreprise;
    }
}