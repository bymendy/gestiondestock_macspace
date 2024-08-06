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

/**
 * Implémentation du service pour la gestion des interventions client.
 */
@Service
@Slf4j
public class InterventionClientServiceImpl implements InterventionClientService {

    private InterventionClientRepository interventionClientRepository;
    private LigneInterventionClientRepository ligneInterventionClientRepository;
    private ClientRepository clientRepository;
    private ProduitRepository produitRepository;
    private MvtStkService mvtStkService;

    /**
     * Construit une nouvelle instance de {@link InterventionClientServiceImpl} avec les dépôts et services spécifiés.
     *
     * @param interventionClientRepository le dépôt pour les interventions client
     * @param clientRepository le dépôt pour les clients
     * @param produitRepository le dépôt pour les produits
     * @param ligneInterventionClientRepository le dépôt pour les lignes d'intervention client
     * @param mvtStkService le service pour les mouvements de stock
     */
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

    /**
     * Enregistre une nouvelle intervention client ou met à jour une intervention existante.
     *
     * @param dto l'objet DTO de l'intervention client à enregistrer ou mettre à jour
     * @return l'objet DTO de l'intervention client enregistrée ou mise à jour
     * @throws InvalidEntityException si l'intervention client est invalide ou si des produits sont inexistants
     * @throws InvalidOperationException si l'intervention est terminée et ne peut être modifiée
     */
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

    /**
     * Trouve une intervention client par son ID.
     *
     * @param id l'ID de l'intervention client
     * @return l'objet DTO de l'intervention client trouvée
     * @throws EntityNotFoundException si aucune intervention client n'est trouvée avec l'ID donné
     */
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

    /**
     * Trouve une intervention client par son code.
     *
     * @param code le code de l'intervention client
     * @return l'objet DTO de l'intervention client trouvée
     * @throws EntityNotFoundException si aucune intervention client n'est trouvée avec le code donné
     */
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

