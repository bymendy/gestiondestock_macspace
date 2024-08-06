package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.FournisseurDto;

import java.util.List;

/**
 * L'interface FournisseurService définit les opérations de gestion des fournisseurs dans le système.
 * <p>
 * Elle permet d'enregistrer, mettre à jour, rechercher, lister et supprimer des fournisseurs.
 * </p>
 */
public interface FournisseurService {

    /**
     * Enregistre ou met à jour un fournisseur.
     *
     * @param dto l'objet {@link FournisseurDto} représentant le fournisseur à enregistrer ou mettre à jour
     * @return l'objet {@link FournisseurDto} enregistré ou mis à jour
     */
    FournisseurDto save(FournisseurDto dto);

    /**
     * Recherche un fournisseur par son identifiant.
     *
     * @param id l'identifiant du fournisseur
     * @return l'objet {@link FournisseurDto} correspondant à l'identifiant fourni
     * @throws EntityNotFoundException si aucun fournisseur n'est trouvé avec l'identifiant fourni
     */
    FournisseurDto findById(Integer id);

    /**
     * Récupère tous les fournisseurs.
     *
     * @return une liste d'objets {@link FournisseurDto} représentant tous les fournisseurs
     */
    List<FournisseurDto> findAll();

    /**
     * Supprime un fournisseur par son identifiant.
     *
     * @param id l'identifiant du fournisseur à supprimer
     * @throws EntityNotFoundException si aucun fournisseur n'est trouvé avec l'identifiant fourni
     */
    void delete(Integer id);
}
