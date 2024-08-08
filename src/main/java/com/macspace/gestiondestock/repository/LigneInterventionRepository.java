package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneIntervention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour l'entité {@link LigneIntervention}.
 * <p>
 * Cette interface permet d'effectuer des opérations CRUD (Créer, Lire, Mettre à jour, Supprimer)
 * sur les entités {@link LigneIntervention}. Elle étend l'interface {@link JpaRepository}, ce qui fournit
 * des méthodes prédéfinies pour interagir avec la base de données.
 * </p>
 *
 */
public interface LigneInterventionRepository extends JpaRepository<LigneIntervention, Integer> {

    /**
     * Trouve toutes les lignes d'intervention associées à un produit donné.
     * <p>
     * Cette méthode retourne une liste de {@link LigneIntervention} pour toutes les lignes associées
     * à un produit identifié par son ID.
     * </p>
     *
     * @param idProduit l'identifiant du produit pour lequel trouver les lignes d'intervention
     * @return une liste de {@link LigneIntervention} associées au produit
     */
    List<LigneIntervention> findAllByProduitId(Integer idProduit);

    /**
     * Trouve toutes les lignes d'intervention associées à une intervention donnée.
     * <p>
     * Cette méthode retourne une liste de {@link LigneIntervention} pour toutes les lignes associées
     * à une intervention identifiée par son ID.
     * </p>
     *
     * @param id l'identifiant de l'intervention pour laquelle trouver les lignes d'intervention
     * @return une liste de {@link LigneIntervention} associées à l'intervention
     */
    List<LigneIntervention> findAllByInterventionsId(Integer id);
}
