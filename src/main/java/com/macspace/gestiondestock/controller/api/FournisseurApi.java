package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.FournisseurDto;
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

import static com.macspace.gestiondestock.utils.Constants.FOURNISSEUR_ENDPOINT;

/**
 * Interface définissant les endpoints de gestion des fournisseurs dans MacSpace.
 */
@Tag(name = "Fournisseurs",
        description = "API de gestion des fournisseurs")
public interface FournisseurApi {

    /**
     * Enregistre ou met à jour un fournisseur.
     *
     * @param dto Le DTO du fournisseur à enregistrer.
     * @return Le DTO du fournisseur enregistré.
     */
    @Operation(summary = "Enregistrer un fournisseur",
            description = "Permet de créer ou modifier un fournisseur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fournisseur créé ou modifié"),
            @ApiResponse(responseCode = "400",
                    description = "Données invalides")
    })
    @PostMapping(value = FOURNISSEUR_ENDPOINT + "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    FournisseurDto save(@RequestBody FournisseurDto dto);

    /**
     * Recherche un fournisseur par son identifiant.
     *
     * @param id L'identifiant du fournisseur.
     * @return Le DTO du fournisseur trouvé.
     */
    @Operation(summary = "Rechercher un fournisseur par ID",
            description = "Permet de chercher un fournisseur par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fournisseur trouvé"),
            @ApiResponse(responseCode = "404",
                    description = "Fournisseur non trouvé")
    })
    @GetMapping(value = FOURNISSEUR_ENDPOINT + "/{idFournisseur}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);

    /**
     * Recherche un fournisseur par son email.
     *
     * @param email L'email du fournisseur.
     * @return Le DTO du fournisseur trouvé.
     */
    @Operation(summary = "Rechercher un fournisseur par email",
            description = "Permet de chercher un fournisseur par son email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fournisseur trouvé"),
            @ApiResponse(responseCode = "404",
                    description = "Fournisseur non trouvé")
    })
    @GetMapping(value = FOURNISSEUR_ENDPOINT + "/filter/email/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    FournisseurDto findByEmail(@PathVariable("email") String email);

    /**
     * Recherche tous les fournisseurs par nom.
     *
     * @param nom Le nom du fournisseur.
     * @return La liste des fournisseurs correspondants.
     */
    @Operation(summary = "Rechercher des fournisseurs par nom",
            description = "Retourne la liste des fournisseurs correspondants au nom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des fournisseurs ou liste vide")
    })
    @GetMapping(value = FOURNISSEUR_ENDPOINT + "/filter/nom/{nom}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<FournisseurDto> findAllByNom(@PathVariable("nom") String nom);

    /**
     * Récupère tous les fournisseurs.
     *
     * @return La liste de tous les fournisseurs.
     */
    @Operation(summary = "Lister tous les fournisseurs",
            description = "Retourne la liste de tous les fournisseurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des fournisseurs ou liste vide")
    })
    @GetMapping(value = FOURNISSEUR_ENDPOINT + "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<FournisseurDto> findAll();

    /**
     * Récupère tous les fournisseurs d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des fournisseurs de l'entreprise.
     */
    @Operation(summary = "Lister les fournisseurs d'une entreprise",
            description = "Retourne la liste des fournisseurs d'une entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des fournisseurs ou liste vide")
    })
    @GetMapping(value = FOURNISSEUR_ENDPOINT + "/all/{idEntreprise}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<FournisseurDto> findAllByIdEntreprise(
            @PathVariable("idEntreprise") Integer idEntreprise);

    /**
     * Supprime un fournisseur par son identifiant.
     *
     * @param id L'identifiant du fournisseur à supprimer.
     */
    @Operation(summary = "Supprimer un fournisseur",
            description = "Permet de supprimer un fournisseur par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fournisseur supprimé"),
            @ApiResponse(responseCode = "404",
                    description = "Fournisseur non trouvé"),
            @ApiResponse(responseCode = "409",
                    description = "Fournisseur lié à des commandes")
    })
    @DeleteMapping(FOURNISSEUR_ENDPOINT + "/delete/{idFournisseur}")
    void delete(@PathVariable("idFournisseur") Integer id);
}