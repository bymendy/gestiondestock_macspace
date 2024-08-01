package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.ProduitDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.macspace.gestiondestock.utils.Constants.APP_ROOT;

/**
 * Interface pour les opérations CRUD sur les produits.
 * <p>
 * Cette interface définit les points d'entrée de l'API REST pour la gestion des produits.
 * </p>
 */
public interface ProduitApi {

    /**
     * Enregistre un nouveau produit ou met à jour un produit existant.
     * <p>
     * Cette méthode permet de créer ou de mettre à jour un produit dans la base de données.
     * </p>
     *
     * @param dto Le produit à enregistrer ou à mettre à jour.
     * @return Le produit enregistré ou mis à jour.
     */
    @PostMapping(value = APP_ROOT + "/produits/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ProduitDto save(@RequestBody ProduitDto dto);

    /**
     * Recherche un produit par son identifiant unique.
     * <p>
     * Cette méthode permet de récupérer un produit à partir de son identifiant.
     * </p>
     *
     * @param id L'identifiant unique du produit.
     * @return Le produit correspondant à l'identifiant, ou null si aucun produit n'est trouvé.
     */
    @GetMapping(value = APP_ROOT + "/produits/{idProduit}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProduitDto findById(@PathVariable("idProduit") Integer id);

    /**
     * Recherche un produit par son code unique.
     * <p>
     * Cette méthode permet de récupérer un produit à partir de son code unique.
     * </p>
     *
     * @param codeProduit Le code unique du produit.
     * @return Le produit correspondant au code, ou null si aucun produit n'est trouvé.
     */
    @GetMapping(value = APP_ROOT + "/produits/code/{codeProduit}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProduitDto findByCodeProduit(@PathVariable("codeProduit") String codeProduit);

    /**
     * Récupère la liste de tous les produits.
     * <p>
     * Cette méthode permet de récupérer une liste de tous les produits disponibles.
     * </p>
     *
     * @return La liste de tous les produits.
     */
    @GetMapping(value = APP_ROOT + "/produits/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProduitDto> findAll();

    /**
     * Supprime un produit par son identifiant unique.
     * <p>
     * Cette méthode permet de supprimer un produit à partir de son identifiant.
     * </p>
     *
     * @param id L'identifiant unique du produit à supprimer.
     */
    @DeleteMapping(value = APP_ROOT + "/produits/delete/{idProduit}")
    void delete(@PathVariable("idProduit") Integer id);
}
