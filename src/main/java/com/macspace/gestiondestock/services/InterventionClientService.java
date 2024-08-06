package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.InterventionClientDto;
import com.macspace.gestiondestock.dto.LigneInterventionClientDto;
import com.macspace.gestiondestock.model.EtatIntervention;

import java.math.BigDecimal;
import java.util.List;

/**
 * L'interface InterventionClientService définit les opérations de gestion des interventions clients dans le système.
 * <p>
 * Elle permet d'enregistrer, mettre à jour, rechercher, lister et supprimer des interventions clients, ainsi que de gérer
 * les lignes d'intervention associées.
 * </p>
 */
public interface InterventionClientService {

    /**
     * Enregistre ou met à jour une intervention client.
     *
     * @param dto l'objet {@link InterventionClientDto} représentant l'intervention à enregistrer ou mettre à jour
     * @return l'objet {@link InterventionClientDto} enregistré ou mis à jour
     */
    InterventionClientDto save(InterventionClientDto dto);

    /**
     * Met à jour l'état d'une intervention.
     *
     * @param idIntervention l'identifiant de l'intervention à mettre à jour
     * @param etatIntervention le nouvel état de l'intervention
     * @return l'objet {@link InterventionClientDto} mis à jour
     */
    InterventionClientDto updateEtatIntervention(Integer idIntervention, EtatIntervention etatIntervention);

    /**
     * Met à jour la quantité d'une ligne d'intervention.
     *
     * @param idIntervention l'identifiant de l'intervention contenant la ligne
     * @param idLigneIntervention l'identifiant de la ligne d'intervention à mettre à jour
     * @param quantite la nouvelle quantité
     * @return l'objet {@link InterventionClientDto} mis à jour
     */
    InterventionClientDto updateQuantiteIntervention(Integer idIntervention, Integer idLigneIntervention, BigDecimal quantite);

    /**
     * Met à jour le client associé à une intervention.
     *
     * @param idIntervention l'identifiant de l'intervention à mettre à jour
     * @param idClient l'identifiant du nouveau client
     * @return l'objet {@link InterventionClientDto} mis à jour
     */
    InterventionClientDto updateClient(Integer idIntervention, Integer idClient);

    /**
     * Met à jour le produit dans une intervention.
     *
     * @param idIntervention l'identifiant de l'intervention contenant la ligne
     * @param idLigneIntervention l'identifiant de la ligne d'intervention à mettre à jour
     * @param newIdProduit l'identifiant du nouveau produit
     * @return l'objet {@link InterventionClientDto} mis à jour
     */
    InterventionClientDto updateProduit(Integer idIntervention, Integer idLigneIntervention, Integer newIdProduit);

    /**
     * Supprime un produit d'une intervention en supprimant la ligne d'intervention correspondante.
     *
     * @param idIntervention l'identifiant de l'intervention contenant la ligne
     * @param idLigneIntervention l'identifiant de la ligne d'intervention à supprimer
     * @return l'objet {@link InterventionClientDto} mis à jour après la suppression
     */
    InterventionClientDto deleteProduit(Integer idIntervention, Integer idLigneIntervention);

    /**
     * Recherche une intervention par son identifiant.
     *
     * @param id l'identifiant de l'intervention
     * @return l'objet {@link InterventionClientDto} correspondant à l'identifiant fourni
     * @throws EntityNotFoundException si aucune intervention n'est trouvée avec l'identifiant fourni
     */
    InterventionClientDto findById(Integer id);

    /**
     * Recherche une intervention par son code.
     *
     * @param code le code de l'intervention
     * @return l'objet {@link InterventionClientDto} correspondant au code fourni
     * @throws EntityNotFoundException si aucune intervention n'est trouvée avec le code fourni
     */
    InterventionClientDto findByCode(String code);

    /**
     * Récupère toutes les interventions clients.
     *
     * @return une liste d'objets {@link InterventionClientDto} représentant toutes les interventions
     */
    List<InterventionClientDto> findAll();

    /**
     * Récupère toutes les lignes d'intervention pour une intervention spécifique.
     *
     * @param idIntervention l'identifiant de l'intervention
     * @return une liste d'objets {@link LigneInterventionClientDto} représentant les lignes d'intervention associées
     */
    List<LigneInterventionClientDto> findAllLignesInterventionsClientByInterventionClientId(Integer idIntervention);

    /**
     * Supprime une intervention par son identifiant.
     *
     * @param id l'identifiant de l'intervention à supprimer
     * @throws EntityNotFoundException si aucune intervention n'est trouvée avec l'identifiant fourni
     */
    void delete(Integer id);
}
