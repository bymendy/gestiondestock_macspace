package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.MvtStk;
import com.macspace.gestiondestock.model.TypeMvtStk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository pour gérer les entités {@link MvtStk}.
 * Fournit les opérations CRUD et des requêtes
 * personnalisées sur la table 'mvtstk'.
 */
public interface MvtStkRepository extends JpaRepository<MvtStk, Integer> {

    /**
     * Calcule le stock réel d'un produit.
     * Retourne 0 si aucun mouvement n'existe.
     *
     * @param idProduit L'identifiant du produit.
     * @return Le stock réel du produit.
     */
    @Query("select coalesce(sum(m.quantite), 0) from MvtStk m where m.produit.id = :idProduit")
    BigDecimal stockReelProduit(@Param("idProduit") Integer idProduit);

    /**
     * Trouve tous les mouvements de stock d'un produit.
     *
     * @param idProduit L'identifiant du produit.
     * @return La liste des mouvements du produit.
     */
    List<MvtStk> findAllByProduitId(Integer idProduit);

    /**
     * Trouve tous les mouvements de stock par type.
     *
     * @param typeMvt Le type de mouvement.
     * @return La liste des mouvements du type spécifié.
     */
    List<MvtStk> findAllByTypeMvt(TypeMvtStk typeMvt);

    /**
     * Trouve tous les mouvements de stock d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des mouvements de l'entreprise.
     */
    List<MvtStk> findAllByIdEntreprise(Integer idEntreprise);
}