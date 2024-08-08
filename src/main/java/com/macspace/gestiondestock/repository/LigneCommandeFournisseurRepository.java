package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface de repository pour gérer les entités {@link LigneCommandeFournisseur}.
 * Fournit des méthodes pour effectuer des opérations CRUD et des requêtes personnalisées sur la table LigneCommandeFournisseur.
 */
public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur, Integer> {

    /**
     * Trouve toutes les entités {@link LigneCommandeFournisseur} associées à un ID de commande fournisseur spécifique.
     *
     * @param idCommande l'ID de la commande fournisseur.
     * @return une liste d'entités {@link LigneCommandeFournisseur} associées à l'ID de commande fournisseur spécifié.
     */
    List<LigneCommandeFournisseur> findAllByCommandeFournisseurId(Integer idCommande);

    /**
     * Trouve toutes les entités {@link LigneCommandeFournisseur} associées à un ID de produit spécifique.
     * <p>
     * Remarque : Le nom du paramètre est `idCommande`, ce qui semble incorrect. Il devrait être renommé en `idProduit`.
     * </p>
     *
     * @param idCommande l'ID du produit (devrait être renommé en idProduit).
     * @return une liste d'entités {@link LigneCommandeFournisseur} associées à l'ID de produit spécifié.
     */
    List<LigneCommandeFournisseur> findAllByProduitId(Integer idCommande);
}
