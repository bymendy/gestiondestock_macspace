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
import com.macspace.gestiondestock.validator.ProduitValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service pour la gestion des interventions clients dans MacSpace.
 *
 * Gère la création, la modification et la suppression des interventions clients.
 * Les entités liées (Client, Produit) sont récupérées directement
 * depuis les repositories pour éviter les problèmes d'entités détachées JPA.
 */
@Service
@Slf4j
public class InterventionClientServiceImpl implements InterventionClientService {

    private final InterventionClientRepository interventionClientRepository;
    private final LigneInterventionClientRepository ligneInterventionClientRepository;
    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;
    private final MvtStkService mvtStkService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param interventionClientRepository      Repository JPA des interventions clients.
     * @param clientRepository                  Repository JPA des clients.
     * @param produitRepository                 Repository JPA des produits.
     * @param ligneInterventionClientRepository Repository JPA des lignes d'intervention.
     * @param mvtStkService                     Service de gestion des mouvements de stock.
     */
    @Autowired
    public InterventionClientServiceImpl(
            InterventionClientRepository interventionClientRepository,
            ClientRepository clientRepository,
            ProduitRepository produitRepository,
            LigneInterventionClientRepository ligneInterventionClientRepository,
            MvtStkService mvtStkService) {
        this.interventionClientRepository = interventionClientRepository;
        this.ligneInterventionClientRepository = ligneInterventionClientRepository;
        this.clientRepository = clientRepository;
        this.produitRepository = produitRepository;
        this.mvtStkService = mvtStkService;
    }

    /**
     * {@inheritDoc}
     * Vérifie l'existence du client et des produits avant sauvegarde.
     * Utilise les entités attachées à la session Hibernate.
     */
    @Override
    public InterventionClientDto save(InterventionClientDto dto) {
        List<String> errors = InterventionClientValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("L'intervention client n'est pas valide");
            throw new InvalidEntityException(
                    "L'intervention client n'est pas valide",
                    ErrorCodes.INTERVENTION_CLIENT_NOT_VALID,
                    errors
            );
        }
        if (dto.getId() != null && dto.isInterventionTerminee()) {
            throw new InvalidOperationException(
                    "Impossible de modifier l'intervention lorsqu'elle est terminée",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE
            );
        }

