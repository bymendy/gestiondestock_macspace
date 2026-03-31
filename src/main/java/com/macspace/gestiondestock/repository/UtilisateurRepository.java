package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des utilisateurs dans MacSpace.
 * Fournit les opérations CRUD et des requêtes personnalisées.
 */
public interface UtilisateurRepository
        extends JpaRepository<Utilisateur, Integer> {

    /**
     * Trouve un utilisateur par son email.
     * Utilisé pour l'authentification JWT.
     *
     * @param email L'email de l'utilisateur.
     * @return L'utilisateur trouvé.
     */
    Optional<Utilisateur> findUtilisateurByEmail(String email);

    /**
     * Trouve un utilisateur par son email en chargeant ses rôles eagerly.
     * Utilisé par ApplicationUserDetailsService pour éviter le
     * LazyInitializationException hors session Hibernate.
     *
     * @param email L'email de l'utilisateur.
     * @return L'utilisateur trouvé avec ses rôles chargés.
     */
    @Query("SELECT u FROM Utilisateur u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<Utilisateur> findUtilisateurByEmailWithRoles(@Param("email") String email);

    /**
     * Trouve tous les utilisateurs d'une entreprise.
     * Via la relation @ManyToOne Entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des utilisateurs de l'entreprise.
     */
    List<Utilisateur> findAllByEntrepriseId(Integer idEntreprise);

    /**
     * Trouve tous les utilisateurs par fonction.
     *
     * @param fonction La fonction de l'utilisateur.
     * @return La liste des utilisateurs correspondants.
     */
    List<Utilisateur> findAllByFonction(String fonction);

    /**
     * Trouve tous les utilisateurs par idEntreprise.
     * Via le champ hérité de AbstractEntity.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des utilisateurs de l'entreprise.
     */
    List<Utilisateur> findAllByIdEntreprise(Integer idEntreprise);
}