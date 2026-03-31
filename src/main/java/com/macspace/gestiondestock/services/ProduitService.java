package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.ProduitDto;

import java.util.List;

/**
 * Interface de service pour la gestion des produits dans MacSpace.
 * Définit les opérations métier disponibles sur les produits de sécurité.
 * <p>
 * Note : JPA est la spécification de persistance,
 * Hibernate en est l'implémentation concrète.
 * </p>
 */
public interface ProduitService {

    /**
     * Enregistre ou met à jour un produit.
     *
     * @param dto Le DTO du produit à enregistrer.
     * @return Le DTO du produit enregistré.
     */
    ProduitDto save(ProduitDto dto);

    /**
     * Recherche un produit par son identifiant.
     *
     * @param id L'identifiant du produit.
     * @return Le DTO du produit trouvé.
     */
    ProduitDto findById(Integer id);

    /**
     * Recherche un produit par son code.
     *
     * @param codeProduit Le code du produit.
     * @return Le DTO du produit trouvé.
     */
    ProduitDto findByCodeProduit(String codeProduit);

    /**
     * Récupère tous les produits.
     *
     * @return La liste de tous les produits.
     */
    List<ProduitDto> findAll();

    /**
     * Récupère tous les produits d'une catégorie.
     *
     * @param idCategory L'identifiant de la catégorie.
     * @return La liste des produits de la catégorie.
     */
    List<ProduitDto> findAllByCategory(Integer idCategory);

    /**
     * Récupère tous les produits d'un fournisseur.
     *
     * @param idFournisseur L'identifiant du fournisseur.
     * @return La liste des produits du fournisseur.
     */
    List<ProduitDto> findAllByFournisseur(Integer idFournisseur);

    /**
     * Récupère tous les produits d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des produits de l'entreprise.
     */
    List<ProduitDto> findAllByIdEntreprise(Integer idEntreprise);

    /**
     * Supprime un produit par son identifiant.
     *
     * @param id L'identifiant du produit à supprimer.
     */
    void delete(Integer id);
}