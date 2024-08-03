package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.ClientDto;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.model.InterventionClient;
import com.macspace.gestiondestock.repository.ClientRepository;
import com.macspace.gestiondestock.repository.InterventionClientRepository;
import com.macspace.gestiondestock.services.ClientService;
import com.macspace.gestiondestock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service d'implémentation pour la gestion des clients.
 */
@Service
@Slf4j
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private InterventionClientRepository interventionClientRepository;

    /**
     * Constructeur avec injection de dépendances pour les repositories de clients et d'interventions clients.
     *
     * @param clientRepository le repository pour les clients
     * @param interventionClientRepository le repository pour les interventions clients
     */
    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, InterventionClientRepository interventionClientRepository) {
        this.clientRepository = clientRepository;
        this.interventionClientRepository = interventionClientRepository;
    }

    /**
     * Enregistre ou met à jour un client.
     *
     * @param dto le DTO du client à enregistrer ou mettre à jour
     * @return le DTO du client enregistré ou mis à jour
     * @throws InvalidEntityException si le client n'est pas valide
     */
    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Client is not valid {}", dto);
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID, errors);
        }

        return ClientDto.fromEntity(
                clientRepository.save(
                        ClientDto.toEntity(dto)
                )
        );
    }

    /**
     * Recherche un client par son identifiant.
     *
     * @param id l'identifiant du client
     * @return le DTO du client trouvé
     * @throws EntityNotFoundException si aucun client n'est trouvé avec l'identifiant donné
     */
    @Override
    public ClientDto findById(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return null;
        }
        return clientRepository.findById(id)
                .map(ClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun Client avec l'ID = " + id + " n'a ete trouve dans la Base de donnée",
                        ErrorCodes.CLIENT_NOT_FOUND)
                );
    }

    /**
     * Retourne la liste de tous les clients.
     *
     * @return la liste des DTOs des clients
     */
    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Supprime un client par son identifiant.
     *
     * @param id l'identifiant du client à supprimer
     * @throws InvalidOperationException si le client a des interventions clients associées
     */
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return;
        }
        List<InterventionClient> commandeClients = interventionClientRepository.findAllByClientId(id);
        if (!commandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un client qui a deja des interventions clients",
                    ErrorCodes.CLIENT_ALREADY_IN_USE);
        }
        clientRepository.deleteById(id);
    }
}
