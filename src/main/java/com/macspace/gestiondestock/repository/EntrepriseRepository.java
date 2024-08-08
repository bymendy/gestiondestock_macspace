package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pour gérer les entités {@link Entreprise}.
 * <p>
 * Cette interface hérite de {@link JpaRepository} et fournit des méthodes pour effectuer des opérations CRUD sur la table Entreprise.
 * </p>
 */
public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {
    // Aucun ajout de méthode personnalisé pour le moment.
}
