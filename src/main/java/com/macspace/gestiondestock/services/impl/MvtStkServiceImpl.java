package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.MvtStkDto;
import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.model.MvtStk;
import com.macspace.gestiondestock.model.Produit;
import com.macspace.gestiondestock.model.TypeMvtStk;
import com.macspace.gestiondestock.repository.MvtStkRepository;
import com.macspace.gestiondestock.repository.ProduitRepository;
import com.macspace.gestiondestock.services.MvtStkService;
import com.macspace.gestiondestock.services.ProduitService;
import com.macspace.gestiondestock.validator.MvtStkValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implémentation du service pour la gestion des mouvements de stock dans MacSpace.
 *
 * Gère les entrées, sorties et corrections de stock.
 * Le produit est récupéré directement depuis le repository
 * pour éviter les problèmes d'entités détachées JPA.
 */
@Service
@Slf4j
public class MvtStkServiceImpl implements MvtStkService {

    private final MvtStkRepository repository;
    private final ProduitService produitService;
    private final ProduitRepository produitRepository;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param repository       Repository JPA des mouvements de stock.
     * @param produitService   Service de gestion des produits.
     * @param produitRepository Repository JPA des produits.
     */
    @Autowired
    public MvtStkServiceImpl(
            MvtStkRepository repository,
            ProduitService produitService,
            ProduitRepository produitRepository) {
        this.repository = repository;
        this.produitService = produitService;
        this.produitRepository = produitRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal stockReelProduit(Integer idProduit) {
        if (idProduit == null) {
            log.warn("L'ID du produit est nul");
            return BigDecimal.ZERO;
        }
        produitService.findById(idProduit);
        return repository.stockReelProduit(idProduit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MvtStkDto> mvtStkProduit(Integer idProduit) {
        return repository.findAllByProduitId(idProduit).stream()
                .map(MvtStkDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MvtStkDto> findAllByTypeMvt(TypeMvtStk typeMvt) {
        return repository.findAllByTypeMvt(typeMvt).stream()
                .map(MvtStkDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MvtStkDto> findAllByIdEntreprise(Integer idEntreprise) {
        return repository.findAllByIdEntreprise(idEntreprise).stream()
                .map(MvtStkDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MvtStkDto entreeStock(MvtStkDto dto) {
        return entreePositive(dto, TypeMvtStk.ENTREE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MvtStkDto sortieStock(MvtStkDto dto) {
        return sortieNegative(dto, TypeMvtStk.SORTIE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MvtStkDto correctionStockPos(MvtStkDto dto) {
        return entreePositive(dto, TypeMvtStk.CORRECTION_POS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MvtStkDto correctionStockNeg(MvtStkDto dto) {
        return sortieNegative(dto, TypeMvtStk.CORRECTION_NEG);
    }

    // ===== Méthodes privées =====

    /**
     * Enregistre un mouvement de stock positif (entrée ou correction positive).
     *
     * @param dto         Le DTO du mouvement de stock.
     * @param typeMvtStk  Le type de mouvement.
     * @return Le DTO sauvegardé.
     * @throws InvalidEntityException Si le mouvement n'est pas valide.
     */
    private MvtStkDto entreePositive(MvtStkDto dto, TypeMvtStk typeMvtStk) {
        List<String> errors = MvtStkValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Le mouvement de stock n'est pas valide {}", dto);
            throw new InvalidEntityException(
                    "Le mouvement de stock n'est pas valide",
                    ErrorCodes.MVT_STK_NOT_VALID,
                    errors
            );
        }
        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue())
                )
        );
        dto.setTypeMvt(typeMvtStk);
        return MvtStkDto.fromEntity(
                repository.save(buildMvtStk(dto))
        );
    }

    /**
     * Enregistre un mouvement de stock négatif (sortie ou correction négative).
     *
     * @param dto         Le DTO du mouvement de stock.
     * @param typeMvtStk  Le type de mouvement.
     * @return Le DTO sauvegardé.
     * @throws InvalidEntityException Si le mouvement n'est pas valide.
     */
    private MvtStkDto sortieNegative(MvtStkDto dto, TypeMvtStk typeMvtStk) {
        List<String> errors = MvtStkValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Le mouvement de stock n'est pas valide {}", dto);
            throw new InvalidEntityException(
                    "Le mouvement de stock n'est pas valide",
                    ErrorCodes.MVT_STK_NOT_VALID,
                    errors
            );
        }
        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue()) * -1
                )
        );
        dto.setTypeMvt(typeMvtStk);
        return MvtStkDto.fromEntity(
                repository.save(buildMvtStk(dto))
        );
    }

    /**
     * Construit une entité MvtStk avec le produit attaché à la session Hibernate.
     *
     * @param dto Le DTO source.
     * @return L'entité MvtStk prête à être persistée.
     * @throws EntityNotFoundException Si le produit n'existe pas.
     */
    private MvtStk buildMvtStk(MvtStkDto dto) {
        Produit produit = produitRepository
                .findById(dto.getProduit().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun produit avec l'ID "
                                + dto.getProduit().getId() + " n'a été trouvé",
                        ErrorCodes.PRODUIT_NOT_FOUND
                ));
        MvtStk mvtStk = new MvtStk();
        mvtStk.setProduit(produit);
        mvtStk.setDateMvt(dto.getDateMvt());
        mvtStk.setQuantite(dto.getQuantite());
        mvtStk.setTypeMvt(dto.getTypeMvt());
        mvtStk.setSourceMvt(dto.getSourceMvt());
        mvtStk.setIdEntreprise(dto.getIdEntreprise());
        return mvtStk;
    }
}
