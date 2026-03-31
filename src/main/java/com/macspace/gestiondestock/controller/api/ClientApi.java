package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.ClientDto;
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
 * Interface définissant les endpoints de gestion des clients dans MacSpace.
 */
@Tag(name = "Clients",
        description = "API de gestion des clients")
public interface ClientApi {

    /**
     * Enregistre ou met à jour un client.
     *
     * @param dto Le DTO du client à enregistrer.
     * @return Le DTO du client enregistré.
     */
    @Operation(
            summary = "Enregistrer un client",
            description = "Permet d'enregistrer ou modifier un client"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Client créé ou modifié avec succès"),
            @ApiResponse(responseCode = "400",
                    description = "Données du client invalides")
    })
    @PostMapping(value = APP_ROOT + "/clients/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto save(@RequestBody ClientDto dto);

    /**
     * Recherche un client par son identifiant.
     *
     * @param id L'identifiant du client.
     * @return Le DTO du client trouvé.
     */
    @Operation(
            summary = "Rechercher un client par ID",
            description = "Permet de chercher un client par son identifiant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Client trouvé"),
            @ApiResponse(responseCode = "404",
                    description = "Aucun client trouvé avec cet ID")
    })
    @GetMapping(value = APP_ROOT + "/clients/{idClient}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto findById(@PathVariable("idClient") Integer id);

    /**
     * Recherche un client par son email.
     *
     * @param email L'email du client.
     * @return Le DTO du client trouvé.
     */
    @Operation(
            summary = "Rechercher un client par email",
            description = "Permet de chercher un client par son adresse email"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Client trouvé"),
            @ApiResponse(responseCode = "404",
                    description = "Aucun client trouvé avec cet email")
    })
    @GetMapping(value = APP_ROOT + "/clients/filter/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto findByEmail(@PathVariable("email") String email);

    /**
     * Récupère tous les clients.
     *
     * @return La liste de tous les clients.
     */
    @Operation(
            summary = "Lister tous les clients",
            description = "Retourne la liste de tous les clients"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des clients ou liste vide")
    })
    @GetMapping(value = APP_ROOT + "/clients/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<ClientDto> findAll();

    /**
     * Récupère tous les clients d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des clients de l'entreprise.
     */
    @Operation(
            summary = "Lister les clients d'une entreprise",
            description = "Retourne la liste des clients d'une entreprise"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des clients ou liste vide")
    })
    @GetMapping(value = APP_ROOT + "/clients/all/{idEntreprise}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<ClientDto> findAllByIdEntreprise(
            @PathVariable("idEntreprise") Integer idEntreprise);

    /**
     * Supprime un client par son identifiant.
     *
     * @param id L'identifiant du client à supprimer.
     */
    @Operation(
            summary = "Supprimer un client",
            description = "Permet de supprimer un client par son identifiant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Client supprimé avec succès"),
            @ApiResponse(responseCode = "404",
                    description = "Client non trouvé"),
            @ApiResponse(responseCode = "409",
                    description = "Client lié à des interventions")
    })
    @DeleteMapping(value = APP_ROOT + "/clients/delete/{idClient}")
    void delete(@PathVariable("idClient") Integer id);
}