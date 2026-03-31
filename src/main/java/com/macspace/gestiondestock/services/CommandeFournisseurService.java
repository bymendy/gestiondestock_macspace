package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.CommandeFournisseurDto;
import com.macspace.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.macspace.gestiondestock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface de service pour la gestion des commandes fournisseurs dans MacSpace.
 * Définit les opérations métier disponibles sur les commandes fournisseurs.
 */
public interface CommandeFournisseurService {

    /**
     * Enregistre ou met à jour une commande fournisseur.
     *
     * @param dto Le DTO de la commande à enregistrer.
     * @return Le DTO de la commande enregistrée.
     */
    CommandeFournisseurDto save(CommandeFournisseurDto dto);

    /**
     * Met à jour l'état d'une commande fournisseur.
     *
     * @param idCommande L'identifiant de la commande.
     * @param etatCommande Le nouvel état de la commande.
     * @return Le DTO de la commande mise à jour.
     */
    CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    /**
     * Met à jour la quantité d'une ligne de commande.
     *
     * @param idCommande L'identifiant de la commande.
     * @param idLigneCommande L'identifiant de la ligne de commande.
     * @param quantite La nouvelle quantité.
     * @return Le DTO de la commande mise à jour.
     */
    CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    /**
     * Met à jour le fournisseur d'une commande.
     *
     * @param idCommande L'identifiant de la commande.
     * @param idFournisseur L'identifiant du nouveau fournisseur.
     * @return Le DTO de la commande mise à jour.
     */
    CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur);

    /**
     * Met à jour le produit d'une ligne de commande.
     *
     * @param idCommande L'identifiant de la commande.
     * @param idLigneCommande L'identifiant de la ligne de commande.
     * @param idProduit L'identifiant du nouveau produit.
     * @return Le DTO de la commande mise à jour.
     */
    CommandeFournisseurDto updateProduit(Integer idCommande, Integer idLigneCommande, Integer idProduit);

    /**
     * Supprime un produit d'une ligne de commande.
     *
     * @param idCommande L'identifiant de la commande.
     * @param idLigneCommande L'identifiant de la ligne de commande.
     * @return Le DTO de la commande mise à jour.
     */
    CommandeFournisseurDto deleteProduit(Integer idCommande, Integer idLigneCommande);

    /**
     * Recherche une commande fournisseur par son identifiant.
     *
     * @param id L'identifiant de la commande.
     * @return Le DTO de la commande trouvée.
     */
    CommandeFournisseurDto findById(Integer id);

    /**
     * Recherche une commande fournisseur par son code.
     *
     * @param code Le code de la commande.
     * @return Le DTO de la commande trouvée.
     */
    CommandeFournisseurDto findByCode(String code);

    /**
     * Récupère toutes les commandes fournisseurs.
     *
     * @return La liste de toutes les commandes.
     */
    List<CommandeFournisseurDto> findAll();

    /**
     * Récupère toutes les commandes d'un fournisseur.
     *
     * @param idFournisseur L'identifiant du fournisseur.
     * @return La liste des commandes du fournisseur.
     */
    List<CommandeFournisseurDto> findAllByFournisseur(Integer idFournisseur);

    /**
     * Récupère toutes les commandes d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des commandes de l'entreprise.
     */
    List<CommandeFournisseurDto> findAllByIdEntreprise(Integer idEntreprise);

    /**
     * Récupère toutes les lignes de commande d'une commande.
     *
     * @param idCommande L'identifiant de la commande.
     * @return La liste des lignes de commande.
     */
    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande);

    /**
     * Supprime une commande fournisseur par son identifiant.
     *
     * @param id L'identifiant de la commande à supprimer.
     */
    void delete(Integer id);
}