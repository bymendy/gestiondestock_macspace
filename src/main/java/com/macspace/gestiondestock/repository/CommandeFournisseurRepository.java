package com.macspace.gestiondestock.repository;

import java.util.List;
import java.util.Optional;

import com.macspace.gestiondestock.model.CommandeFournisseur;
import com.macspace.gestiondestock.model.InterventionClient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pour gérer les entités {@link CommandeFournisseur}.
 * <p>
 * Cette interface hérite de {@link JpaRepository} et fournit des méthodes pour effectuer des opérations CRUD sur la table CommandeFournisseur.
 * </p>
 */
public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Integer> {

    /**
     * Recherche une commande fournisseur par son code.
     *
     * @param code le code de la commande fournisseur
     * @return une {@link Optional} contenant la commande fournisseur si trouvée, sinon vide
     */
    Optional<CommandeFournisseur> findCommandeFournisseurByCode(String code);

    /**
     * Trouve toutes les interventions clients associées à un fournisseur donné.
     *
     * @param id l'identifiant du fournisseur
     * @return une liste d'objets {@link InterventionClient} associés au fournisseur spécifié
     */
    List<InterventionClient> findAllByFournisseurId(Integer id);
}
