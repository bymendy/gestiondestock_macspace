package com.macspace.gestiondestock.services;


import com.macspace.gestiondestock.dto.ProduitDto;

import java.util.List;

// Une interface c'est un contrat de service, rien de particulier dans une interface
// seulement que la definition des methodes
//Jpa = c'est la specification
//Hibernate = c'est l'implementation

/**
 * Interface de service pour la gestion des produits.
 * <p>
 * Cette interface définit les méthodes CRUD (Create, Read, Update, Delete)
 * pour les entités {@link ProduitDto}.
 * Les interfaces de service agissent comme un contrat de service, définissant
 * les méthodes sans implémentation concrète.
 * <p>
 * JPA (Java Persistence API) est la spécification utilisée pour la gestion de la persistance des données,
 * tandis qu'Hibernate est l'implémentation concrète de cette spécification.
 * </p>
 */
public interface ProduitService {

    /**
     * Enregistre un nouveau produit ou met à jour un produit existant.
     *
     * @param dto Le produit à enregistrer ou à mettre à jour.
     * @return Le produit enregistré ou mis à jour.
     */
    ProduitDto save(ProduitDto dto);

    /**
     * Recherche un produit par son identifiant unique.
     *
     * @param id L'identifiant unique du produit.
     * @return Le produit correspondant à l'identifiant, ou null si aucun produit n'est trouvé.
     */
    ProduitDto findById(Integer id);

    /**
     * Recherche un produit par son code unique.
     *
     * @param codeProduit Le code unique du produit.
     * @return Le produit correspondant au code, ou null si aucun produit n'est trouvé.
     */
    ProduitDto findByCodeProduit(String codeProduit);

    /**
     * Récupère la liste de tous les produits.
     *
     * @return La liste de tous les produits.
     */
    List<ProduitDto> findAll();

    /**
     * Supprime un produit par son identifiant unique.
     *
     * @param id L'identifiant unique du produit à supprimer.
     */
    void delete(Integer id);

}