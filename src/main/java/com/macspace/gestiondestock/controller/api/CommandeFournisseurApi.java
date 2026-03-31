package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.CommandeFournisseurDto;
import com.macspace.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.macspace.gestiondestock.model.EtatCommande;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

import static com.macspace.gestiondestock.utils.Constants.*;

/**
 * Interface définissant les endpoints de gestion des commandes fournisseurs dans MacSpace.
 */
@Tag(name = "Commandes Fournisseurs",
        description = "API de gestion des commandes fournisseurs")
public interface CommandeFournisseurApi {

    /**
     * Enregistre ou met à jour une commande fournisseur.
     *
     * @param dto Le DTO de la commande fournisseur.
     * @return Le DTO de la commande enregistrée.
     */
    @Operation(summary = "Enregistrer une commande fournisseur",
            description = "Permet de créer ou modifier une commande fournisseur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commande créée ou modifiée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT)
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto);

    /**
     * Met à jour l'état d'une commande fournisseur.
     *
     * @param idCommande   L'identifiant de la commande.
     * @param etatCommande Le nouvel état de la commande.
     * @return Le DTO de la commande mise à jour.
     */
    @Operation(summary = "Mettre à jour l'état d'une commande",
            description = "Permet de modifier l'état d'une commande fournisseur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "État mis à jour"),
            @ApiResponse(responseCode = "404", description = "Commande non trouvée"),
            @ApiResponse(responseCode = "400", description = "Modification impossible")
    })
    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT
            + "/update/etat/{idCommande}/{etatCommande}")
    CommandeFournisseurDto updateEtatCommande(
            @PathVariable("idCommande") Integer idCommande,
            @PathVariable("etatCommande") EtatCommande etatCommande);

    /**
     * Met à jour la quantité d'une ligne de commande.
     *
     * @param idCommande      L'identifiant de la commande.
     * @param idLigneCommande L'identifiant de la ligne de commande.
     * @param quantite        La nouvelle quantité.
     * @return Le DTO de la commande mise à jour.
     */
    @Operation(summary = "Mettre à jour la quantité d'une ligne",
            description = "Permet de modifier la quantité d'une ligne de commande")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantité mise à jour"),
            @ApiResponse(responseCode = "400", description = "Quantité invalide")
    })
    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT
            + "/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    CommandeFournisseurDto updateQuantiteCommande(
            @PathVariable("idCommande") Integer idCommande,
            @PathVariable("idLigneCommande") Integer idLigneCommande,
            @PathVariable("quantite") BigDecimal quantite);

    /**
     * Met à jour le fournisseur d'une commande.
     *
     * @param idCommande    L'identifiant de la commande.
     * @param idFournisseur L'identifiant du nouveau fournisseur.
     * @return Le DTO de la commande mise à jour.
     */
    @Operation(summary = "Mettre à jour le fournisseur d'une commande",
            description = "Permet de modifier le fournisseur d'une commande")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fournisseur mis à jour"),
            @ApiResponse(responseCode = "404", description = "Fournisseur non trouvé")
    })
    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT
            + "/update/fournisseur/{idCommande}/{idFournisseur}")
    CommandeFournisseurDto updateFournisseur(
            @PathVariable("idCommande") Integer idCommande,
            @PathVariable("idFournisseur") Integer idFournisseur);

    /**
     * Met à jour le produit d'une ligne de commande.
     *
     * @param idCommande      L'identifiant de la commande.
     * @param idLigneCommande L'identifiant de la ligne de commande.
     * @param idProduit       L'identifiant du nouveau produit.
     * @return Le DTO de la commande mise à jour.
     */
    @Operation(summary = "Mettre à jour le produit d'une ligne",
            description = "Permet de modifier le produit d'une ligne de commande")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit mis à jour"),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT
            + "/update/produit/{idCommande}/{idLigneCommande}/{idProduit}")
    CommandeFournisseurDto updateProduit(
            @PathVariable("idCommande") Integer idCommande,
            @PathVariable("idLigneCommande") Integer idLigneCommande,
            @PathVariable("idProduit") Integer idProduit);

    /**
     * Supprime un produit d'une ligne de commande.
     *
     * @param idCommande      L'identifiant de la commande.
     * @param idLigneCommande L'identifiant de la ligne de commande.
     * @return Le DTO de la commande mise à jour.
     */
    @Operation(summary = "Supprimer un produit d'une ligne de commande",
            description = "Permet de supprimer un produit d'une ligne de commande")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit supprimé"),
            @ApiResponse(responseCode = "404", description = "Ligne non trouvée")
    })
    @DeleteMapping(COMMANDE_FOURNISSEUR_ENDPOINT
            + "/delete/produit/{idCommande}/{idLigneCommande}")
    CommandeFournisseurDto deleteProduit(
            @PathVariable("idCommande") Integer idCommande,
            @PathVariable("idLigneCommande") Integer idLigneCommande);

    /**
     * Recherche une commande fournisseur par son identifiant.
     *
     * @param id L'identifiant de la commande.
     * @return Le DTO de la commande trouvée.
     */
    @Operation(summary = "Rechercher une commande par ID",
            description = "Permet de chercher une commande fournisseur par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commande trouvée"),
            @ApiResponse(responseCode = "404", description = "Commande non trouvée")
    })
    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT)
    CommandeFournisseurDto findById(
            @PathVariable("idCommandeFournisseur") Integer id);

    /**
     * Recherche une commande fournisseur par son code.
     *
     * @param code Le code de la commande.
     * @return Le DTO de la commande trouvée.
     */
    @Operation(summary = "Rechercher une commande par code",
            description = "Permet de chercher une commande fournisseur par son code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commande trouvée"),
            @ApiResponse(responseCode = "404", description = "Commande non trouvée")
    })
    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT)
    CommandeFournisseurDto findByCode(
            @PathVariable("codeCommandeFournisseur") String code);

    /**
     * Récupère toutes les commandes fournisseurs.
     *
     * @return La liste de toutes les commandes.
     */
    @Operation(summary = "Lister toutes les commandes fournisseurs",
            description = "Retourne la liste de toutes les commandes fournisseurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des commandes ou liste vide")
    })
    @GetMapping(FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT)
    List<CommandeFournisseurDto> findAll();

    /**
     * Récupère toutes les commandes d'un fournisseur.
     *
     * @param idFournisseur L'identifiant du fournisseur.
     * @return La liste des commandes du fournisseur.
     */
    @Operation(summary = "Lister les commandes d'un fournisseur",
            description = "Retourne la liste des commandes d'un fournisseur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des commandes ou liste vide")
    })
    @GetMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/all/fournisseur/{idFournisseur}")
    List<CommandeFournisseurDto> findAllByFournisseur(
            @PathVariable("idFournisseur") Integer idFournisseur);

    /**
     * Récupère toutes les commandes d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des commandes de l'entreprise.
     */
    @Operation(summary = "Lister les commandes d'une entreprise",
            description = "Retourne la liste des commandes d'une entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des commandes ou liste vide")
    })
    @GetMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/all/entreprise/{idEntreprise}")
    List<CommandeFournisseurDto> findAllByIdEntreprise(
            @PathVariable("idEntreprise") Integer idEntreprise);

    /**
     * Récupère toutes les lignes d'une commande fournisseur.
     *
     * @param idCommande L'identifiant de la commande.
     * @return La liste des lignes de la commande.
     */
    @Operation(summary = "Lister les lignes d'une commande",
            description = "Retourne toutes les lignes d'une commande fournisseur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des lignes ou liste vide")
    })
    @GetMapping(COMMANDE_FOURNISSEUR_ENDPOINT
            + "/lignesCommande/{idCommande}")
    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(
            @PathVariable("idCommande") Integer idCommande);

    /**
     * Supprime une commande fournisseur par son identifiant.
     *
     * @param id L'identifiant de la commande à supprimer.
     */
    @Operation(summary = "Supprimer une commande fournisseur",
            description = "Permet de supprimer une commande fournisseur par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commande supprimée"),
            @ApiResponse(responseCode = "404", description = "Commande non trouvée"),
            @ApiResponse(responseCode = "409",
                    description = "Commande liée à des lignes de commande")
    })
    @DeleteMapping(DELETE_COMMANDE_FOURNISSEUR_ENDPOINT)
    void delete(@PathVariable("idCommandeFournisseur") Integer id);
}