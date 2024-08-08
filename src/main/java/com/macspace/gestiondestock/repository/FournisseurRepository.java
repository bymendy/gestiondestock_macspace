package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pour gérer les entités {@link Fournisseur}.
 * <p>
 * Cette interface hérite de {@link JpaRepository} et fournit des méthodes pour effectuer des opérations CRUD sur la table Fournisseur.
 * </p>
 */
public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {
    // Aucun ajout de méthode personnalisé pour le moment.
}
