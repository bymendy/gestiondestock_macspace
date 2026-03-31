package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneInterventionClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour gérer les entités {@link LigneInterventionClient}.
 * Fournit les opérations CRUD et des requêtes
 * personnalisées sur la table 'ligneinterventionclient'.
 */
public interface LigneInterventionClientRepository extends JpaRepository<LigneInterventionClient, Integer> {

    /**
     * Trouve toutes les lignes d'une intervention client.
     *
     * @param idInterventionClient L'identifiant de l'intervention client.
     * @return La liste des lignes de l'intervention client.
     */
    List<LigneInterventionClient> findAllByInterventionClientId(Integer idInterventionClient);

    /**
     * Trouve toutes les lignes d'intervention client d'un produit.
     *
     * @param idProduit L'identifiant du produit.
     * @return La liste des lignes d'intervention du produit.
     */
    List<LigneInterventionClient> findAllByProduitId(Integer idProduit);

    /**
     * Trouve toutes les lignes d'intervention client d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des lignes de l'entreprise.
     */
    List<LigneInterventionClient> findAllByIdEntreprise(Integer idEntreprise);
}