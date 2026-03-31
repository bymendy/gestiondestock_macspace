package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneIntervention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour gérer les entités {@link LigneIntervention}.
 * Fournit les opérations CRUD et des requêtes
 * personnalisées sur la table 'ligneintervention'.
 */
public interface LigneInterventionRepository extends JpaRepository<LigneIntervention, Integer> {

    /**
     * Trouve toutes les lignes d'intervention d'un produit.
     *
     * @param idProduit L'identifiant du produit.
     * @return La liste des lignes d'intervention du produit.
     */
    List<LigneIntervention> findAllByProduitId(Integer idProduit);

    /**
     * Trouve toutes les lignes d'une intervention.
     *
     * @param idIntervention L'identifiant de l'intervention.
     * @return La liste des lignes de l'intervention.
     */
    List<LigneIntervention> findAllByInterventionId(Integer idIntervention);

    /**
     * Trouve toutes les lignes d'intervention d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des lignes de l'entreprise.
     */
    List<LigneIntervention> findAllByIdEntreprise(Integer idEntreprise);
}