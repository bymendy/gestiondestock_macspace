package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.model.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour gérer les entités {@link LigneCommandeFournisseur}.
 * Fournit les opérations CRUD et des requêtes
 * personnalisées sur la table 'lignecommandefournisseur'.
 */
public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur, Integer> {

    /**
     * Trouve toutes les lignes d'une commande fournisseur.
     *
     * @param idCommande L'identifiant de la commande fournisseur.
     * @return La liste des lignes de la commande.
     */
    List<LigneCommandeFournisseur> findAllByCommandeFournisseurId(Integer idCommande);

    /**
     * Trouve toutes les lignes de commande d'un produit.
     *
     * @param idProduit L'identifiant du produit.
     * @return La liste des lignes de commande du produit.
     */
    List<LigneCommandeFournisseur> findAllByProduitId(Integer idProduit);

    /**
     * Trouve toutes les lignes de commande d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des lignes de commande de l'entreprise.
     */
    List<LigneCommandeFournisseur> findAllByIdEntreprise(Integer idEntreprise);
}