package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.CategoryDto;
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
 * Interface définissant les endpoints de gestion des catégories dans MacSpace.
 */
@Tag(name = "Catégories",
        description = "API de gestion des catégories de produits")
public interface CategoryApi {

    /**
     * Enregistre ou met à jour une catégorie.
     *
     * @param dto Le DTO de la catégorie à enregistrer.
     * @return Le DTO de la catégorie enregistrée.
     */
    @Operation(
            summary = "Enregistrer une catégorie",
            description = "Permet d'enregistrer ou modifier une catégorie"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Catégorie créée ou modifiée avec succès"),
            @ApiResponse(responseCode = "400",
                    description = "Données de la catégorie invalides")
    })
    @PostMapping(value = APP_ROOT + "/categories/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    CategoryDto save(@RequestBody CategoryDto dto);

    /**
     * Recherche une catégorie par son identifiant.
     *
     * @param idCategory L'identifiant de la catégorie.
     * @return Le DTO de la catégorie trouvée.
     */
    @Operation(
            summary = "Rechercher une catégorie par ID",
            description = "Permet de chercher une catégorie par son identifiant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Catégorie trouvée"),
            @ApiResponse(responseCode = "404",
                    description = "Aucune catégorie trouvée avec cet ID")
    })
    @GetMapping(value = APP_ROOT + "/categories/{idCategory}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    CategoryDto findById(@PathVariable("idCategory") Integer idCategory);

    /**
     * Recherche une catégorie par son code.
     *
     * @param codeCategory Le code de la catégorie.
     * @return Le DTO de la catégorie trouvée.
     */
    @Operation(
            summary = "Rechercher une catégorie par code",
            description = "Permet de chercher une catégorie par son code"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Catégorie trouvée"),
            @ApiResponse(responseCode = "404",
                    description = "Aucune catégorie trouvée avec ce code")
    })
    @GetMapping(value = APP_ROOT + "/categories/filter/{codeCategory}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    CategoryDto findByCode(
            @PathVariable("codeCategory") String codeCategory);

    /**
     * Récupère toutes les catégories.
     *
     * @return La liste de toutes les catégories.
     */
    @Operation(
            summary = "Lister toutes les catégories",
            description = "Retourne la liste de toutes les catégories"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des catégories ou liste vide")
    })
    @GetMapping(value = APP_ROOT + "/categories/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<CategoryDto> findAll();

    /**
     * Récupère toutes les catégories d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des catégories de l'entreprise.
     */
    @Operation(
            summary = "Lister les catégories d'une entreprise",
            description = "Retourne la liste des catégories d'une entreprise"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des catégories ou liste vide")
    })
    @GetMapping(value = APP_ROOT + "/categories/all/{idEntreprise}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<CategoryDto> findAllByIdEntreprise(
            @PathVariable("idEntreprise") Integer idEntreprise);

    /**
     * Supprime une catégorie par son identifiant.
     *
     * @param idCategory L'identifiant de la catégorie à supprimer.
     */
    @Operation(
            summary = "Supprimer une catégorie",
            description = "Permet de supprimer une catégorie par son identifiant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Catégorie supprimée avec succès"),
            @ApiResponse(responseCode = "404",
                    description = "Catégorie non trouvée"),
            @ApiResponse(responseCode = "409",
                    description = "Catégorie utilisée par des produits")
    })
    @DeleteMapping(value = APP_ROOT + "/categories/delete/{idCategory}")
    void delete(@PathVariable("idCategory") Integer idCategory);
}