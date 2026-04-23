package com.macspace.gestiondestock.services.impl;
import com.macspace.gestiondestock.annotation.Auditable;

import com.macspace.gestiondestock.dto.InterventionDto;
import com.macspace.gestiondestock.dto.LigneInterventionDto;
import com.macspace.gestiondestock.dto.MvtStkDto;
import com.macspace.gestiondestock.dto.ProduitDto;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.model.*;
import com.macspace.gestiondestock.repository.InterventionRepository;
import com.macspace.gestiondestock.repository.LigneInterventionRepository;
import com.macspace.gestiondestock.repository.ProduitRepository;
import com.macspace.gestiondestock.repository.UtilisateurRepository;
import com.macspace.gestiondestock.services.InterventionService;
import com.macspace.gestiondestock.services.MvtStkService;
import com.macspace.gestiondestock.validator.InterventionValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service pour la gestion des interventions dans MacSpace.
 *
 * Règles métier stock :
 * - EN_ATTENTE / ANNULEE → lignes sauvegardées, stock NON décompté
 * - EN_COURS / TERMINEE  → lignes sauvegardées, stock décompté (SORTIE)
 * - Modification         → stock des anciennes lignes remis (ENTREE)
 *                          puis stock des nouvelles lignes décompté si EN_COURS/TERMINEE
 */
@Service
@Slf4j
public class InterventionServiceImpl implements InterventionService {

    private final ProduitRepository produitRepository;
    private final InterventionRepository interventionsRepository;
    private final LigneInterventionRepository ligneInterventionRepository;
    private final MvtStkService mvtStkService;
    private final UtilisateurRepository utilisateurRepository;

    /**
     * Constructeur avec injection de dépendances.
     */
    @Autowired
    public InterventionServiceImpl(
            ProduitRepository produitRepository,
            InterventionRepository interventionsRepository,
            LigneInterventionRepository ligneInterventionRepository,
            MvtStkService mvtStkService,
            UtilisateurRepository utilisateurRepository) {
        this.produitRepository = produitRepository;
        this.interventionsRepository = interventionsRepository;
        this.ligneInterventionRepository = ligneInterventionRepository;
        this.mvtStkService = mvtStkService;
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * Vérifie si un état nécessite le décompte du stock.
     *
     * @param etat L'état de l'intervention
     * @return true si EN_COURS ou TERMINEE
     */
    private boolean decompterStock(EtatIntervention etat) {
        return etat == EtatIntervention.EN_COURS
                || etat == EtatIntervention.TERMINEE;
    }

    /**
     * {@inheritDoc}
     *
     * Logique stock :
     * - MODE CREATE : décompte si EN_COURS/TERMINEE
     * - MODE UPDATE : remet le stock des anciennes lignes,
     *                 puis décompte les nouvelles si EN_COURS/TERMINEE
     *  * @Transactional garantit que toutes les opérations
     *  * (suppression anciennes lignes + insertion nouvelles lignes)
     *  * se font dans la même transaction.
     *  * Si une erreur survient, tout est annulé (rollback).
     */
    @Auditable(entite = "intervention", action = "CREATE_UPDATE")
    @Override
    @Transactional
    public InterventionDto save(InterventionDto dto) {

        /* ===== Validation ===== */
        List<String> errors = InterventionValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("L'intervention n'est pas valide");
            throw new InvalidEntityException(
                    "L'intervention n'est pas valide",
                    ErrorCodes.INTERVENTION_NOT_VALID,
                    errors
            );
        }

        /* ===== Vérification des produits ===== */
        List<String> produitErrors = new ArrayList<>();
        if (dto.getLigneInterventions() != null) {
            dto.getLigneInterventions().forEach(ligne -> {
                if (ligne.getProduit() != null) {
                    Optional<Produit> produit = produitRepository
                            .findById(ligne.getProduit().getId());
                    if (produit.isEmpty()) {
                        produitErrors.add("Aucun produit avec l'ID "
                                + ligne.getProduit().getId()
                                + " n'a été trouvé");
                    }
                }
            });
        }
        if (!produitErrors.isEmpty()) {
            log.error("Produits non trouvés : {}", produitErrors);
            throw new InvalidEntityException(
                    "Un ou plusieurs produits n'ont pas été trouvés",
                    ErrorCodes.INTERVENTION_NOT_VALID,
                    produitErrors
            );
        }

        /* ===== MODE UPDATE ou CREATE ===== */
        Intervention intervention;

        if (dto.getId() != null) {
            /* ===== MODE UPDATE ===== */
            intervention = interventionsRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Aucune intervention avec l'ID " + dto.getId(),
                            ErrorCodes.INTERVENTION_NOT_FOUND
                    ));

            /* Récupérer l'ancien état AVANT modification */
            EtatIntervention ancienEtat = intervention.getEtatIntervention();

            /* Récupérer les anciennes lignes */
            List<LigneIntervention> anciennesLignes = ligneInterventionRepository
                    .findAllByInterventionId(dto.getId());

            /* Remettre le stock des anciennes lignes
               UNIQUEMENT si l'ancien état décomptait le stock */
            if (decompterStock(ancienEtat)) {
                log.info("Remise en stock des anciennes lignes " +
                        "(ancien état: {})", ancienEtat);
                anciennesLignes.forEach(this::remettreStk);
            }

