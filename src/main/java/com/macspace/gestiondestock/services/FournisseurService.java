package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.FournisseurDto;

import java.util.List;

/**
 * Interface de service pour la gestion des fournisseurs dans MacSpace.
 * Définit les opérations métier disponibles sur les fournisseurs.
 */
public interface FournisseurService {

    /**
     * Enregistre ou met à jour un fournisseur.
     *
     * @param dto Le DTO du fournisseur à enregistrer.
     * @return Le DTO du fournisseur enregistré.
     */
    FournisseurDto save(FournisseurDto dto);

    /**
     * Recherche un fournisseur par son identifiant.
     *
     * @param id L'identifiant du fournisseur.
     * @return Le DTO du fournisseur trouvé.
     */
    FournisseurDto findById(Integer id);

    /**
     * Recherche un fournisseur par son email.
     *
     * @param email L'email du fournisseur.
     * @return Le DTO du fournisseur trouvé.
     */
    FournisseurDto findByEmail(String email);

    /**
     * Récupère tous les fournisseurs par leur nom.
     *
     * @param nom Le nom du fournisseur.
     * @return La liste des fournisseurs correspondants.
     */
    List<FournisseurDto> findAllByNom(String nom);

    /**
     * Récupère tous les fournisseurs.
     *
     * @return La liste de tous les fournisseurs.
     */
    List<FournisseurDto> findAll();

    /**
     * Récupère tous les fournisseurs d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des fournisseurs de l'entreprise.
     */
    List<FournisseurDto> findAllByIdEntreprise(Integer idEntreprise);

    /**
     * Supprime un fournisseur par son identifiant.
     *
     * @param id L'identifiant du fournisseur à supprimer.
     */
    void delete(Integer id);
}