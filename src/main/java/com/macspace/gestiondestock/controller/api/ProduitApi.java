package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.ProduitDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(APP_ROOT + "/produits")
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
    @ApiOperation(value = "Enregistrer un produit ", notes = "Cette methode permet d'enregistrer ou modifier un produit", response = ProduitDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet produit céer / modifier"),
            @ApiResponse(code = 400, message = "L'objet produit n'est pas valide")

    })
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
    @ApiOperation(value = "Rechercher un produit par ID", notes = "Cette methode permet de chercher un produit par son ID", response = ProduitDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le produit a ete trouver dans la base de donnée "),
            @ApiResponse(code = 400, message = "Aucun produit n'existe dans la base de donnée avec l'ID fournie")

    })
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
    @ApiOperation(value = "Rechercher un produit par CODE", notes = "Cette methode permet de chercher un produit par son CODE", response = ProduitDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le produit a ete trouver dans la base de donnée "),
            @ApiResponse(code = 400, message = "Aucun produit n'existe dans la base de donnée avec le CODE fournie")

    })
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
    @ApiOperation(value = "Renvoie la liste des produits", notes = "Cette methode renvoie la liste des produits qui existent " + "dans la base de donnée", responseContainer = "List<ProduitDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le produit a ete trouver dans la base de donnée "),
    })
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
    @ApiOperation(value = "Supprimer un produit", notes = "Cette methode permet de supprimer un produit par ID", response = ProduitDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le produit a ete supprimer "),
    })
    void delete(@PathVariable("idProduit") Integer id);
}
