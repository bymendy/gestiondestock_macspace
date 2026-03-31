package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.InterventionDto;
import com.macspace.gestiondestock.model.EtatIntervention;
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

import static com.macspace.gestiondestock.utils.Constants.INTERVENTIONS_ENDPOINT;

/**
 * Interface définissant les endpoints de gestion des interventions dans MacSpace.
 */
@Tag(name = "Interventions",
        description = "API de gestion des interventions techniques")
public interface InterventionApi {

    /**
     * Enregistre une nouvelle intervention.
     *
     * @param dto Le DTO de l'intervention.
     * @return Le DTO de l'intervention enregistrée.
     */
    @Operation(summary = "Enregistrer une intervention",
            description = "Permet de créer ou modifier une intervention")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Intervention créée ou modifiée"),
            @ApiResponse(responseCode = "400",
                    description = "Données invalides")
    })
    @PostMapping(value = INTERVENTIONS_ENDPOINT + "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    InterventionDto save(@RequestBody InterventionDto dto);

    /**
     * Recherche une intervention par son identifiant.
     *
     * @param id L'identifiant de l'intervention.
     * @return Le DTO de l'intervention trouvée.
     */
    @Operation(summary = "Rechercher une intervention par ID",
            description = "Permet de chercher une intervention par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Intervention trouvée"),
            @ApiResponse(responseCode = "404",
                    description = "Intervention non trouvée")
    })
    @GetMapping(value = INTERVENTIONS_ENDPOINT + "/{idIntervention}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    InterventionDto findById(
            @PathVariable("idIntervention") Integer id);

    /**
     * Recherche une intervention par son code.
     *
     * @param code Le code de l'intervention.
     * @return Le DTO de l'intervention trouvée.
     */
    @Operation(summary = "Rechercher une intervention par code",
            description = "Permet de chercher une intervention par son code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Intervention trouvée"),
            @ApiResponse(responseCode = "404",
                    description = "Intervention non trouvée")
    })
    @GetMapping(value = INTERVENTIONS_ENDPOINT + "/filter/{codeIntervention}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    InterventionDto findByCode(
            @PathVariable("codeIntervention") String code);

    /**
     * Récupère toutes les interventions.
     *
     * @return La liste de toutes les interventions.
     */
    @Operation(summary = "Lister toutes les interventions",
            description = "Retourne la liste de toutes les interventions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des interventions ou liste vide")
    })
    @GetMapping(value = INTERVENTIONS_ENDPOINT + "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<InterventionDto> findAll();

    /**
     * Récupère toutes les interventions par état.
     *
     * @param etatIntervention L'état de l'intervention.
     * @return La liste des interventions correspondantes.
     */
    @Operation(summary = "Lister les interventions par état",
            description = "Retourne la liste des interventions selon leur état")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des interventions ou liste vide")
    })
    @GetMapping(value = INTERVENTIONS_ENDPOINT + "/all/etat/{etatIntervention}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<InterventionDto> findAllByEtatIntervention(
            @PathVariable("etatIntervention") EtatIntervention etatIntervention);

    /**
     * Récupère toutes les interventions d'un technicien.
     *
     * @param idTechnicien L'identifiant du technicien.
     * @return La liste des interventions du technicien.
     */
    @Operation(summary = "Lister les interventions d'un technicien",
            description = "Retourne la liste des interventions d'un technicien")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des interventions ou liste vide")
    })
    @GetMapping(value = INTERVENTIONS_ENDPOINT
            + "/all/technicien/{idTechnicien}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<InterventionDto> findAllByTechnicien(
            @PathVariable("idTechnicien") Integer idTechnicien);

    /**
     * Récupère toutes les interventions d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des interventions de l'entreprise.
     */
    @Operation(summary = "Lister les interventions d'une entreprise",
            description = "Retourne la liste des interventions d'une entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des interventions ou liste vide")
    })
    @GetMapping(value = INTERVENTIONS_ENDPOINT
            + "/all/entreprise/{idEntreprise}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<InterventionDto> findAllByIdEntreprise(
            @PathVariable("idEntreprise") Integer idEntreprise);

    /**
     * Supprime une intervention par son identifiant.
     *
     * @param id L'identifiant de l'intervention à supprimer.
     */
    @Operation(summary = "Supprimer une intervention",
            description = "Permet de supprimer une intervention par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Intervention supprimée"),
            @ApiResponse(responseCode = "404",
                    description = "Intervention non trouvée"),
            @ApiResponse(responseCode = "409",
                    description = "Intervention liée à des lignes")
    })
    @DeleteMapping(INTERVENTIONS_ENDPOINT + "/delete/{idIntervention}")
    void delete(@PathVariable("idIntervention") Integer id);
}