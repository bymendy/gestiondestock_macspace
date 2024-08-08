package com.macspace.gestiondestock.services.auth;

import java.util.ArrayList;
import java.util.List;

import com.macspace.gestiondestock.dto.UtilisateurDto;
import com.macspace.gestiondestock.model.auth.ExtendedUser;
import com.macspace.gestiondestock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service d'authentification qui charge les détails de l'utilisateur à partir de la base de données.
 * <p>
 * Cette classe implémente l'interface {@link UserDetailsService} de Spring Security, permettant
 * à l'application de récupérer les informations d'authentification d'un utilisateur en fonction de son email.
 * </p>
 */
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    @Lazy
    private UtilisateurService service;

    /**
     * Charge les détails de l'utilisateur en fonction de l'email fourni.
     * <p>
     * Cette méthode est utilisée par Spring Security pour authentifier un utilisateur.
     * Elle récupère l'utilisateur depuis la base de données via {@link UtilisateurService},
     * et convertit ses rôles en objets {@link SimpleGrantedAuthority} pour l'authentification.
     * </p>
     *
     * @param email l'email de l'utilisateur à authentifier
     * @return les détails de l'utilisateur sous la forme d'un objet {@link UserDetails}
     * @throws UsernameNotFoundException si l'utilisateur avec l'email donné n'est pas trouvé
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UtilisateurDto utilisateur = service.findByEmail(email);

        // Création de la liste des autorités (rôles) pour l'utilisateur
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        utilisateur.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));

        // Retourne un objet ExtendedUser contenant les détails de l'utilisateur pour l'authentification
        return new ExtendedUser(utilisateur.getEmail(), utilisateur.getPassword(), utilisateur.getEntreprise().getId(), authorities);
    }
}
