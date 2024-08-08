package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Interface repository pour l'entité {@link Utilisateur}.
 * Fournit des méthodes pour interagir avec la base de données et gérer les utilisateurs.
 * <p>
 * Hérite de {@link JpaRepository}, ce qui permet d'utiliser toutes les méthodes CRUD par défaut.
 * Inclut également une méthode personnalisée pour rechercher un utilisateur par email.
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    /**
     * Recherche un utilisateur par son adresse email.
     * Utilise une requête JPQL pour sélectionner l'utilisateur ayant l'email spécifié.
     *
     * @param email l'email de l'utilisateur à rechercher
     * @return un {@link Optional} contenant l'utilisateur s'il est trouvé, ou vide sinon
     */
    @Query(value = "select u from Utilisateur u where u.email = :email")
    Optional<Utilisateur> findUtilisateurByEmail(@Param("email") String email);
}
