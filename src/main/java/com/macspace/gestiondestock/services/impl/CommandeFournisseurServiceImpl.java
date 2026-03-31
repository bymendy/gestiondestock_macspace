package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.*;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.model.*;
import com.macspace.gestiondestock.repository.CommandeFournisseurRepository;
import com.macspace.gestiondestock.repository.FournisseurRepository;
import com.macspace.gestiondestock.repository.LigneCommandeFournisseurRepository;
import com.macspace.gestiondestock.repository.ProduitRepository;
import com.macspace.gestiondestock.services.CommandeFournisseurService;
import com.macspace.gestiondestock.services.MvtStkService;
import com.macspace.gestiondestock.validator.CommandeFournisseurValidator;
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
 * Implémentation du service pour la gestion des commandes fournisseurs dans MacSpace.
 *
 * Gère la création, la modification et la suppression des commandes fournisseurs.
 * Les entités liées (Fournisseur, Produit) sont récupérées directement
 * depuis les repositories pour éviter les problèmes d'entités détachées JPA.
 */
@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private final CommandeFournisseurRepository commandeFournisseurRepository;
    private final LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ProduitRepository produitRepository;
    private final MvtStkService mvtStkService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param commandeFournisseurRepository      Repository JPA des commandes fournisseurs.
     * @param fournisseurRepository              Repository JPA des fournisseurs.
     * @param produitRepository                  Repository JPA des produits.
     * @param ligneCommandeFournisseurRepository Repository JPA des lignes de commande.
     * @param mvtStkService                      Service de gestion des mouvements de stock.
     */
    @Autowired
    public CommandeFournisseurServiceImpl(
            CommandeFournisseurRepository commandeFournisseurRepository,
            FournisseurRepository fournisseurRepository,
            ProduitRepository produitRepository,
            LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
            MvtStkService mvtStkService) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.produitRepository = produitRepository;
        this.mvtStkService = mvtStkService;
    }

    /**
     * {@inheritDoc}
     * Vérifie l'existence du fournisseur et des produits avant sauvegarde.
     * Utilise les entités attachées à la session Hibernate.
     */
    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        List<String> errors = CommandeFournisseurValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("La commande fournisseur n'est pas valide");
            throw new InvalidEntityException(
                    "La commande fournisseur n'est pas valide",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID,
                    errors
            );
        }
        if (dto.getId() != null && dto.isCommandeLivree()) {
            throw new InvalidOperationException(
                    "Impossible de modifier la commande lorsqu'elle est livrée",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }

        // Récupérer le fournisseur attaché à la session
        Fournisseur fournisseur = fournisseurRepository
                .findById(dto.getFournisseur().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun fournisseur avec l'ID "
                                + dto.getFournisseur().getId() + " n'a été trouvé",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND
                ));

        // Vérifier l'existence des produits
        List<String> produitErrors = new ArrayList<>();
        if (dto.getLigneCommandeFournisseurs() != null) {
            dto.getLigneCommandeFournisseurs().forEach(ligCmdFrs -> {
                if (ligCmdFrs.getProduit() != null) {
                    Optional<Produit> produit = produitRepository
                            .findById(ligCmdFrs.getProduit().getId());
                    if (produit.isEmpty()) {
                        produitErrors.add("Le produit avec l'ID "
                                + ligCmdFrs.getProduit().getId() + " n'existe pas");
                    }
                } else {
                    produitErrors.add(
                            "Impossible d'enregistrer une commande avec un produit NULL"
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

        // Construire la commande avec le fournisseur attaché
        dto.setDateCommande(Instant.now());
        CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
        commandeFournisseur.setCode(dto.getCode());
        commandeFournisseur.setDateCommande(dto.getDateCommande());
        commandeFournisseur.setEtatCommande(dto.getEtatCommande());
        commandeFournisseur.setFournisseur(fournisseur);
        commandeFournisseur.setIdEntreprise(dto.getIdEntreprise());

        CommandeFournisseur savedCmdFrs = commandeFournisseurRepository
                .save(commandeFournisseur);

        // Sauvegarder les lignes de commande
        if (dto.getLigneCommandeFournisseurs() != null) {
            dto.getLigneCommandeFournisseurs().forEach(ligCmdFrs -> {
                // Récupérer le produit attaché
                Produit produit = produitRepository
                        .findById(ligCmdFrs.getProduit().getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Produit non trouvé",
                                ErrorCodes.PRODUIT_NOT_FOUND
                        ));

                LigneCommandeFournisseur ligne = new LigneCommandeFournisseur();
                ligne.setCommandeFournisseur(savedCmdFrs);
                ligne.setProduit(produit);
                ligne.setQuantite(ligCmdFrs.getQuantite());
                ligne.setPrixUnitaire(ligCmdFrs.getPrixUnitaire());
                ligne.setIdEntreprise(savedCmdFrs.getIdEntreprise());

                LigneCommandeFournisseur saveLigne =
                        ligneCommandeFournisseurRepository.save(ligne);
                effectuerEntree(saveLigne);
            });
        }
        CommandeFournisseur reloadedCmdFrs = commandeFournisseurRepository
                .findById(savedCmdFrs.getId())
                .orElse(savedCmdFrs);
        return CommandeFournisseurDto.fromEntity(reloadedCmdFrs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if (id == null) {
            log.error("L'ID de la commande fournisseur est nul");
            return null;
        }
        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur avec l'ID " + id + " n'a été trouvée",
                        ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Le code de la commande fournisseur est nul");
            return null;
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur avec le CODE " + code + " n'a été trouvée",
                        ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CommandeFournisseurDto> findAllByFournisseur(Integer idFournisseur) {
        return commandeFournisseurRepository.findAllByFournisseurId(idFournisseur)
                .stream()
                .map(CommandeFournisseurDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CommandeFournisseurDto> findAllByIdEntreprise(Integer idEntreprise) {
        return commandeFournisseurRepository.findAllByIdEntreprise(idEntreprise)
                .stream()
                .map(CommandeFournisseurDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(
            Integer idCommande) {
        return ligneCommandeFournisseurRepository
                .findAllByCommandeFournisseurId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID de la commande fournisseur est nul");
            return;
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs =
                ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(id);
        if (!ligneCommandeFournisseurs.isEmpty()) {
            throw new InvalidOperationException(
                    "Impossible de supprimer une commande fournisseur déjà utilisée",
                    ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE
            );
        }
        commandeFournisseurRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     * Récupère la commande depuis la BDD et met à jour son état.
     */
    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande,
                                                     EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (etatCommande == null) {
            log.error("L'état de la commande fournisseur est nul");
            throw new InvalidOperationException(
                    "Impossible de modifier l'état de la commande avec un état null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }
        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommande(idCommande);

        // Récupérer l'entité attachée et modifier directement
        CommandeFournisseur commandeFournisseur = commandeFournisseurRepository
                .findById(idCommande)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Commande non trouvée",
                        ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
        commandeFournisseur.setEtatCommande(etatCommande);
        CommandeFournisseur savedCommande = commandeFournisseurRepository
                .save(commandeFournisseur);

        if (commandeFournisseurDto.isCommandeLivree()) {
            updateMvtStk(idCommande);
        }
        return CommandeFournisseurDto.fromEntity(savedCommande);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande,
                                                         Integer idLigneCommande,
                                                         BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("La quantité de la ligne de commande est invalide");
            throw new InvalidOperationException(
                    "Impossible de modifier la commande avec une quantité nulle ou inférieure à 0",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional =
                findLigneCommandeFournisseur(idLigneCommande);
        LigneCommandeFournisseur ligneCommandeFournisseur =
                ligneCommandeFournisseurOptional.get();
        ligneCommandeFournisseur.setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
        return commandeFournisseur;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande,
                                                    Integer idFournisseur) {
        checkIdCommande(idCommande);
        if (idFournisseur == null) {
            log.error("L'ID du fournisseur est nul");
            throw new InvalidOperationException(
                    "Impossible de modifier la commande avec un ID fournisseur null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }
        CommandeFournisseurDto commandeFournisseurDto = checkEtatCommande(idCommande);

        // Récupérer les entités attachées
        Fournisseur fournisseur = fournisseurRepository.findById(idFournisseur)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun fournisseur avec l'ID " + idFournisseur + " n'a été trouvé",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND
                ));
        CommandeFournisseur commande = commandeFournisseurRepository
                .findById(idCommande)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Commande non trouvée",
                        ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
        commande.setFournisseur(fournisseur);
        return CommandeFournisseurDto.fromEntity(
                commandeFournisseurRepository.save(commande)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto updateProduit(Integer idCommande,
                                                Integer idLigneCommande,
                                                Integer idProduit) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdProduit(idProduit, "nouveau");
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur =
                findLigneCommandeFournisseur(idLigneCommande);
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
        LigneCommandeFournisseur ligneToSave = ligneCommandeFournisseur.get();
        ligneToSave.setProduit(produit);
        ligneCommandeFournisseurRepository.save(ligneToSave);
        return commandeFournisseur;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandeFournisseurDto deleteProduit(Integer idCommande,
                                                Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        findLigneCommandeFournisseur(idLigneCommande);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);
        return commandeFournisseur;
    }

    // ===== Méthodes privées =====

    /**
     * Vérifie et retourne la commande si elle n'est pas livrée.
     *
     * @param idCommande L'ID de la commande à vérifier.
     * @return Le DTO de la commande si modifiable.
     * @throws InvalidOperationException Si la commande est livrée.
     */
    private CommandeFournisseurDto checkEtatCommande(Integer idCommande) {
        CommandeFournisseurDto commandeFournisseur = findById(idCommande);
        if (commandeFournisseur.isCommandeLivree()) {
            throw new InvalidOperationException(
                    "Impossible de modifier la commande lorsqu'elle est livrée",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }
        return commandeFournisseur;
    }

    /**
     * Recherche une ligne de commande fournisseur par son ID.
     *
     * @param idLigneCommande L'ID de la ligne de commande.
     * @return La ligne de commande trouvée.
     * @throws EntityNotFoundException Si la ligne n'existe pas.
     */
    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(
            Integer idLigneCommande) {
        Optional<LigneCommandeFournisseur> ligneOptional =
                ligneCommandeFournisseurRepository.findById(idLigneCommande);
        if (ligneOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne de commande fournisseur avec l'ID "
                            + idLigneCommande + " n'a été trouvée",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
            );
        }
        return ligneOptional;
    }

    /**
     * Vérifie que l'ID de la commande n'est pas nul.
     *
     * @param idCommande L'ID à vérifier.
     * @throws InvalidOperationException Si l'ID est nul.
     */
    private void checkIdCommande(Integer idCommande) {
        if (idCommande == null) {
            log.error("L'ID de la commande fournisseur est nul");
            throw new InvalidOperationException(
                    "Impossible de modifier la commande avec un ID null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }
    }

    /**
     * Vérifie que l'ID de la ligne de commande n'est pas nul.
     *
     * @param idLigneCommande L'ID à vérifier.
     * @throws InvalidOperationException Si l'ID est nul.
     */
    private void checkIdLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null) {
            log.error("L'ID de la ligne de commande est nul");
            throw new InvalidOperationException(
                    "Impossible de modifier la commande avec une ligne de commande null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
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
                    "Impossible de modifier la commande avec un ID "
                            + msg + " produit null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }
    }

    /**
     * Met à jour les mouvements de stock pour une commande livrée.
     *
     * @param idCommande L'ID de la commande livrée.
     */
    private void updateMvtStk(Integer idCommande) {
        List<LigneCommandeFournisseur> ligneCommandeFournisseur =
                ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande);
        ligneCommandeFournisseur.forEach(this::effectuerEntree);
    }

    /**
     * Enregistre un mouvement d'entrée de stock pour une ligne de commande.
     *
     * @param lig La ligne de commande fournisseur.
     */
    private void effectuerEntree(LigneCommandeFournisseur lig) {
        MvtStkDto mvtStkDto = MvtStkDto.builder()
                .produit(ProduitDto.fromEntity(lig.getProduit()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.ENTREE)
                .sourceMvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStkService.entreeStock(mvtStkDto);
    }
}