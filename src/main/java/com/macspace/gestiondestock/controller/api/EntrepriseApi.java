package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.EntrepriseDto;
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

import static com.macspace.gestiondestock.utils.Constants.ENTREPRISE_ENDPOINT;

/**
 * Interface définissant les endpoints de gestion des entreprises dans MacSpace.
 */
@Tag(name = "Entreprises",
        description = "API de gestion des entreprises")
public interface EntrepriseApi {

    /**
     * Enregistre ou met à jour une entreprise.
     *
     * @param dto Le DTO de l'entreprise à enregistrer.
     * @return Le DTO de l'entreprise enregistrée.
     */
    @Operation(summary = "Enregistrer une entreprise",
            description = "Permet de créer ou modifier une entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Entreprise créée ou modifiée"),
            @ApiResponse(responseCode = "400",
                    description = "Données invalides")
    })
    @PostMapping(value = ENTREPRISE_ENDPOINT + "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    EntrepriseDto save(@RequestBody EntrepriseDto dto);

    /**
     * Recherche une entreprise par son identifiant.
     *
     * @param id L'identifiant de l'entreprise.
     * @return Le DTO de l'entreprise trouvée.
     */
    @Operation(summary = "Rechercher une entreprise par ID",
            description = "Permet de chercher une entreprise par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entreprise trouvée"),
            @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    @GetMapping(value = ENTREPRISE_ENDPOINT + "/{idEntreprise}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);

    /**
     * Recherche une entreprise par son nom.
     *
     * @param nom Le nom de l'entreprise.
     * @return Le DTO de l'entreprise trouvée.
     */
    @Operation(summary = "Rechercher une entreprise par nom",
            description = "Permet de chercher une entreprise par son nom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entreprise trouvée"),
            @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    @GetMapping(value = ENTREPRISE_ENDPOINT + "/filter/nom/{nom}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    EntrepriseDto findByNom(@PathVariable("nom") String nom);

    /**
     * Recherche une entreprise par son email.
     *
     * @param email L'email de l'entreprise.
     * @return Le DTO de l'entreprise trouvée.
     */
    @Operation(summary = "Rechercher une entreprise par email",
            description = "Permet de chercher une entreprise par son email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entreprise trouvée"),
            @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    @GetMapping(value = ENTREPRISE_ENDPOINT + "/filter/email/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    EntrepriseDto findByEmail(@PathVariable("email") String email);

    /**
     * Recherche une entreprise par son code fiscal.
     *
     * @param codeFiscal Le code fiscal de l'entreprise.
     * @return Le DTO de l'entreprise trouvée.
     */
    @Operation(summary = "Rechercher une entreprise par code fiscal",
            description = "Permet de chercher une entreprise par son code fiscal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entreprise trouvée"),
            @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    @GetMapping(value = ENTREPRISE_ENDPOINT + "/filter/codefiscal/{codeFiscal}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    EntrepriseDto findByCodeFiscal(
            @PathVariable("codeFiscal") String codeFiscal);

    /**
     * Récupère toutes les entreprises.
     *
     * @return La liste de toutes les entreprises.
     */
    @Operation(summary = "Lister toutes les entreprises",
            description = "Retourne la liste de toutes les entreprises")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des entreprises ou liste vide")
    })
    @GetMapping(value = ENTREPRISE_ENDPOINT + "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<EntrepriseDto> findAll();

    /**
     * Supprime une entreprise par son identifiant.
     *
     * @param id L'identifiant de l'entreprise à supprimer.
     */
    @Operation(summary = "Supprimer une entreprise",
            description = "Permet de supprimer une entreprise par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Entreprise supprimée"),
            @ApiResponse(responseCode = "404",
                    description = "Entreprise non trouvée")
    })
    @DeleteMapping(ENTREPRISE_ENDPOINT + "/delete/{idEntreprise}")
    void delete(@PathVariable("idEntreprise") Integer id);
}