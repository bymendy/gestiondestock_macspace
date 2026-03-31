package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.AdresseDto;
import com.macspace.gestiondestock.dto.ClientDto;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.model.Adresse;
import com.macspace.gestiondestock.model.Client;
import com.macspace.gestiondestock.model.InterventionClient;
import com.macspace.gestiondestock.repository.AdresseRepository;
import com.macspace.gestiondestock.repository.ClientRepository;
import com.macspace.gestiondestock.repository.InterventionClientRepository;
import com.macspace.gestiondestock.services.ClientService;
import com.macspace.gestiondestock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation du service pour la gestion des clients dans MacSpace.
 *
 * Gère la création, la recherche et la suppression des clients.
 * L'adresse est persistée avant le client pour respecter
 * les contraintes de clé étrangère JPA.
 */
@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final InterventionClientRepository interventionClientRepository;
    private final AdresseRepository adresseRepository;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param clientRepository             Repository JPA des clients.
     * @param interventionClientRepository Repository JPA des interventions clients.
     * @param adresseRepository            Repository JPA des adresses.
     */
    @Autowired
    public ClientServiceImpl(
            ClientRepository clientRepository,
            InterventionClientRepository interventionClientRepository,
            AdresseRepository adresseRepository) {
        this.clientRepository = clientRepository;
        this.interventionClientRepository = interventionClientRepository;
        this.adresseRepository = adresseRepository;
    }

    /**
     * {@inheritDoc}
     * Sauvegarde l'adresse avant le client si elle existe.
     */
    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Le client n'est pas valide {}", dto);
            throw new InvalidEntityException(
                    "Le client n'est pas valide",
                    ErrorCodes.CLIENT_NOT_VALID,
                    errors
            );
        }

        // 1. Sauvegarder l'adresse en premier si elle existe
        Adresse adresseSauvegardee = null;
        if (dto.getAdresse() != null) {
            adresseSauvegardee = adresseRepository.save(
                    AdresseDto.toEntity(dto.getAdresse())
            );
            dto.setAdresse(AdresseDto.fromEntity(adresseSauvegardee));
        }

        // 2. Construire le client — UPDATE si id présent, INSERT sinon
        Client client;
        if (dto.getId() != null) {
            /* Mode modification — récupérer le client existant */
            client = clientRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Aucun client avec l'ID = " + dto.getId(),
                            ErrorCodes.CLIENT_NOT_FOUND
                    ));
        } else {
            /* Mode création */
            client = new Client();
        }

        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setEmail(dto.getEmail());
        client.setNumTel(dto.getNumTel());
        client.setPhoto(dto.getPhoto());
        client.setAdresse(adresseSauvegardee);
        client.setIdEntreprise(dto.getIdEntreprise());

        // 3. Sauvegarder le client
        return ClientDto.fromEntity(clientRepository.save(client));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientDto findById(Integer id) {
        if (id == null) {
            log.error("L'ID du client est nul");
            return null;
        }
        return clientRepository.findById(id)
                .map(ClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun client avec l'ID = " + id + " n'a été trouvé",
                        ErrorCodes.CLIENT_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientDto findByEmail(String email) {
        if (email == null || email.isBlank()) {
            log.error("L'email du client est nul");
            return null;
        }
        return clientRepository.findClientByEmail(email)
                .map(ClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun client avec l'email = " + email + " n'a été trouvé",
                        ErrorCodes.CLIENT_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClientDto> findAllByIdEntreprise(Integer idEntreprise) {
        return clientRepository.findAllByIdEntreprise(idEntreprise)
                .stream()
                .map(ClientDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID du client est nul");
            return;
        }
        List<InterventionClient> interventionClients =
                interventionClientRepository.findAllByClientId(id);
        if (!interventionClients.isEmpty()) {
            throw new InvalidOperationException(
                    "Impossible de supprimer un client qui a déjà des interventions",
                    ErrorCodes.CLIENT_ALREADY_IN_USE
            );
        }
        clientRepository.deleteById(id);
    }
}