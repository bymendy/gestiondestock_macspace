package com.macspace.gestiondestock.services.auth;

import com.macspace.gestiondestock.model.Utilisateur;
import com.macspace.gestiondestock.model.auth.ExtendedUser;
import com.macspace.gestiondestock.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service d'authentification qui charge les détails de l'utilisateur
 * à partir de la base de données pour Spring Security.
 * Utilise findUtilisateurByEmailWithRoles() pour charger les rôles
 * eagerly et éviter le LazyInitializationException hors session Hibernate.
 */
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /**
     * Charge les détails de l'utilisateur en fonction de son email.
     * Utilisé par Spring Security pour l'authentification JWT.
     * Les rôles sont chargés eagerly via LEFT JOIN FETCH.
     *
     * @param email L'email de l'utilisateur à authentifier.
     * @return Les détails de l'utilisateur sous forme de {@link UserDetails}.
     * @throws UsernameNotFoundException Si aucun utilisateur n'est trouvé.
     */
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurRepository
                .findUtilisateurByEmailWithRoles(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Aucun utilisateur trouvé avec l'email : " + email
                ));

        // Création de la liste des autorités (rôles)
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (utilisateur.getRoles() != null) {
            utilisateur.getRoles().forEach(role ->
                    authorities.add(new SimpleGrantedAuthority(
                            role.getRoleName().name()
                    ))
            );
        }

        // Récupération de l'id entreprise
        Integer idEntreprise = utilisateur.getEntreprise() != null
                ? utilisateur.getEntreprise().getId()
                : null;

        return new ExtendedUser(
                utilisateur.getEmail(),
                utilisateur.getPassword(),
                idEntreprise,
                authorities
        );
    }
}