            /* Supprimer les anciennes lignes */
            ligneInterventionRepository.deleteAll(anciennesLignes);
            log.info("Anciennes lignes supprimées pour l'intervention {}",
                    dto.getId());

        } else {
            /* ===== MODE CREATE ===== */
            intervention = new Intervention();
        }

        /* ===== Mise à jour des champs ===== */
        intervention.setCode(dto.getCode());
        intervention.setDateIntervention(dto.getDateIntervention() != null
                ? dto.getDateIntervention() : Instant.now());
        intervention.setProblematique(dto.getProblematique());
        intervention.setEtatIntervention(dto.getEtatIntervention());
        intervention.setIdEntreprise(dto.getIdEntreprise());

        /* Récupérer le technicien */
        if (dto.getTechnicien() != null && dto.getTechnicien().getId() != null) {
            Utilisateur technicien = utilisateurRepository
                    .findById(dto.getTechnicien().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Aucun technicien avec l'ID "
                                    + dto.getTechnicien().getId(),
                            ErrorCodes.UTILISATEUR_NOT_FOUND
                    ));
            intervention.setTechnicien(technicien);
        }

        Intervention savedIntervention = interventionsRepository.save(intervention);

        /* ===== Sauvegarder les nouvelles lignes ===== */
        if (dto.getLigneInterventions() != null
                && !dto.getLigneInterventions().isEmpty()) {

            dto.getLigneInterventions().forEach(ligneDto -> {
                if (ligneDto.getProduit() != null) {

                    Produit produit = produitRepository
                            .findById(ligneDto.getProduit().getId())
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Produit non trouvé",
                                    ErrorCodes.PRODUIT_NOT_FOUND
                            ));

                    /* Créer la nouvelle ligne */
                    LigneIntervention ligne = new LigneIntervention();
                    ligne.setIntervention(savedIntervention);
                    ligne.setProduit(produit);
                    ligne.setQuantite(ligneDto.getQuantite());
                    ligne.setNumeroContrat(ligneDto.getNumeroContrat());
                    ligne.setProblematique(ligneDto.getProblematique());
                    ligne.setIdEntreprise(savedIntervention.getIdEntreprise());

                    LigneIntervention savedLigne =
                            ligneInterventionRepository.save(ligne);

                    /* Décompter le stock UNIQUEMENT si EN_COURS ou TERMINEE */
                    if (decompterStock(savedIntervention.getEtatIntervention())) {
                        log.info("Décompte stock produit {} (quantité: {})",
                                produit.getId(), ligneDto.getQuantite());
                        sortieStk(savedLigne);
                    }
                }
            });
        }

        return InterventionDto.fromEntity(
                interventionsRepository.findById(savedIntervention.getId())
                        .orElse(savedIntervention)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionDto findById(Integer id) {
        if (id == null) {
            log.error("L'ID de l'intervention est nul");
            return null;
        }
        return interventionsRepository.findById(id)
                .map(InterventionDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune intervention avec l'ID " + id,
                        ErrorCodes.INTERVENTION_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Le code de l'intervention est nul");
            return null;
        }
        return interventionsRepository.findInterventionByCode(code)
                .map(InterventionDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune intervention avec le CODE " + code,
                        ErrorCodes.INTERVENTION_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InterventionDto> findAll() {
        return interventionsRepository.findAll().stream()
                .map(InterventionDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InterventionDto> findAllByEtatIntervention(
            EtatIntervention etatIntervention) {
        return interventionsRepository
                .findAllByEtatIntervention(etatIntervention)
                .stream()
                .map(InterventionDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InterventionDto> findAllByTechnicien(Integer idTechnicien) {
        return interventionsRepository.findAllByTechnicienId(idTechnicien)
                .stream()
                .map(InterventionDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InterventionDto> findAllByIdEntreprise(Integer idEntreprise) {
        return interventionsRepository.findAllByIdEntreprise(idEntreprise)
                .stream()
                .map(InterventionDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     * Empêche la suppression si des lignes sont associées.
     */
    @Auditable(entite = "intervention", action = "DELETE")
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID de l'intervention est nul");
            return;
        }
        List<LigneIntervention> lignes =
                ligneInterventionRepository.findAllByInterventionId(id);
        if (!lignes.isEmpty()) {
            throw new InvalidOperationException(
                    "Impossible de supprimer une intervention " +
                            "qui a des lignes associées",
                    ErrorCodes.INTERVENTION_ALREADY_IN_USE
            );
        }
        interventionsRepository.deleteById(id);
    }

    // ===== Méthodes privées =====

    /**
     * Enregistre une SORTIE de stock pour une ligne d'intervention.
     * Appelé uniquement si état = EN_COURS ou TERMINEE.
     *
     * @param ligne La ligne d'intervention
     */
    private void sortieStk(LigneIntervention ligne) {
        MvtStkDto mvt = MvtStkDto.builder()
                .produit(ProduitDto.fromEntity(ligne.getProduit()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.SORTIE)
                .sourceMvt(SourceMvtStk.INTERVENTION)
                .quantite(ligne.getQuantite())
                .idEntreprise(ligne.getIdEntreprise())
                .build();
        mvtStkService.sortieStock(mvt);
    }

    /**
     * Enregistre une ENTREE de stock pour annuler une ancienne ligne.
     * Appelé en mode UPDATE si l'ancien état était EN_COURS ou TERMINEE.
     *
     * @param ligne L'ancienne ligne d'intervention à annuler
     */
    private void remettreStk(LigneIntervention ligne) {
        MvtStkDto mvt = MvtStkDto.builder()
                .produit(ProduitDto.fromEntity(ligne.getProduit()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.ENTREE)
                .sourceMvt(SourceMvtStk.INTERVENTION)
                .quantite(ligne.getQuantite())
                .idEntreprise(ligne.getIdEntreprise())
                .build();
        mvtStkService.entreeStock(mvt);
    }
}