package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.InterventionsDto;

import java.util.List;

/**
 * Interface pour les services de gestion des interventions.
 * <p>
 * Cette interface définit les opérations pour enregistrer, rechercher, lister et supprimer des interventions.
 * </p>
 */
public interface InterventionsService {

    /**
     * Enregistre ou met à jour une intervention.
     *
     * @param dto l'objet {@link InterventionsDto} représentant l'intervention à enregistrer ou mettre à jour
     * @return l'objet {@link InterventionsDto} enregistré ou mis à jour
     */
    InterventionsDto save(InterventionsDto dto);

    /**
     * Recherche une intervention par son identifiant.
     *
     * @param id l'identifiant de l'intervention
     * @return l'objet {@link InterventionsDto} correspondant à l'identifiant fourni
     * @throws EntityNotFoundException si aucune intervention n'est trouvée avec l'identifiant fourni
     */
    InterventionsDto findById(Integer id);

    /**
     * Recherche une intervention par son code.
     *
     * @param code le code de l'intervention
     * @return l'objet {@link InterventionsDto} correspondant au code fourni
     * @throws EntityNotFoundException si aucune intervention n'est trouvée avec le code fourni
     */
    InterventionsDto findByCode(String code);

    /**
     * Récupère toutes les interventions.
     *
     * @return une liste d'objets {@link InterventionsDto} représentant toutes les interventions
     */
    List<InterventionsDto> findAll();

    /**
     * Supprime une intervention par son identifiant.
     *
     * @param id l'identifiant de l'intervention à supprimer
     * @throws EntityNotFoundException si aucune intervention n'est trouvée avec l'identifiant fourni
     */
    void delete(Integer id);
}
