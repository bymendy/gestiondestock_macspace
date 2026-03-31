package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.ProduitDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.macspace.gestiondestock.utils.Constants.APP_ROOT;

/**
 * Interface définissant les endpoints de gestion des produits dans MacSpace.
 */
@Tag(name = "Produits",
        description = "API de gestion des produits de sécurité")
public interface ProduitApi {

    /**
     * Enregistre ou met à jour un produit.
     *
     * @param dto Le DTO du produit à enregistrer.
     * @return Le DTO du produit enregistré.
     */
    @Operation(summary = "Enregistrer un produit",
            description = "Permet d'enregistrer ou modifier un produit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Produit créé ou modifié"),
            @ApiResponse(responseCode = "400",
                    description = "Données invalides")
    })
    @PostMapping(value = APP_ROOT + "/produits/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ProduitDto save(@RequestBody ProduitDto dto);

    /**
     * Recherche un produit par son identifiant.
     *
     * @param id L'identifiant du produit.
     * @return Le DTO du produit trouvé.
     */
    @Operation(summary = "Rechercher un produit par ID",
            description = "Permet de chercher un produit par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit trouvé"),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/produits/{idProduit}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ProduitDto findById(@PathVariable("idProduit") Integer id);

    /**
     * Recherche un produit par son code.
     *
     * @param codeProduit Le code du produit.
     * @return Le DTO du produit trouvé.
     */
    @Operation(summary = "Rechercher un produit par code",
            description = "Permet de chercher un produit par son code unique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit trouvé"),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    @GetMapping(value = APP_ROOT + "/produits/code/{codeProduit}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ProduitDto findByCodeProduit(
            @PathVariable("codeProduit") String codeProduit);

    /**
     * Récupère tous les produits.
     *
     * @return La liste de tous les produits.
     */
    @Operation(summary = "Lister tous les produits",
            description = "Retourne la liste de tous les produits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des produits ou liste vide")
    })
    @GetMapping(value = APP_ROOT + "/produits/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProduitDto> findAll();

    /**
     * Récupère tous les produits d'une catégorie.
     *
     * @param idCategory L'identifiant de la catégorie.
     * @return La liste des produits de la catégorie.
     */
    @Operation(summary = "Lister les produits d'une catégorie",
            description = "Retourne la liste des produits d'une catégorie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des produits ou liste vide")
    })
    @GetMapping(value = APP_ROOT + "/produits/all/category/{idCategory}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProduitDto> findAllByCategory(
            @PathVariable("idCategory") Integer idCategory);

    /**
     * Récupère tous les produits d'un fournisseur.
     *
     * @param idFournisseur L'identifiant du fournisseur.
     * @return La liste des produits du fournisseur.
     */
    @Operation(summary = "Lister les produits d'un fournisseur",
            description = "Retourne la liste des produits d'un fournisseur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des produits ou liste vide")
    })
    @GetMapping(value = APP_ROOT + "/produits/all/fournisseur/{idFournisseur}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProduitDto> findAllByFournisseur(
            @PathVariable("idFournisseur") Integer idFournisseur);

    /**
     * Récupère tous les produits d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des produits de l'entreprise.
     */
    @Operation(summary = "Lister les produits d'une entreprise",
            description = "Retourne la liste des produits d'une entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des produits ou liste vide")
    })
    @GetMapping(value = APP_ROOT + "/produits/all/entreprise/{idEntreprise}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<ProduitDto> findAllByIdEntreprise(
            @PathVariable("idEntreprise") Integer idEntreprise);

    /**
     * Supprime un produit par son identifiant.
     *
     * @param id L'identifiant du produit à supprimer.
     */
    @Operation(summary = "Supprimer un produit",
            description = "Permet de supprimer un produit par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Produit supprimé"),
            @ApiResponse(responseCode = "404",
                    description = "Produit non trouvé")
    })
    @DeleteMapping(value = APP_ROOT + "/produits/delete/{idProduit}")
    void delete(@PathVariable("idProduit") Integer id);
}