        // Récupérer le client attaché à la session
        Client client = clientRepository.findById(dto.getClient().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun client avec l'ID "
                                + dto.getClient().getId() + " n'a été trouvé",
                        ErrorCodes.CLIENT_NOT_FOUND
                ));

        // Vérifier l'existence des produits
        List<String> produitErrors = new ArrayList<>();
        if (dto.getLigneInterventionClients() != null) {
            dto.getLigneInterventionClients().forEach(ligIntClt -> {
                if (ligIntClt.getProduit() != null) {
                    Optional<Produit> produit = produitRepository
                            .findById(ligIntClt.getProduit().getId());
                    if (produit.isEmpty()) {
                        produitErrors.add("Le produit avec l'ID "
                                + ligIntClt.getProduit().getId() + " n'existe pas");
                    }
                } else {
                    produitErrors.add(
                            "Impossible d'enregistrer une intervention avec un produit NULL"
                    );
                }
            });
        }
        if (!produitErrors.isEmpty()) {
            log.warn("Produits non trouvés dans la base de données");
            throw new InvalidEntityException(
                    "Le produit n'existe pas dans la base de données",
                    ErrorCodes.PRODUIT_NOT_FOUND,
                    produitErrors
            );
        }

        // Construire l'intervention client avec le client attaché
        dto.setDateIntervention(Instant.now());
        InterventionClient interventionClient = new InterventionClient();
        interventionClient.setCode(dto.getCode());
        interventionClient.setDateIntervention(dto.getDateIntervention());
        interventionClient.setEtatIntervention(dto.getEtatIntervention());
        interventionClient.setClient(client);
        interventionClient.setIdEntreprise(dto.getIdEntreprise());

        InterventionClient savedIntClt = interventionClientRepository
                .save(interventionClient);

        // Sauvegarder les lignes d'intervention
        if (dto.getLigneInterventionClients() != null) {
            dto.getLigneInterventionClients().forEach(ligIntClt -> {
                // Récupérer le produit attaché
                Produit produit = produitRepository
                        .findById(ligIntClt.getProduit().getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Produit non trouvé",
                                ErrorCodes.PRODUIT_NOT_FOUND
                        ));

                LigneInterventionClient ligne = new LigneInterventionClient();
                ligne.setInterventionClient(savedIntClt);
                ligne.setProduit(produit);
                ligne.setQuantite(ligIntClt.getQuantite());
                ligne.setNumeroContrat(ligIntClt.getNumeroContrat());
                ligne.setProblematique(ligIntClt.getProblematique());
                ligne.setIdEntreprise(savedIntClt.getIdEntreprise());

                LigneInterventionClient savedLigne =
                        ligneInterventionClientRepository.save(ligne);
                effectuerSortie(savedLigne);
            });
        }
        InterventionClient reloadedIntClt = interventionClientRepository
                .findById(savedIntClt.getId())
                .orElse(savedIntClt);
        return InterventionClientDto.fromEntity(reloadedIntClt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionClientDto findById(Integer id) {
        if (id == null) {
            log.error("L'ID de l'intervention client est nul");
            return null;
        }
        return interventionClientRepository.findById(id)
                .map(InterventionClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune intervention client avec l'ID " + id + " n'a été trouvée",
                        ErrorCodes.INTERVENTION_CLIENT_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionClientDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Le code de l'intervention client est nul");
            return null;
        }
        return interventionClientRepository.findInterventionClientByCode(code)
                .map(InterventionClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune intervention client avec le CODE " + code + " n'a été trouvée",
                        ErrorCodes.INTERVENTION_CLIENT_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InterventionClientDto> findAll() {
        return interventionClientRepository.findAll().stream()
                .map(InterventionClientDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InterventionClientDto> findAllByClient(Integer idClient) {
        return interventionClientRepository.findAllByClientId(idClient)
                .stream()
                .map(InterventionClientDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InterventionClientDto> findAllByEtatIntervention(
            EtatIntervention etatIntervention) {
        return interventionClientRepository
                .findAllByEtatIntervention(etatIntervention)
                .stream()
                .map(InterventionClientDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InterventionClientDto> findAllByIdEntreprise(Integer idEntreprise) {
        return interventionClientRepository.findAllByIdEntreprise(idEntreprise)
                .stream()
                .map(InterventionClientDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LigneInterventionClientDto> findAllLignesInterventionsClientByInterventionClientId(
            Integer idIntervention) {
        return ligneInterventionClientRepository
                .findAllByInterventionClientId(idIntervention).stream()
                .map(LigneInterventionClientDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID de l'intervention client est nul");
            return;
        }
        List<LigneInterventionClient> ligneInterventionClients =
                ligneInterventionClientRepository.findAllByInterventionClientId(id);
        if (!ligneInterventionClients.isEmpty()) {
            throw new InvalidOperationException(
                    "Impossible de supprimer une intervention client déjà utilisée",
                    ErrorCodes.INTERVENTION_CLIENT_ALREADY_IN_USE
            );
        }
        interventionClientRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     * Récupère l'intervention depuis la BDD et met à jour son état.
     */
    @Override
    public InterventionClientDto updateEtatIntervention(Integer idIntervention,
                                                        EtatIntervention etatIntervention) {
        checkIdIntervention(idIntervention);
        if (etatIntervention == null) {
            log.error("L'état de l'intervention client est nul");
            throw new InvalidOperationException(
                    "Impossible de modifier l'état de l'intervention avec un état null",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE
            );
        }
        InterventionClientDto interventionClientDto =
                checkEtatIntervention(idIntervention);

        // Récupérer l'entité attachée et modifier directement
        InterventionClient interventionClient = interventionClientRepository
                .findById(idIntervention)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Intervention client non trouvée",
                        ErrorCodes.INTERVENTION_CLIENT_NOT_FOUND
                ));
        interventionClient.setEtatIntervention(etatIntervention);
        InterventionClient savedIntClt = interventionClientRepository
                .save(interventionClient);

        if (interventionClientDto.isInterventionTerminee()) {
            updateMvtStk(idIntervention);
        }
        return InterventionClientDto.fromEntity(savedIntClt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionClientDto updateQuantiteIntervention(Integer idIntervention,
                                                            Integer idLigneIntervention,
                                                            BigDecimal quantite) {
        checkIdIntervention(idIntervention);
        checkIdLigneIntervention(idLigneIntervention);
        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("La quantité de la ligne d'intervention est invalide");
            throw new InvalidOperationException(
                    "Impossible de modifier l'intervention avec une quantité nulle ou inférieure à 0",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE
            );
        }
        InterventionClientDto interventionClient =
                checkEtatIntervention(idIntervention);
        Optional<LigneInterventionClient> ligneInterventionClientOptional =
                findLigneInterventionClient(idLigneIntervention);
        LigneInterventionClient ligneInterventionClient =
                ligneInterventionClientOptional.get();
        ligneInterventionClient.setQuantite(quantite);
        ligneInterventionClientRepository.save(ligneInterventionClient);
        return interventionClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionClientDto updateClient(Integer idIntervention,
                                              Integer idClient) {
        checkIdIntervention(idIntervention);
        if (idClient == null) {
            log.error("L'ID du client est nul");
            throw new InvalidOperationException(
                    "Impossible de modifier l'intervention avec un ID client null",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE
            );
        }
        checkEtatIntervention(idIntervention);

        // Récupérer les entités attachées
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun client avec l'ID " + idClient + " n'a été trouvé",
                        ErrorCodes.CLIENT_NOT_FOUND
                ));
        InterventionClient intervention = interventionClientRepository
                .findById(idIntervention)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Intervention client non trouvée",
                        ErrorCodes.INTERVENTION_CLIENT_NOT_FOUND
                ));
        intervention.setClient(client);
        return InterventionClientDto.fromEntity(
                interventionClientRepository.save(intervention)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionClientDto updateProduit(Integer idIntervention,
                                               Integer idLigneIntervention,
                                               Integer idProduit) {
        checkIdIntervention(idIntervention);
        checkIdLigneIntervention(idLigneIntervention);
        checkIdProduit(idProduit, "nouveau");
        InterventionClientDto interventionClient =
                checkEtatIntervention(idIntervention);
        Optional<LigneInterventionClient> ligneInterventionClient =
                findLigneInterventionClient(idLigneIntervention);
        Produit produit = produitRepository.findById(idProduit)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun produit avec l'ID " + idProduit + " n'a été trouvé",
                        ErrorCodes.PRODUIT_NOT_FOUND
                ));
        List<String> errors = ProduitValidator.validate(
                ProduitDto.fromEntity(produit));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException(
                    "Produit invalide",
                    ErrorCodes.PRODUIT_NOT_VALID,
                    errors
            );
        }
        LigneInterventionClient ligneToSave = ligneInterventionClient.get();
        ligneToSave.setProduit(produit);
        ligneInterventionClientRepository.save(ligneToSave);
        return interventionClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionClientDto deleteProduit(Integer idIntervention,
                                               Integer idLigneIntervention) {
        checkIdIntervention(idIntervention);
        checkIdLigneIntervention(idLigneIntervention);
        InterventionClientDto interventionClient =
                checkEtatIntervention(idIntervention);
        findLigneInterventionClient(idLigneIntervention);
        ligneInterventionClientRepository.deleteById(idLigneIntervention);
        return interventionClient;
    }

    // ===== Méthodes privées =====

    /**
     * Vérifie et retourne l'intervention si elle n'est pas terminée.
     *
     * @param idIntervention L'ID de l'intervention à vérifier.
     * @return Le DTO de l'intervention si modifiable.
     * @throws InvalidOperationException Si l'intervention est terminée.
     */
    private InterventionClientDto checkEtatIntervention(Integer idIntervention) {
        InterventionClientDto interventionClient = findById(idIntervention);
        if (interventionClient.isInterventionTerminee()) {
            throw new InvalidOperationException(
                    "Impossible de modifier l'intervention lorsqu'elle est terminée",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE
            );
        }
        return interventionClient;
    }

    /**
     * Recherche une ligne d'intervention client par son ID.
     *
     * @param idLigneIntervention L'ID de la ligne d'intervention.
     * @return La ligne d'intervention trouvée.
     * @throws EntityNotFoundException Si la ligne n'existe pas.
     */
    private Optional<LigneInterventionClient> findLigneInterventionClient(
            Integer idLigneIntervention) {
        Optional<LigneInterventionClient> ligneOptional =
                ligneInterventionClientRepository.findById(idLigneIntervention);
        if (ligneOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne d'intervention client avec l'ID "
                            + idLigneIntervention + " n'a été trouvée",
                    ErrorCodes.INTERVENTION_CLIENT_NOT_FOUND
            );
        }
        return ligneOptional;
    }

    /**
     * Vérifie que l'ID de l'intervention n'est pas nul.
     *
     * @param idIntervention L'ID à vérifier.
     * @throws InvalidOperationException Si l'ID est nul.
     */
    private void checkIdIntervention(Integer idIntervention) {
        if (idIntervention == null) {
            log.error("L'ID de l'intervention client est nul");
            throw new InvalidOperationException(
                    "Impossible de modifier l'intervention avec un ID null",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE
            );
        }
    }

    /**
     * Vérifie que l'ID de la ligne d'intervention n'est pas nul.
     *
     * @param idLigneIntervention L'ID à vérifier.
     * @throws InvalidOperationException Si l'ID est nul.
     */
    private void checkIdLigneIntervention(Integer idLigneIntervention) {
        if (idLigneIntervention == null) {
            log.error("L'ID de la ligne d'intervention est nul");
            throw new InvalidOperationException(
                    "Impossible de modifier l'intervention avec une ligne d'intervention null",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE
            );
        }
    }

    /**
     * Vérifie que l'ID du produit n'est pas nul.
     *
     * @param idProduit L'ID à vérifier.
     * @param msg       Message contextuel pour le log.
     * @throws InvalidOperationException Si l'ID est nul.
     */
    private void checkIdProduit(Integer idProduit, String msg) {
        if (idProduit == null) {
            log.error("L'ID du {} produit est nul", msg);
            throw new InvalidOperationException(
                    "Impossible de modifier l'intervention avec un ID "
                            + msg + " produit null",
                    ErrorCodes.INTERVENTION_CLIENT_NON_MODIFIABLE
            );
        }
    }

    /**
     * Met à jour les mouvements de stock pour une intervention terminée.
     *
     * @param idIntervention L'ID de l'intervention terminée.
     */
    private void updateMvtStk(Integer idIntervention) {
        List<LigneInterventionClient> ligneInterventionClients =
                ligneInterventionClientRepository
                        .findAllByInterventionClientId(idIntervention);
        ligneInterventionClients.forEach(this::effectuerSortie);
    }

    /**
     * Enregistre un mouvement de sortie de stock pour une ligne d'intervention.
     *
     * @param lig La ligne d'intervention client.
     */
    private void effectuerSortie(LigneInterventionClient lig) {
        MvtStkDto mvtStkDto = MvtStkDto.builder()
                .produit(ProduitDto.fromEntity(lig.getProduit()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.SORTIE)
                .sourceMvt(SourceMvtStk.INTERVENTION_CLIENT)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStkService.sortieStock(mvtStkDto);
    }
}