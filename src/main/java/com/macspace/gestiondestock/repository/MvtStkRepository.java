package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.MvtStk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository pour l'entité {@link MvtStk}.
 * <p>
 * Cette interface permet d'effectuer des opérations CRUD (Créer, Lire, Mettre à jour, Supprimer)
 * sur les entités {@link MvtStk}. Elle étend l'interface {@link JpaRepository}, ce qui fournit
 * des méthodes prédéfinies pour interagir avec la base de données.
 * </p>
 *
 */
public interface MvtStkRepository extends JpaRepository<MvtStk, Integer> {

    /**
     * Calcule le stock réel d'un produit en fonction de son identifiant.
     * <p>
     * Cette méthode utilise une requête JPQL pour sommer toutes les quantités des mouvements
     * de stock associés à un produit donné, identifiés par son ID.
     * </p>
     *
     * @param idProduit l'identifiant du produit pour lequel calculer le stock réel
     * @return un {@link BigDecimal} représentant le stock réel du produit
     */
    @Query("select sum(m.quantite) from MvtStk m where m.produit.id = :idProduit")
    BigDecimal stockReelProduit(@Param("idProduit") Integer idProduit);

    /**
     * Trouve tous les mouvements de stock associés à un produit donné.
     * <p>
     * Cette méthode retourne une liste de {@link MvtStk} pour tous les mouvements de stock
     * associés à un produit identifié par son ID.
     * </p>
     *
     * @param idArticle l'identifiant du produit pour lequel trouver les mouvements de stock
     * @return une liste de {@link MvtStk} associés au produit
     */
    List<MvtStk> findAllByProduitId(Integer idArticle);
}
