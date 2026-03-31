package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.InterventionDto;
import com.macspace.gestiondestock.model.EtatIntervention;

import java.util.List;

/**
 * Interface de service pour la gestion des interventions dans MacSpace.
 * Définit les opérations métier disponibles sur les interventions techniques.
 */
public interface InterventionService {

    /**
     * Enregistre ou met à jour une intervention.
     *
     * @param dto Le DTO de l'intervention à enregistrer.
     * @return Le DTO de l'intervention enregistrée.
     */
    InterventionDto save(InterventionDto dto);

    /**
     * Recherche une intervention par son identifiant.
     *
     * @param id L'identifiant de l'intervention.
     * @return Le DTO de l'intervention trouvée.
     */
    InterventionDto findById(Integer id);

    /**
     * Recherche une intervention par son code.
     *
     * @param code Le code de l'intervention.
     * @return Le DTO de l'intervention trouvée.
     */
    InterventionDto findByCode(String code);

    /**
     * Récupère toutes les interventions.
     *
     * @return La liste de toutes les interventions.
     */
    List<InterventionDto> findAll();

    /**
     * Récupère toutes les interventions par état.
     *
     * @param etatIntervention L'état de l'intervention.
     * @return La liste des interventions correspondantes.
     */
    List<InterventionDto> findAllByEtatIntervention(
            EtatIntervention etatIntervention);

    /**
     * Récupère toutes les interventions d'un technicien.
     *
     * @param idTechnicien L'identifiant du technicien.
     * @return La liste des interventions du technicien.
     */
    List<InterventionDto> findAllByTechnicien(Integer idTechnicien);

    /**
     * Récupère toutes les interventions d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des interventions de l'entreprise.
     */
    List<InterventionDto> findAllByIdEntreprise(Integer idEntreprise);

    /**
     * Supprime une intervention par son identifiant.
     *
     * @param id L'identifiant de l'intervention à supprimer.
     */
    void delete(Integer id);
}