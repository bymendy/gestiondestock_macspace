package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.EntrepriseDto;

import java.util.List;

/**
 * Interface de service pour la gestion des entreprises dans MacSpace.
 * Définit les opérations métier disponibles sur les entreprises.
 */
public interface EntrepriseService {

    /**
     * Enregistre ou met à jour une entreprise.
     *
     * @param dto Le DTO de l'entreprise à enregistrer.
     * @return Le DTO de l'entreprise enregistrée.
     */
    EntrepriseDto save(EntrepriseDto dto);

    /**
     * Recherche une entreprise par son identifiant.
     *
     * @param id L'identifiant de l'entreprise.
     * @return Le DTO de l'entreprise trouvée.
     */
    EntrepriseDto findById(Integer id);

    /**
     * Recherche une entreprise par son nom.
     *
     * @param nom Le nom de l'entreprise.
     * @return Le DTO de l'entreprise trouvée.
     */
    EntrepriseDto findByNom(String nom);

    /**
     * Recherche une entreprise par son email.
     *
     * @param email L'email de l'entreprise.
     * @return Le DTO de l'entreprise trouvée.
     */
    EntrepriseDto findByEmail(String email);

    /**
     * Recherche une entreprise par son code fiscal.
     *
     * @param codeFiscal Le code fiscal de l'entreprise.
     * @return Le DTO de l'entreprise trouvée.
     */
    EntrepriseDto findByCodeFiscal(String codeFiscal);

    /**
     * Récupère toutes les entreprises.
     *
     * @return La liste de toutes les entreprises.
     */
    List<EntrepriseDto> findAll();

    /**
     * Supprime une entreprise par son identifiant.
     *
     * @param id L'identifiant de l'entreprise à supprimer.
     */
    void delete(Integer id);
}