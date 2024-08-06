package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.EntrepriseDto;

import java.util.List;

/**
 * L'interface EntrepriseService définit les opérations de gestion des entreprises dans le système.
 * <p>
 * Elle fournit des méthodes pour enregistrer, mettre à jour, rechercher, lister et supprimer des entreprises.
 */
public interface EntrepriseService {

    /**
     * Enregistre ou met à jour une entreprise.
     *
     * @param dto l'objet {@link EntrepriseDto} représentant l'entreprise à enregistrer ou mettre à jour
     * @return l'objet {@link EntrepriseDto} enregistré ou mis à jour
     */
    EntrepriseDto save(EntrepriseDto dto);

    /**
     * Recherche une entreprise par son identifiant.
     *
     * @param id l'identifiant de l'entreprise
     * @return l'objet {@link EntrepriseDto} correspondant à l'identifiant fourni
     */
    EntrepriseDto findById(Integer id);

    /**
     * Récupère toutes les entreprises.
     *
     * @return une liste d'objets {@link EntrepriseDto} représentant toutes les entreprises
     */
    List<EntrepriseDto> findAll();

    /**
     * Supprime une entreprise par son identifiant.
     *
     * @param id l'identifiant de l'entreprise à supprimer
     */
    void delete(Integer id);
}
