package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.InterventionsDto;
import com.macspace.gestiondestock.dto.LigneInterventionDto;
import com.macspace.gestiondestock.dto.MvtStkDto;
import com.macspace.gestiondestock.dto.ProduitDto;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.model.*;
import com.macspace.gestiondestock.repository.InterventionsRepository;
import com.macspace.gestiondestock.repository.LigneInterventionRepository;
import com.macspace.gestiondestock.repository.ProduitRepository;
import com.macspace.gestiondestock.services.InterventionsService;
import com.macspace.gestiondestock.services.MvtStkService;
import com.macspace.gestiondestock.validator.InterventionsValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation du service {@link InterventionsService}.
 * <p>
 * Cette classe gère les interventions, y compris la création, la recherche et la suppression d'interventions.
 * Elle utilise les dépôts {@link ProduitRepository}, {@link InterventionsRepository}, et
 * {@link LigneInterventionRepository} ainsi que le service {@link MvtStkService} pour gérer les mouvements de stock.
 * </p>
 */
@Service
@Slf4j
public class InterventionsServiceImpl implements InterventionsService {

    private final ProduitRepository produitRepository;
    private final InterventionsRepository interventionsRepository;
    private final LigneInterventionRepository ligneInterventionRepository;
    private final MvtStkService mvtStkService;

    /**
     * Constructeur de {@link InterventionsServiceImpl}.
     *
     * @param produitRepository le dépôt pour les produits
     * @param interventionsRepository le dépôt pour les interventions
     * @param ligneInterventionRepository le dépôt pour les lignes d'intervention
     * @param mvtStkService le service pour gérer les mouvements de stock
     */
    @Autowired
    public InterventionsServiceImpl(ProduitRepository produitRepository,
                                    InterventionsRepository interventionsRepository,
                                    LigneInterventionRepository ligneInterventionRepository,
                                    MvtStkService mvtStkService) {
        this.produitRepository = produitRepository;
        this.interventionsRepository = interventionsRepository;
        this.ligneInterventionRepository = ligneInterventionRepository;
        this.mvtStkService = mvtStkService;
    }

    /**
     * Enregistre une nouvelle intervention.
     * <p>
     * Valide les données de l'intervention, vérifie l'existence des produits associés,
     * enregistre l'intervention, puis enregistre les lignes d'intervention et met à jour les mouvements de stock.
     * </p>
     *
     * @param dto les données de l'intervention à enregistrer
     * @return les données de l'intervention enregistrée
     * @throws InvalidEntityException si les données de l'intervention sont invalides ou si un produit est manquant
     */
    @Override
    public InterventionsDto save(InterventionsDto dto) {
        List<String> errors = InterventionsValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Interventions n'est pas valide");
            throw new InvalidEntityException("L'objet vente n'est pas valide", ErrorCodes.INTERVENTION_NOT_VALID, errors);
        }

        List<String> produitErrors = new ArrayList<>();

        dto.getLigneInterventions().forEach(ligneInterventionDto -> {
            Optional<Produits> produit = produitRepository.findById(ligneInterventionDto.getProduit().getId());
            if (produit.isEmpty()) {
                produitErrors.add("Aucun produit avec l'ID " + ligneInterventionDto.getProduit().getId() + " n'a ete trouve dans la BDD");
            }
        });

        if (!produitErrors.isEmpty()) {
            log.error("One or more produits were not found in the DB, {}", produitErrors);
            throw new InvalidEntityException("Un ou plusieurs produit n'ont pas ete trouve dans la BDD", ErrorCodes.INTERVENTION_NOT_VALID, produitErrors);
        }

        Interventions savedInterventions = interventionsRepository.save(InterventionsDto.toEntity(dto));

        dto.getLigneInterventions().forEach(ligneInterventionDto -> {
            LigneIntervention ligneIntervention = LigneInterventionDto.toEntity(ligneInterventionDto);
            ligneIntervention.setInterventions(savedInterventions);
            ligneInterventionRepository.save(ligneIntervention);
            updateMvtStk(ligneIntervention);
        });

        return InterventionsDto.fromEntity(savedInterventions);
    }

    /**
     * Trouve une intervention par son ID.
     *
     * @param id l'ID de l'intervention à rechercher
     * @return les données de l'intervention trouvée
     * @throws EntityNotFoundException si aucune intervention n'est trouvée pour l'ID spécifié
     */
    @Override
    public InterventionsDto findById(Integer id) {
        if (id == null) {
            log.error("Interventions ID is NULL");
            return null;
        }
        return interventionsRepository.findById(id)
                .map(InterventionsDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune intervention n'a ete trouve dans la BDD", ErrorCodes.INTERVENTION_NOT_FOUND));
    }

    /**
     * Trouve une intervention par son code.
     *
     * @param code le code de l'intervention à rechercher
     * @return les données de l'intervention trouvée
     * @throws EntityNotFoundException si aucune intervention n'est trouvée pour le code spécifié
     */
    @Override
    public InterventionsDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Intervention CODE is NULL");
            return null;
        }
        return interventionsRepository.findInterventionsByCode(code)
                .map(InterventionsDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune Interventions client n'a ete trouve avec le CODE " + code, ErrorCodes.INTERVENTION_NOT_VALID
                ));
    }

    /**
     * Retourne la liste de toutes les interventions.
     *
     * @return la liste des interventions
     */
    @Override
    public List<InterventionsDto> findAll() {
        return interventionsRepository.findAll().stream()
                .map(InterventionsDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Supprime une intervention par son ID.
     * <p>
     * Vérifie si l'intervention est utilisée dans des lignes d'intervention avant de la supprimer.
     * </p>
     *
     * @param id l'ID de l'intervention à supprimer
     * @throws InvalidOperationException si l'intervention est utilisée dans des lignes d'intervention
     */
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Vente ID is NULL");
            return;
        }
        List<LigneIntervention> ligneInterventions = ligneInterventionRepository.findAllByInterventionsId(id);
        if (!ligneInterventions.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer une intervention ...",
                    ErrorCodes.INTERVENTION_ALREADY_IN_USE);
        }
        interventionsRepository.deleteById(id);
    }

    /**
     * Met à jour les mouvements de stock pour une ligne d'intervention.
     *
     * @param lig la ligne d'intervention à traiter
     */
    private void updateMvtStk(LigneIntervention lig) {
        MvtStkDto mvtStkDto = MvtStkDto.builder()
                .produit(ProduitDto.fromEntity(lig.getProduit()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.SORTIE)
                .sourceMvt(SourceMvtStk.INTERVENTION)
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStkService.sortieStock(mvtStkDto);
    }
}
