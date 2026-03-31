package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.ClientDto;

import java.util.List;

/**
 * Interface de service pour la gestion des clients dans MacSpace.
 * Définit les opérations métier disponibles sur les clients.
 */
public interface ClientService {

    /**
     * Enregistre ou met à jour un client.
     *
     * @param dto Le DTO du client à enregistrer.
     * @return Le DTO du client enregistré.
     */
    ClientDto save(ClientDto dto);

    /**
     * Recherche un client par son identifiant.
     *
     * @param id L'identifiant du client.
     * @return Le DTO du client trouvé.
     */
    ClientDto findById(Integer id);

    /**
     * Recherche un client par son email.
     *
     * @param email L'email du client.
     * @return Le DTO du client trouvé.
     */
    ClientDto findByEmail(String email);

    /**
     * Récupère tous les clients.
     *
     * @return La liste de tous les clients.
     */
    List<ClientDto> findAll();

    /**
     * Récupère tous les clients d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des clients de l'entreprise.
     */
    List<ClientDto> findAllByIdEntreprise(Integer idEntreprise);

    /**
     * Supprime un client par son identifiant.
     *
     * @param id L'identifiant du client à supprimer.
     */
    void delete(Integer id);
}