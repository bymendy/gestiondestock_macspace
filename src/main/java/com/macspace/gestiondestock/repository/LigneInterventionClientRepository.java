package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneInterventionClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour l'entité {@link LigneInterventionClient}.
 * <p>
 * Cette interface fournit des méthodes pour interagir avec les entités {@link LigneInterventionClient},
 * permettant des opérations CRUD ainsi que des recherches spécifiques en fonction des critères donnés.
 * </p>
 *
 */
public interface LigneInterventionClientRepository extends JpaRepository<LigneInterventionClient, Integer> {

    /**
     * Trouve toutes les lignes d'intervention client associées à une intervention client spécifique.
     * <p>
     * Cette méthode retourne une liste de {@link LigneInterventionClient} pour toutes les lignes associées
     * à une intervention client identifiée par son ID.
     * </p>
     *
     * @param id l'identifiant de l'intervention client pour laquelle trouver les lignes d'intervention
     * @return une liste de {@link LigneInterventionClient} associées à l'intervention client
     */
    List<LigneInterventionClient> findAllByInterventionClientId(Integer id);

    /**
     * Trouve toutes les lignes d'intervention client associées à un produit spécifique.
     * <p>
     * Cette méthode retourne une liste de {@link LigneInterventionClient} pour toutes les lignes associées
     * à un produit identifié par son ID.
     * </p>
     *
     * @param id l'identifiant du produit pour lequel trouver les lignes d'intervention
     * @return une liste de {@link LigneInterventionClient} associées au produit
     */
    List<LigneInterventionClient> findAllByProduitId(Integer id);
}
