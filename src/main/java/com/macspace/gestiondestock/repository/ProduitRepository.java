package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour gérer les entités {@link Produit}.
 * Fournit les opérations CRUD et des requêtes
 * personnalisées sur la table 'produits'.
 */
public interface ProduitRepository extends JpaRepository<Produit, Integer> {

    /**
     * Trouve un produit par son code.
     *
     * @param codeProduit Le code du produit.
     * @return Une {@link Optional} contenant le produit si trouvé.
     */
    Optional<Produit> findProduitByCodeProduit(String codeProduit);

    /**
     * Trouve tous les produits d'une catégorie.
     *
     * @param idCategory L'identifiant de la catégorie.
     * @return La liste des produits de la catégorie.
     */
    List<Produit> findAllByCategoryId(Integer idCategory);

    /**
     * Trouve tous les produits d'un fournisseur.
     *
     * @param idFournisseur L'identifiant du fournisseur.
     * @return La liste des produits du fournisseur.
     */
    List<Produit> findAllByFournisseurId(Integer idFournisseur);

    /**
     * Trouve tous les produits d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des produits de l'entreprise.
     */
    List<Produit> findAllByIdEntreprise(Integer idEntreprise);
}