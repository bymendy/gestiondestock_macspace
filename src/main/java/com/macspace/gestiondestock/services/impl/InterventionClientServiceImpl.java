package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.*;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.model.*;
import com.macspace.gestiondestock.repository.ClientRepository;
import com.macspace.gestiondestock.repository.InterventionClientRepository;
import com.macspace.gestiondestock.repository.LigneInterventionClientRepository;
import com.macspace.gestiondestock.repository.ProduitRepository;
import com.macspace.gestiondestock.services.InterventionClientService;
import com.macspace.gestiondestock.services.MvtStkService;
import com.macspace.gestiondestock.validator.InterventionClientValidator;
import com.macspace.gestiondestock.validator.ProduitsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InterventionClientServiceImpl implements InterventionClientService {

    private InterventionClientRepository interventionClientRepository;
    private LigneInterventionClientRepository ligneInterventionClientRepository;
    private ClientRepository clientRepository;
    private ProduitRepository produitRepository;
    private MvtStkService mvtStkService;

    @Autowired
    public InterventionClientServiceImpl(InterventionClientRepository interventionClientRepository,
                                         ClientRepository clientRepository, ProduitRepository produitRepository, LigneInterventionClientRepository ligneInterventionClientRepository,
                                         MvtStkService mvtStkService) {
        this.interventionClientRepository = interventionClientRepository;
        this.ligneInterventionClientRepository = ligneInterventionClientRepository;
        this.clientRepository = clientRepository;
        this.produitRepository = produitRepository;
        this.mvtStkService = mvtStkService;
    }
    @Override
    public InterventionClientDto save(InterventionClientDto dto) {
        List<String> errors = InterventionClientValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("Intervention client n'est pas valide");
            throw new InvalidEntityException("L'Intervention client n'est pas valide", ErrorCodes.INTERVENTION_CLIENT_NOT_VALID, errors);
        }

        if (dto.getId() != null && dto.isInterventionTerminee()) {
            throw new InvalidOperationException("Impossible de modifier l'Intervention lorsqu'elle est terminee", ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE);
        }

        Optional<Client> client = clientRepository.findById(dto.getClient().getId());
        if (client.isEmpty()) {
            log.warn("Client with ID {} was not found in the DB", dto.getClient().getId());
            throw new EntityNotFoundException("Aucun client avec l'ID" + dto.getClient().getId() + " n'a ete trouve dans la BDD",
                    ErrorCodes.CLIENT_NOT_FOUND);
        }

        List<String> produitErrors = new ArrayList<>();

        if (dto.getLigneInterventionClients() != null) {
            dto.getLigneInterventionClients().forEach(ligIntClt -> {
                if (ligIntClt.getProduit() != null) {
                    Optional<Produits> produit = produitRepository.findById(ligIntClt.getProduit().getId());
                    if (produit.isEmpty()) {
                        produitErrors.add("Le produit avec l'ID " + ligIntClt.getProduit().getId() + " n'existe pas");
                    }
                } else {
                    produitErrors.add("Impossible d'enregister une commande avec un aticle NULL");
                }
            });
        }

        if (!produitErrors.isEmpty()) {
            log.warn("");
            throw new InvalidEntityException("Produit n'existe pas dans la BDD", ErrorCodes.PRODUIT_NOT_FOUND, produitErrors);
        }
        dto.setDateIntervention(Instant.now());
        InterventionClient savedIntClt = interventionClientRepository.save(InterventionClientDto.toEntity(dto));

        if (dto.getLigneInterventionClients() != null) {
            dto.getLigneInterventionClients().forEach(ligIntClt -> {
                LigneInterventionClient ligneInterventionClient = LigneInterventionClientDto.toEntity(ligIntClt);
                ligneInterventionClient.setInterventionClient(savedIntClt);
                LigneInterventionClient savedLigneInt = ligneInterventionClientRepository.save(ligneInterventionClient);

                effectuerSortie(savedLigneInt);
            });
        }

        return InterventionClientDto.fromEntity(savedIntClt);


    }

    @Override
    public InterventionClientDto findById(Integer id) {
        if (id == null) {
            log.error("Intervention client ID is NULL");
            return null;
        }
        return interventionClientRepository.findById(id)
                .map(InterventionClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune Intervention client n'a ete trouve avec l'ID " + id, ErrorCodes.INTERVENTION_CLIENT_NOT_FOUND
                ));
    }

    @Override
    public InterventionClientDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Intervention client CODE is NULL");
            return null;
        }
        return interventionClientRepository.findInterventionClientByCode(code)
                .map(InterventionClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune Intervention client n'a ete trouve avec le CODE " + code, ErrorCodes.INTERVENTION_CLIENT_NOT_FOUND
                ));
    }

    @Override
    public List<InterventionClientDto> findAll() {
        return interventionClientRepository.findAll().stream()
                .map(InterventionClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Commande client ID is NULL");
            return;
        }
        List<LigneInterventionClient> ligneInterventionClients = ligneInterventionClientRepository.findAllByInterventionClientId(id);
        if (!ligneInterventionClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer une commande client deja utilisee",
                    ErrorCodes.INTERVENTION_CLIENT_ALREADY_IN_USE);
        }
        interventionClientRepository.deleteById(id);
    }

    @Override
    public List<LigneInterventionClientDto> findAllLignesInterventionsClientByInterventionClientId(Integer idIntervention) {
        return ligneInterventionClientRepository.findAllByInterventionClientId(idIntervention).stream()
                .map(LigneInterventionClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public InterventionClientDto updateEtatIntervention(Integer idIntervention, EtatIntervention etatIntervention) {
        checkIdIntervention(idIntervention);
        if (!StringUtils.hasLength(String.valueOf(etatIntervention))) {
            log.error("L'etat de l'Intervention client is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de l'Intervention avec un etat null",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE);
        }
        InterventionClientDto interventionClient = checkEtatIntervention(idIntervention);
        interventionClient.setEtatIntervention(etatIntervention);

        InterventionClient savedCmdClt = interventionClientRepository.save(InterventionClientDto.toEntity(interventionClient));
        if (interventionClient.isInterventionTerminee()) {
            updateMvtStk(idIntervention);
        }

        return InterventionClientDto.fromEntity(savedCmdClt);
    }

    @Override
    public InterventionClientDto updateQuantiteIntervention(Integer idIntervention, Integer idLigneIntervention, BigDecimal quantite) {
        checkIdIntervention(idIntervention);
        checkIdLigneIntervention(idLigneIntervention);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("L'ID de la ligne Intervention is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de l'Intervention avec une quantite null ou ZERO",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE);
        }

        InterventionClientDto interventionClient = checkEtatIntervention(idIntervention);
        Optional<LigneInterventionClient> ligneInterventionClientOptional = findLigneInterventionClient(idLigneIntervention);

        LigneInterventionClient ligneInterventionClient = ligneInterventionClientOptional.get();
        ligneInterventionClient.setQuantite(quantite);
        ligneInterventionClientRepository.save(ligneInterventionClient);

        return interventionClient;
    }


    @Override
    public InterventionClientDto updateClient(Integer idIntervention, Integer idClient) {
        checkIdIntervention(idIntervention);
        if (idClient == null) {
            log.error("L'ID du client is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de l'Intervention avec un ID client null",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE);
        }
        InterventionClientDto interventionClient = checkEtatIntervention(idIntervention);
        Optional<Client> clientOptional = clientRepository.findById(idClient);
        if (clientOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucun client n'a ete trouve avec l'ID " + idClient, ErrorCodes.CLIENT_NOT_FOUND);
        }
        interventionClient.setClient(ClientDto.fromEntity(clientOptional.get()));

        return InterventionClientDto.fromEntity(
                interventionClientRepository.save(InterventionClientDto.toEntity(interventionClient))
        );
    }

    @Override
    public InterventionClientDto updateProduit(Integer idIntervention, Integer idLigneIntervention, Integer idProduit) {
        checkIdIntervention(idIntervention);
        checkIdLigneIntervention(idLigneIntervention);
        checkIdProduit(idProduit, "nouveau");

        InterventionClientDto interventionClient = checkEtatIntervention(idIntervention);

        Optional<LigneInterventionClient> ligneInterventionClient = findLigneInterventionClient(idLigneIntervention);

        Optional<Produits> produitOptional = produitRepository.findById(idProduit);
        if (produitOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucun produit n'a ete trouve avec l'ID " + idProduit, ErrorCodes.PRODUIT_NOT_FOUND);
        }

        List<String> errors = ProduitsValidator.validate(ProduitDto.fromEntity(produitOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Produit invalid", ErrorCodes.PRODUIT_NOT_VALID, errors);
        }

        LigneInterventionClient ligneInterventionClientToSaved = ligneInterventionClient.get();
        ligneInterventionClientToSaved.setProduit(produitOptional.get());
        ligneInterventionClientRepository.save(ligneInterventionClientToSaved);

        return interventionClient;
    }

    @Override
    public InterventionClientDto deleteProduit(Integer idIntervention, Integer idLigneIntervention) {
        checkIdIntervention(idIntervention);
        checkIdLigneIntervention(idLigneIntervention);

        InterventionClientDto interventionClient = checkEtatIntervention(idIntervention);
        // Just to check the LigneInterventionClient and inform the client in case it is absent
        findLigneInterventionClient(idLigneIntervention);
        ligneInterventionClientRepository.deleteById(idLigneIntervention);

        return interventionClient;
    }


    private InterventionClientDto checkEtatIntervention(Integer idIntervention) {
        InterventionClientDto interventionClient = findById(idIntervention);
        if (interventionClient.isInterventionTerminee()) {
            throw new InvalidOperationException("Impossible de modifier l'intervention lorsqu'elle est terminer", ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE);
        }
        return interventionClient;
    }

    private Optional<LigneInterventionClient> findLigneInterventionClient(Integer idLigneIntervention) {
        Optional<LigneInterventionClient> ligneInterventionClientOptional = ligneInterventionClientRepository.findById(idLigneIntervention);
        if (ligneInterventionClientOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne Intervention client n'a ete trouve avec l'ID " + idLigneIntervention, ErrorCodes.INTERVENTION_CLIENT_NOT_FOUND);
        }
        return ligneInterventionClientOptional;
    }

    private void checkIdIntervention(Integer idIntervention) {
        if (idIntervention == null) {
            log.error("Intervention client ID is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de l'Intervention avec un ID null",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE);
        }
    }

    private void checkIdLigneIntervention(Integer idLigneIntervention) {
        if (idLigneIntervention == null) {
            log.error("L'ID de la ligne Intervention is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de l'Intervention avec une ligne d'Intervention null",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE);
        }
    }


    private void checkIdProduit(Integer idProduit, String msg) {
        if (idProduit == null) {
            log.error("L'ID de " + msg + " is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de l'intervention avec un " + msg + " ID produit null",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE);
        }
    }
    private void updateMvtStk(Integer idIntervention) {
        List<LigneInterventionClient> ligneInterventionClients = ligneInterventionClientRepository.findAllByInterventionClientId(idIntervention);
        ligneInterventionClients.forEach(lig -> {
            effectuerSortie(lig);
        });
    }

    private void effectuerSortie(LigneInterventionClient lig) {
        MvtStkDto mvtStkDto = MvtStkDto.builder()
                .produit(ProduitDto.fromEntity(lig.getProduit()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.SORTIE)
                .sourceMvt(SourceMvtStk.INTERVENTION_CLIENT)
                //.quantite(lig.getQuantite())
                //.idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStkService.sortieStock(mvtStkDto);
    }
}
