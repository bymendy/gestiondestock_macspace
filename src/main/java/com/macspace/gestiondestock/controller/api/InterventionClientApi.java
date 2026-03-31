package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.InterventionClientDto;
import com.macspace.gestiondestock.dto.LigneInterventionClientDto;
import com.macspace.gestiondestock.model.EtatIntervention;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

import static com.macspace.gestiondestock.utils.Constants.APP_ROOT;

/**
 * Interface définissant les endpoints de gestion des interventions clients dans MacSpace.
 */
@Tag(name = "Interventions Clients",
        description = "API de gestion des interventions clients")
public interface InterventionClientApi {

    /**
     * Enregistre une nouvelle intervention client.
     *
     * @param dto Le DTO de l'intervention client.
     * @return L'intervention client enregistrée.
     */
    @Operation(summary = "Enregistrer une intervention client",
            description = "Permet de créer ou modifier une intervention client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Intervention créée ou modifiée"),
            @ApiResponse(responseCode = "400",
                    description = "Données invalides")
    })
    @PostMapping(value = APP_ROOT + "/interventionsclients/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InterventionClientDto> save(
            @RequestBody InterventionClientDto dto);

    /**
     * Met à jour l'état d'une intervention client.
     *
     * @param idIntervention   L'identifiant de l'intervention.
     * @param etatIntervention Le nouvel état.
     * @return L'intervention mise à jour.
     */
    @Operation(summary = "Mettre à jour l'état d'une intervention",
            description = "Permet de modifier l'état d'une intervention client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "État mis à jour"),
            @ApiResponse(responseCode = "404",
                    description = "Intervention non trouvée"),
            @ApiResponse(responseCode = "400",
                    description = "Modification impossible")
    })
    @PatchMapping(APP_ROOT
            + "/interventionsclients/update/etat/{idIntervention}/{etatIntervention}")
    ResponseEntity<InterventionClientDto> updateEtatIntervention(
            @PathVariable("idIntervention") Integer idIntervention,
            @PathVariable("etatIntervention") EtatIntervention etatIntervention);

    /**
     * Met à jour la quantité d'une ligne d'intervention.
     *
     * @param idIntervention      L'identifiant de l'intervention.
     * @param idLigneIntervention L'identifiant de la ligne.
     * @param quantite            La nouvelle quantité.
     * @return L'intervention mise à jour.
     */
    @Operation(summary = "Mettre à jour la quantité d'une ligne",
            description = "Permet de modifier la quantité d'une ligne d'intervention")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantité mise à jour"),
            @ApiResponse(responseCode = "400", description = "Quantité invalide")
    })
    @PatchMapping(APP_ROOT
            + "/interventionsclients/update/quantite/{idIntervention}/{idLigneIntervention}/{quantite}")
    ResponseEntity<InterventionClientDto> updateQuantiteIntervention(
            @PathVariable("idIntervention") Integer idIntervention,
            @PathVariable("idLigneIntervention") Integer idLigneIntervention,
            @PathVariable("quantite") BigDecimal quantite);

    /**
     * Met à jour le client d'une intervention.
     *
     * @param idIntervention L'identifiant de l'intervention.
     * @param idClient       L'identifiant du nouveau client.
     * @return L'intervention mise à jour.
     */
    @Operation(summary = "Mettre à jour le client d'une intervention",
            description = "Permet de modifier le client d'une intervention")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client mis à jour"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @PatchMapping(APP_ROOT
            + "/interventionsclients/update/client/{idIntervention}/{idClient}")
    ResponseEntity<InterventionClientDto> updateClient(
            @PathVariable("idIntervention") Integer idIntervention,
            @PathVariable("idClient") Integer idClient);

    /**
     * Met à jour le produit d'une ligne d'intervention.
     *
     * @param idIntervention      L'identifiant de l'intervention.
     * @param idLigneIntervention L'identifiant de la ligne.
     * @param idProduit           L'identifiant du nouveau produit.
     * @return L'intervention mise à jour.
     */
    @Operation(summary = "Mettre à jour le produit d'une ligne",
            description = "Permet de modifier le produit d'une ligne d'intervention")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit mis à jour"),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    @PatchMapping(APP_ROOT
            + "/interventionsclients/update/produit/{idIntervention}/{idLigneIntervention}/{idProduit}")
    ResponseEntity<InterventionClientDto> updateProduit(
            @PathVariable("idIntervention") Integer idIntervention,
            @PathVariable("idLigneIntervention") Integer idLigneIntervention,
            @PathVariable("idProduit") Integer idProduit);

    /**
     * Supprime un produit d'une ligne d'intervention.
     *
     * @param idIntervention      L'identifiant de l'intervention.
     * @param idLigneIntervention L'identifiant de la ligne.
     * @return L'intervention mise à jour.
     */
    @Operation(summary = "Supprimer un produit d'une ligne",
            description = "Permet de supprimer un produit d'une ligne d'intervention")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit supprimé"),
            @ApiResponse(responseCode = "404", description = "Ligne non trouvée")
    })
    @DeleteMapping(APP_ROOT
            + "/interventionsclients/delete/produit/{idIntervention}/{idLigneIntervention}")
    ResponseEntity<InterventionClientDto> deleteProduit(
            @PathVariable("idIntervention") Integer idIntervention,
            @PathVariable("idLigneIntervention") Integer idLigneIntervention);

    /**
     * Recherche une intervention client par son identifiant.
     *
     * @param idInterventionClient L'identifiant de l'intervention.
     * @return L'intervention client trouvée.
     */
    @Operation(summary = "Rechercher une intervention par ID",
            description = "Permet de chercher une intervention client par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intervention trouvée"),
            @ApiResponse(responseCode = "404",
                    description = "Intervention non trouvée")
    })
    @GetMapping(value = APP_ROOT + "/interventionsclients/{idInterventionClient}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InterventionClientDto> findById(
            @PathVariable("idInterventionClient") Integer idInterventionClient);

    /**
     * Recherche une intervention client par son code.
     *
     * @param code Le code de l'intervention.
     * @return L'intervention client trouvée.
     */
    @Operation(summary = "Rechercher une intervention par code",
            description = "Permet de chercher une intervention client par son code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intervention trouvée"),
            @ApiResponse(responseCode = "404",
                    description = "Intervention non trouvée")
    })
    @GetMapping(value = APP_ROOT
            + "/interventionsclients/filter/{codeInterventionClient}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InterventionClientDto> findByCode(
            @PathVariable("codeInterventionClient") String code);

    /**
     * Récupère toutes les interventions clients.
     *
     * @return La liste de toutes les interventions clients.
     */
    @Operation(summary = "Lister toutes les interventions clients",
            description = "Retourne la liste de toutes les interventions clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des interventions ou liste vide")
    })
    @GetMapping(value = APP_ROOT + "/interventionsclients/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<InterventionClientDto>> findAll();

    /**
     * Récupère toutes les interventions d'un client.
     *
     * @param idClient L'identifiant du client.
     * @return La liste des interventions du client.
     */
    @Operation(summary = "Lister les interventions d'un client",
            description = "Retourne la liste des interventions d'un client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des interventions ou liste vide")
    })
    @GetMapping(value = APP_ROOT + "/interventionsclients/all/client/{idClient}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<InterventionClientDto>> findAllByClient(
            @PathVariable("idClient") Integer idClient);

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
    @GetMapping(value = APP_ROOT
            + "/interventionsclients/all/etat/{etatIntervention}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<InterventionClientDto>> findAllByEtatIntervention(
            @PathVariable("etatIntervention") EtatIntervention etatIntervention);

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
    @GetMapping(value = APP_ROOT
            + "/interventionsclients/all/entreprise/{idEntreprise}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<InterventionClientDto>> findAllByIdEntreprise(
            @PathVariable("idEntreprise") Integer idEntreprise);

    /**
     * Récupère toutes les lignes d'une intervention client.
     *
     * @param idIntervention L'identifiant de l'intervention.
     * @return La liste des lignes de l'intervention.
     */
    @Operation(summary = "Lister les lignes d'une intervention",
            description = "Retourne toutes les lignes d'une intervention client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des lignes ou liste vide")
    })
    @GetMapping(value = APP_ROOT
            + "/interventionsclients/lignesIntervention/{idIntervention}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<LigneInterventionClientDto>> findAllLignesInterventionsClientByInterventionClientId(
            @PathVariable("idIntervention") Integer idIntervention);

    /**
     * Supprime une intervention client par son identifiant.
     *
     * @param id L'identifiant de l'intervention à supprimer.
     * @return Une réponse vide.
     */
    @Operation(summary = "Supprimer une intervention client",
            description = "Permet de supprimer une intervention client par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Intervention supprimée"),
            @ApiResponse(responseCode = "404",
                    description = "Intervention non trouvée"),
            @ApiResponse(responseCode = "409",
                    description = "Intervention liée à des lignes")
    })
    @DeleteMapping(APP_ROOT
            + "/interventionsclients/delete/{idInterventionClient}")
    ResponseEntity<Void> delete(
            @PathVariable("idInterventionClient") Integer id);
}