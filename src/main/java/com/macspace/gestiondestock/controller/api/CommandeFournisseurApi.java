package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.CommandeFournisseurDto;
import com.macspace.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.macspace.gestiondestock.model.EtatCommande;
import io.swagger.annotations.Api;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.macspace.gestiondestock.utils.Constants.*;

/**
 * API pour gérer les opérations liées aux commandes fournisseurs.
 * Cette interface définit les endpoints pour la création, la mise à jour,
 * la suppression et la recherche des commandes fournisseurs ainsi que de leurs lignes.
 */
@Api("commandefournisseur")
public interface CommandeFournisseurApi {

    /**
     * Crée ou enregistre une nouvelle commande fournisseur.
     *
     * @param dto le Data Transfer Object (DTO) représentant la commande fournisseur à créer ou à mettre à jour.
     * @return le DTO de la commande fournisseur créée ou mise à jour.
     */
    @PostMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT)
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto);

    /**
     * Met à jour l'état d'une commande fournisseur.
     *
     * @param idCommande l'ID de la commande fournisseur à mettre à jour.
     * @param etatCommande le nouvel état de la commande fournisseur.
     * @return le DTO de la commande fournisseur mise à jour.
     */
    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/update/etat/{idCommande}/{etatCommande}")
    CommandeFournisseurDto updateEtatCommande(@PathVariable("idCommande") Integer idCommande,
                                              @PathVariable("etatCommande") EtatCommande etatCommande);

    /**
     * Met à jour la quantité d'une ligne de commande fournisseur.
     *
     * @param idCommande l'ID de la commande fournisseur contenant la ligne à mettre à jour.
     * @param idLigneCommande l'ID de la ligne de commande fournisseur à mettre à jour.
     * @param quantite la nouvelle quantité de la ligne de commande fournisseur.
     * @return le DTO de la commande fournisseur mise à jour.
     */
    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    CommandeFournisseurDto updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                                  @PathVariable("idLigneCommande") Integer idLigneCommande,
                                                  @PathVariable("quantite") BigDecimal quantite);

    /**
     * Met à jour le fournisseur d'une commande fournisseur.
     *
     * @param idCommande l'ID de la commande fournisseur à mettre à jour.
     * @param idFournisseur le nouvel ID du fournisseur.
     * @return le DTO de la commande fournisseur mise à jour.
     */
    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/update/fournisseur/{idCommande}/{idFournisseur}")
    CommandeFournisseurDto updateFournisseur(@PathVariable("idCommande") Integer idCommande,
                                             @PathVariable("idFournisseur") Integer idFournisseur);

    /**
     * Met à jour le produit dans une ligne de commande fournisseur.
     *
     * @param idCommande l'ID de la commande fournisseur contenant la ligne à mettre à jour.
     * @param idLigneCommande l'ID de la ligne de commande fournisseur à mettre à jour.
     * @param idProduit le nouvel ID du produit.
     * @return le DTO de la commande fournisseur mise à jour.
     */
    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/update/produit/{idCommande}/{idLigneCommande}/{idProduit}")
    CommandeFournisseurDto updateArticle(@PathVariable("idCommande") Integer idCommande,
                                         @PathVariable("idLigneCommande") Integer idLigneCommande,
                                         @PathVariable("idProduit") Integer idProduit);

    /**
     * Supprime un produit d'une commande fournisseur.
     *
     * @param idCommande l'ID de la commande fournisseur contenant la ligne à supprimer.
     * @param idLigneCommande l'ID de la ligne de commande fournisseur à supprimer.
     * @return le DTO de la commande fournisseur mise à jour après suppression.
     */
    @DeleteMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/produit/{idCommande}/{idLigneCommande}")
    CommandeFournisseurDto deleteArticle(@PathVariable("idCommande") Integer idCommande,
                                         @PathVariable("idLigneCommande") Integer idLigneCommande);

    /**
     * Recherche une commande fournisseur par son ID.
     *
     * @param id l'ID de la commande fournisseur à rechercher.
     * @return le DTO de la commande fournisseur trouvée.
     */
    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT)
    CommandeFournisseurDto findById(@PathVariable("idCommandeFournisseur") Integer id);

    /**
     * Recherche une commande fournisseur par son code.
     *
     * @param code le code de la commande fournisseur à rechercher.
     * @return le DTO de la commande fournisseur trouvée.
     */
    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT)
    CommandeFournisseurDto findByCode(@PathVariable("codeCommandeFournisseur") String code);

    /**
     * Recherche toutes les commandes fournisseurs.
     *
     * @return la liste des DTO des commandes fournisseurs.
     */
    @GetMapping(FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT)
    List<CommandeFournisseurDto> findAll();

    /**
     * Recherche toutes les lignes de commande fournisseur associées à une commande fournisseur.
     *
     * @param idCommande l'ID de la commande fournisseur.
     * @return la liste des DTO des lignes de commande fournisseur associées.
     */
    @GetMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/lignesCommande/{idCommande}")
    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(@PathVariable("idCommande") Integer idCommande);

    /**
     * Supprime une commande fournisseur par son ID.
     *
     * @param id l'ID de la commande fournisseur à supprimer.
     */
    @DeleteMapping(DELETE_COMMANDE_FOURNISSEUR_ENDPOINT)
    void delete(@PathVariable("idCommandeFournisseur") Integer id);

}
