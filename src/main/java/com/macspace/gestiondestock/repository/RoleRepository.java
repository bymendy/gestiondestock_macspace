package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Role;
import com.macspace.gestiondestock.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour gérer les entités {@link Role}.
 * Fournit les opérations CRUD et des requêtes
 * personnalisées sur la table 'roles'.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Trouve tous les rôles d'un utilisateur.
     *
     * @param idUtilisateur L'identifiant de l'utilisateur.
     * @return La liste des rôles de l'utilisateur.
     */
    List<Role> findAllByUtilisateurId(Integer idUtilisateur);

    /**
     * Trouve tous les rôles par type.
     *
     * @param roleName Le type de rôle.
     * @return La liste des rôles correspondants.
     */
    List<Role> findAllByRoleName(RoleType roleName);
}