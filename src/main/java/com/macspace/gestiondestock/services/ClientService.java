package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.ClientDto;
import java.util.List;

/**
 * L'interface ClientService définit les opérations de gestion des clients dans le système.
 * <p>
 * Elle fournit des méthodes pour enregistrer, rechercher, lister et supprimer des clients.
 */
public interface ClientService {

    /**
     * Enregistre ou met à jour un client.
     *
     * @param dto l'objet {@link ClientDto} représentant le client à enregistrer ou mettre à jour
     * @return l'objet {@link ClientDto} enregistré ou mis à jour
     */
    ClientDto save(ClientDto dto);

    /**
     * Recherche un client par son identifiant.
     *
     * @param id l'identifiant du client
     * @return l'objet {@link ClientDto} correspondant à l'identifiant fourni
     */
    ClientDto findById(Integer id);

    /**
     * Récupère tous les clients.
     *
     * @return une liste d'objets {@link ClientDto} représentant tous les clients
     */
    List<ClientDto> findAll();

    /**
     * Supprime un client par son identifiant.
     *
     * @param id l'identifiant du client à supprimer
     */
    void delete(Integer id);
}
