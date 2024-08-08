package com.macspace.gestiondestock.controller;

import java.math.BigDecimal;
import java.util.List;

import com.macspace.gestiondestock.controller.api.CommandeFournisseurApi;
import com.macspace.gestiondestock.dto.CommandeFournisseurDto;
import com.macspace.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.macspace.gestiondestock.model.EtatCommande;
import com.macspace.gestiondestock.services.CommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur pour gérer les opérations relatives aux commandes fournisseurs.
 * Implémente l'interface {@link CommandeFournisseurApi} pour exposer les endpoints API.
 */
@RestController
public class CommandeFournisseurController implements CommandeFournisseurApi {

    private CommandeFournisseurService commandeFournisseurService;

    /**
     * Constructeur pour {@link CommandeFournisseurController}.
     *
     * @param commandeFournisseurService le service pour gérer les opérations sur les commandes fournisseurs.
     */
    @Autowired
    public CommandeFournisseurController(CommandeFournisseurService commandeFournisseurService) {
        this.commandeFournisseurService = commandeFournisseurService;
    }

    /**
     * Enregistre ou met à jour une commande fournisseur.
     *
     * @param dto le Data Transfer Object (DTO) représentant la commande fournisseur à enregistrer ou mettre à jour.
     * @return le DTO de la commande fournisseur enregistrée ou mise à jour.
     */
    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        return commandeFournisseurService.save(dto);
    }

    /**
     * Met à jour l'état d'une commande fournisseur.
     *
     * @param idCommande l'ID de la commande fournisseur à mettre à jour.
     * @param etatCommande le nouvel état de la commande fournisseur.
     * @return le DTO de la commande fournisseur mise à jour.
     */
    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return commandeFournisseurService.updateEtatCommande(idCommande, etatCommande);
    }

    /**
     * Met à jour la quantité d'une ligne de commande fournisseur.
     *
     * @param idCommande l'ID de la commande fournisseur contenant la ligne.
     * @param idLigneCommande l'ID de la ligne de commande fournisseur à mettre à jour.
     * @param quantite la nouvelle quantité de la ligne de commande fournisseur.
     * @return le DTO de la commande fournisseur mise à jour.
     */
    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return commandeFournisseurService.updateQuantiteCommande(idCommande, idLigneCommande, quantite);
    }

    /**
     * Met à jour le fournisseur d'une commande fournisseur.
     *
     * @param idCommande l'ID de la commande fournisseur à mettre à jour.
     * @param idFournisseur le nouvel ID du fournisseur.
     * @return le DTO de la commande fournisseur mise à jour.
     */
    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
        return commandeFournisseurService.updateFournisseur(idCommande, idFournisseur);
    }

    /**
     * Met à jour le produit dans une ligne de commande fournisseur.
     *
     * @param idCommande l'ID de la commande fournisseur contenant la ligne.
     * @param idLigneCommande l'ID de la ligne de commande fournisseur à mettre à jour.
     * @param idProduit le nouvel ID du produit.
     * @return le DTO de la commande fournisseur mise à jour.
     */
    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idProduit) {
        return commandeFournisseurService.updateProduit(idCommande, idLigneCommande, idProduit);
    }

    /**
     * Supprime un produit d'une commande fournisseur.
     *
     * @param idCommande l'ID de la commande fournisseur contenant la ligne à supprimer.
     * @param idLigneCommande l'ID de la ligne de commande fournisseur à supprimer.
     * @return le DTO de la commande fournisseur mise à jour après suppression.
     */
    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return commandeFournisseurService.deleteProduit(idCommande, idLigneCommande);
    }

    /**
     * Recherche une commande fournisseur par son ID.
     *
     * @param id l'ID de la commande fournisseur à rechercher.
     * @return le DTO de la commande fournisseur trouvée.
     */
    @Override
    public CommandeFournisseurDto findById(Integer id) {
        return commandeFournisseurService.findById(id);
    }

    /**
     * Recherche une commande fournisseur par son code.
     *
     * @param code le code de la commande fournisseur à rechercher.
     * @return le DTO de la commande fournisseur trouvée.
     */
    @Override
    public CommandeFournisseurDto findByCode(String code) {
        return commandeFournisseurService.findByCode(code);
    }

    /**
     * Recherche toutes les commandes fournisseurs.
     *
     * @return la liste des DTO des commandes fournisseurs.
     */
    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurService.findAll();
    }

    /**
     * Recherche toutes les lignes de commande fournisseur associées à une commande fournisseur.
     *
     * @param idCommande l'ID de la commande fournisseur.
     * @return la liste des DTO des lignes de commande fournisseur.
     */
    @Override
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        return commandeFournisseurService.findAllLignesCommandesFournisseurByCommandeFournisseurId(idCommande);
    }

    /**
     * Supprime une commande fournisseur par son ID.
     *
     * @param id l'ID de la commande fournisseur à supprimer.
     */
    @Override
    public void delete(Integer id) {
        commandeFournisseurService.delete(id);
    }
}