    /**
     * Récupère toutes les interventions client.
     *
     * @return une liste de tous les objets DTO des interventions client
     */
    @Override
    public List<InterventionClientDto> findAll() {
        return interventionClientRepository.findAll().stream()
                .map(InterventionClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Supprime une intervention client par son ID.
     *
     * @param id l'ID de l'intervention client à supprimer
     * @throws InvalidOperationException si l'intervention client est déjà utilisée dans des lignes d'intervention
     */
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

    /**
     * Récupère toutes les lignes d'intervention client pour un ID d'intervention donné.
     *
     * @param idIntervention l'ID de l'intervention client
     * @return une liste de tous les objets DTO des lignes d'intervention client
     */
    @Override
    public List<LigneInterventionClientDto> findAllLignesInterventionsClientByInterventionClientId(Integer idIntervention) {
        return ligneInterventionClientRepository.findAllByInterventionClientId(idIntervention).stream()
                .map(LigneInterventionClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Met à jour l'état d'une intervention client.
     *
     * @param idIntervention l'ID de l'intervention à mettre à jour
     * @param etatIntervention le nouvel état de l'intervention
     * @return l'objet DTO de l'intervention client mise à jour
     * @throws InvalidOperationException si l'état est nul ou si l'intervention est terminée
     */
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

    /**
     * Met à jour la quantité d'une ligne d'intervention client.
     *
     * @param idIntervention l'ID de l'intervention
     * @param idLigneIntervention l'ID de la ligne d'intervention à mettre à jour
     * @param quantite la nouvelle quantité
     * @return l'objet DTO de l'intervention client mise à jour
     * @throws InvalidOperationException si la quantité est nulle ou zéro
     */
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

    /**
     * Met à jour le client d'une intervention.
     *
     * @param idIntervention l'ID de l'intervention à mettre à jour
     * @param idClient l'ID du nouveau client
     * @return l'objet DTO de l'intervention client mise à jour
     * @throws InvalidOperationException si l'ID du client est nul
     * @throws EntityNotFoundException si aucun client n'est trouvé avec l'ID donné
     */
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

    /**
     * Met à jour le produit d'une ligne d'intervention client.
     *
     * @param idIntervention l'ID de l'intervention
     * @param idLigneIntervention l'ID de la ligne d'intervention
     * @param idProduit l'ID du nouveau produit
     * @return l'objet DTO de l'intervention client mise à jour
     * @throws InvalidOperationException si l'ID du produit est nul
     * @throws EntityNotFoundException si aucun produit n'est trouvé avec l'ID donné
     * @throws InvalidEntityException si le produit est invalide
     */
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

    /**
     * Supprime un produit d'une ligne d'intervention client.
     *
     * @param idIntervention l'ID de l'intervention
     * @param idLigneIntervention l'ID de la ligne d'intervention
     * @return l'objet DTO de l'intervention client mise à jour
     * @throws InvalidOperationException si l'ID de l'intervention ou de la ligne d'intervention est nul
     */
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


    /**
     * Vérifie l'état d'une intervention client avant modification.
     *
     * @param idIntervention l'ID de l'intervention à vérifier
     * @return l'objet DTO de l'intervention client si l'état est modifiable
     * @throws InvalidOperationException si l'intervention est déjà terminée
     */
    private InterventionClientDto checkEtatIntervention(Integer idIntervention) {
        InterventionClientDto interventionClient = findById(idIntervention);
        if (interventionClient.isInterventionTerminee()) {
            throw new InvalidOperationException("Impossible de modifier l'intervention lorsqu'elle est terminer", ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE);
        }
        return interventionClient;
    }

    /**
     * Trouve une ligne d'intervention client par son ID.
     *
     * @param idLigneIntervention l'ID de la ligne d'intervention
     * @return une option contenant la ligne d'intervention client si trouvée
     * @throws EntityNotFoundException si aucune ligne d'intervention client n'est trouvée avec l'ID donné
     */
    private Optional<LigneInterventionClient> findLigneInterventionClient(Integer idLigneIntervention) {
        Optional<LigneInterventionClient> ligneInterventionClientOptional = ligneInterventionClientRepository.findById(idLigneIntervention);
        if (ligneInterventionClientOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne Intervention client n'a ete trouve avec l'ID " + idLigneIntervention, ErrorCodes.INTERVENTION_CLIENT_NOT_FOUND);
        }
        return ligneInterventionClientOptional;
    }

    /**
     * Vérifie que l'ID de l'intervention client n'est pas nul.
     *
     * @param idIntervention l'ID de l'intervention à vérifier
     * @throws InvalidOperationException si l'ID est nul
     */
    private void checkIdIntervention(Integer idIntervention) {
        if (idIntervention == null) {
            log.error("Intervention client ID is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de l'Intervention avec un ID null",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE);
        }
    }

    /**
     * Vérifie que l'ID de la ligne d'intervention n'est pas nul.
     *
     * @param idLigneIntervention l'ID de la ligne d'intervention à vérifier
     * @throws InvalidOperationException si l'ID est nul
     */
    private void checkIdLigneIntervention(Integer idLigneIntervention) {
        if (idLigneIntervention == null) {
            log.error("L'ID de la ligne Intervention is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de l'Intervention avec une ligne d'Intervention null",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE);
        }
    }

    /**
     * Vérifie que l'ID du produit n'est pas nul.
     *
     * @param idProduit l'ID du produit à vérifier
     * @param msg le message utilisé dans le cas d'erreur
     * @throws InvalidOperationException si l'ID est nul
     */
    private void checkIdProduit(Integer idProduit, String msg) {
        if (idProduit == null) {
            log.error("L'ID de " + msg + " is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de l'intervention avec un " + msg + " ID produit null",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE);
        }
    }

    /**
     * Met à jour les mouvements de stock pour une intervention client terminée.
     *
     * @param idIntervention l'ID de l'intervention client dont les mouvements de stock doivent être mis à jour
     */
    private void updateMvtStk(Integer idIntervention) {
        List<LigneInterventionClient> ligneInterventionClients = ligneInterventionClientRepository.findAllByInterventionClientId(idIntervention);
        ligneInterventionClients.forEach(lig -> {
            effectuerSortie(lig);
        });
    }

    /**
     * Effectue une sortie de stock pour une ligne d'intervention client donnée.
     *
     * @param lig la ligne d'intervention client pour laquelle la sortie doit être effectuée
     */
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
