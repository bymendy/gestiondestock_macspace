package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.InterventionClientDto;
import com.macspace.gestiondestock.dto.LigneInterventionClientDto;
import com.macspace.gestiondestock.model.EtatIntervention;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface de service pour la gestion des interventions clients dans MacSpace.
 * Définit les opérations métier disponibles sur les interventions clients.
 */
public interface InterventionClientService {

    /**
     * Enregistre ou met à jour une intervention client.
     *
     * @param dto Le DTO de l'intervention à enregistrer.
     * @return Le DTO de l'intervention enregistrée.
     */
    InterventionClientDto save(InterventionClientDto dto);

    /**
     * Met à jour l'état d'une intervention client.
     *
     * @param idIntervention L'identifiant de l'intervention.
     * @param etatIntervention Le nouvel état de l'intervention.
     * @return Le DTO de l'intervention mise à jour.
     */
    InterventionClientDto updateEtatIntervention(Integer idIntervention,
                                                 EtatIntervention etatIntervention);

    /**
     * Met à jour la quantité d'une ligne d'intervention.
     *
     * @param idIntervention L'identifiant de l'intervention.
     * @param idLigneIntervention L'identifiant de la ligne d'intervention.
     * @param quantite La nouvelle quantité.
     * @return Le DTO de l'intervention mise à jour.
     */
    InterventionClientDto updateQuantiteIntervention(Integer idIntervention,
                                                     Integer idLigneIntervention, BigDecimal quantite);

    /**
     * Met à jour le client d'une intervention.
     *
     * @param idIntervention L'identifiant de l'intervention.
     * @param idClient L'identifiant du nouveau client.
     * @return Le DTO de l'intervention mise à jour.
     */
    InterventionClientDto updateClient(Integer idIntervention, Integer idClient);

    /**
     * Met à jour le produit d'une ligne d'intervention.
     *
     * @param idIntervention L'identifiant de l'intervention.
     * @param idLigneIntervention L'identifiant de la ligne d'intervention.
     * @param newIdProduit L'identifiant du nouveau produit.
     * @return Le DTO de l'intervention mise à jour.
     */
    InterventionClientDto updateProduit(Integer idIntervention,
                                        Integer idLigneIntervention, Integer newIdProduit);

    /**
     * Supprime un produit d'une ligne d'intervention.
     *
     * @param idIntervention L'identifiant de l'intervention.
     * @param idLigneIntervention L'identifiant de la ligne d'intervention.
     * @return Le DTO de l'intervention mise à jour.
     */
    InterventionClientDto deleteProduit(Integer idIntervention,
                                        Integer idLigneIntervention);

    /**
     * Recherche une intervention client par son identifiant.
     *
     * @param id L'identifiant de l'intervention.
     * @return Le DTO de l'intervention trouvée.
     */
    InterventionClientDto findById(Integer id);

    /**
     * Recherche une intervention client par son code.
     *
     * @param code Le code de l'intervention.
     * @return Le DTO de l'intervention trouvée.
     */
    InterventionClientDto findByCode(String code);

    /**
     * Récupère toutes les interventions clients.
     *
     * @return La liste de toutes les interventions.
     */
    List<InterventionClientDto> findAll();

    /**
     * Récupère toutes les interventions d'un client.
     *
     * @param idClient L'identifiant du client.
     * @return La liste des interventions du client.
     */
    List<InterventionClientDto> findAllByClient(Integer idClient);

    /**
     * Récupère toutes les interventions par état.
     *
     * @param etatIntervention L'état de l'intervention.
     * @return La liste des interventions correspondantes.
     */
    List<InterventionClientDto> findAllByEtatIntervention(
            EtatIntervention etatIntervention);

    /**
     * Récupère toutes les interventions d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des interventions de l'entreprise.
     */
    List<InterventionClientDto> findAllByIdEntreprise(Integer idEntreprise);

    /**
     * Récupère toutes les lignes d'une intervention client.
     *
     * @param idIntervention L'identifiant de l'intervention.
     * @return La liste des lignes de l'intervention.
     */
    List<LigneInterventionClientDto> findAllLignesInterventionsClientByInterventionClientId(
            Integer idIntervention);

    /**
     * Supprime une intervention client par son identifiant.
     *
     * @param id L'identifiant de l'intervention à supprimer.
     */
    void delete(Integer id);
}