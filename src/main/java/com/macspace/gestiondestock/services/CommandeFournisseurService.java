package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.CommandeFournisseurDto;
import com.macspace.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.macspace.gestiondestock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

/**
 * L'interface CommandeFournisseurService définit les opérations de gestion des commandes fournisseurs dans le système.
 * <p>
 * Elle fournit des méthodes pour enregistrer, mettre à jour, rechercher, lister et supprimer des commandes fournisseurs ainsi que leurs lignes de commande.
 */
public interface CommandeFournisseurService {

    /**
     * Enregistre ou met à jour une commande fournisseur.
     *
     * @param dto l'objet {@link CommandeFournisseurDto} représentant la commande fournisseur à enregistrer ou mettre à jour
     * @return l'objet {@link CommandeFournisseurDto} enregistré ou mis à jour
     */
    CommandeFournisseurDto save(CommandeFournisseurDto dto);

    /**
     * Met à jour l'état d'une commande fournisseur.
     *
     * @param idCommande l'identifiant de la commande
     * @param etatCommande le nouvel état de la commande
     * @return l'objet {@link CommandeFournisseurDto} mis à jour
     */
    CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    /**
     * Met à jour la quantité d'une ligne de commande fournisseur.
     *
     * @param idCommande l'identifiant de la commande
     * @param idLigneCommande l'identifiant de la ligne de commande
     * @param quantite la nouvelle quantité
     * @return l'objet {@link CommandeFournisseurDto} mis à jour
     */
    CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    /**
     * Met à jour le fournisseur d'une commande.
     *
     * @param idCommande l'identifiant de la commande
     * @param idFournisseur l'identifiant du nouveau fournisseur
     * @return l'objet {@link CommandeFournisseurDto} mis à jour
     */
    CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur);

    /**
     * Met à jour le produit d'une ligne de commande fournisseur.
     *
     * @param idCommande l'identifiant de la commande
     * @param idLigneCommande l'identifiant de la ligne de commande
     * @param idProduit l'identifiant du nouveau produit
     * @return l'objet {@link CommandeFournisseurDto} mis à jour
     */
    CommandeFournisseurDto updateProduit(Integer idCommande, Integer idLigneCommande, Integer idProduit);

    /**
     * Supprime un produit d'une ligne de commande fournisseur.
     *
     * @param idCommande l'identifiant de la commande
     * @param idLigneCommande l'identifiant de la ligne de commande
     * @return l'objet {@link CommandeFournisseurDto} mis à jour après suppression du produit
     */
    CommandeFournisseurDto deleteProduit(Integer idCommande, Integer idLigneCommande);

    /**
     * Recherche une commande fournisseur par son identifiant.
     *
     * @param id l'identifiant de la commande
     * @return l'objet {@link CommandeFournisseurDto} correspondant à l'identifiant fourni
     */
    CommandeFournisseurDto findById(Integer id);

    /**
     * Recherche une commande fournisseur par son code.
     *
     * @param code le code de la commande
     * @return l'objet {@link CommandeFournisseurDto} correspondant au code fourni
     */
    CommandeFournisseurDto findByCode(String code);

    /**
     * Récupère toutes les commandes fournisseurs.
     *
     * @return une liste d'objets {@link CommandeFournisseurDto} représentant toutes les commandes fournisseurs
     */
    List<CommandeFournisseurDto> findAll();

    /**
     * Récupère toutes les lignes de commande fournisseur d'une commande donnée.
     *
     * @param idCommande l'identifiant de la commande
     * @return une liste d'objets {@link LigneCommandeFournisseurDto} représentant toutes les lignes de commande fournisseur de la commande
     */
    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande);

    /**
     * Supprime une commande fournisseur par son identifiant.
     *
     * @param id l'identifiant de la commande à supprimer
     */
    void delete(Integer id);
}
