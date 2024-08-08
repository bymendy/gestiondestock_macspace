package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pour l'entité {@link Roles}.
 * <p>
 * Cette interface permet d'effectuer des opérations CRUD (Créer, Lire, Mettre à jour, Supprimer)
 * sur les entités {@link Roles}. Elle étend l'interface {@link JpaRepository}, ce qui fournit
 * des méthodes prédéfinies pour interagir avec la base de données.
 * </p>
 *
 */
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    // Aucune méthode supplémentaire n'est définie ici.
    // Les méthodes de base pour les opérations CRUD sont héritées de JpaRepository.
}
