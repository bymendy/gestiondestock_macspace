package com.macspace.gestiondestock.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.macspace.gestiondestock.dto.MvtStkDto;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.model.TypeMvtStk;
import com.macspace.gestiondestock.repository.MvtStkRepository;
import com.macspace.gestiondestock.services.MvtStkService;
import com.macspace.gestiondestock.services.ProduitService;
import com.macspace.gestiondestock.validator.MvtStkValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implémentation du service {@link MvtStkService}.
 * <p>
 * Cette classe gère les mouvements de stock (entrée, sortie, et corrections) en interagissant avec le dépôt
 * {@link MvtStkRepository} et le service {@link ProduitService}. Elle utilise le validateur {@link MvtStkValidator}
 * pour s'assurer que les mouvements de stock sont valides avant de les enregistrer.
 * </p>
 */
@Service
@Slf4j
public class MvtStkServiceImpl implements MvtStkService {

    private final MvtStkRepository repository;
    private final ProduitService produitService;

    /**
     * Constructeur de {@link MvtStkServiceImpl}.
     *
     * @param repository le dépôt des mouvements de stock
     * @param produitService le service pour gérer les produits
     */
    @Autowired
    public MvtStkServiceImpl(MvtStkRepository repository, ProduitService produitService) {
        this.repository = repository;
        this.produitService = produitService;
    }

    /**
     * Retourne le stock réel d'un produit spécifié par son ID.
     *
     * @param idProduit l'ID du produit
     * @return la quantité réelle du stock pour le produit spécifié
     */
    @Override
    public BigDecimal stockReelProduit(Integer idProduit) {
        if (idProduit == null) {
            log.warn("ID Produit is NULL");
            return BigDecimal.valueOf(-1);
        }
        produitService.findById(idProduit);
        return repository.stockReelProduit(idProduit);
    }

    /**
     * Retourne la liste des mouvements de stock pour un produit spécifié par son ID.
     *
     * @param idArticle l'ID du produit
     * @return la liste des mouvements de stock pour le produit spécifié
     */
    @Override
    public List<MvtStkDto> mvtStkProduit(Integer idArticle) {
        return repository.findAllByProduitId(idArticle).stream()
                .map(MvtStkDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Gère l'entrée de stock pour un produit en utilisant les informations fournies dans le DTO.
     *
     * @param dto le DTO contenant les informations sur le mouvement d'entrée de stock
     * @return le DTO mis à jour après l'enregistrement du mouvement de stock
     */
    @Override
    public MvtStkDto entreeStock(MvtStkDto dto) {
        return entreePositive(dto, TypeMvtStk.ENTREE);
    }

    /**
     * Gère la sortie de stock pour un produit en utilisant les informations fournies dans le DTO.
     *
     * @param dto le DTO contenant les informations sur le mouvement de sortie de stock
     * @return le DTO mis à jour après l'enregistrement du mouvement de stock
     */
    @Override
    public MvtStkDto sortieStock(MvtStkDto dto) {
        return sortieNegative(dto, TypeMvtStk.SORTIE);
    }

    /**
     * Gère la correction positive de stock pour un produit en utilisant les informations fournies dans le DTO.
     *
     * @param dto le DTO contenant les informations sur le mouvement de correction positive de stock
     * @return le DTO mis à jour après l'enregistrement du mouvement de stock
     */
    @Override
    public MvtStkDto correctionStockPos(MvtStkDto dto) {
        return entreePositive(dto, TypeMvtStk.CORRECTION_POS);
    }

    /**
     * Gère la correction négative de stock pour un produit en utilisant les informations fournies dans le DTO.
     *
     * @param dto le DTO contenant les informations sur le mouvement de correction négative de stock
     * @return le DTO mis à jour après l'enregistrement du mouvement de stock
     */
    @Override
    public MvtStkDto correctionStockNeg(MvtStkDto dto) {
        return sortieNegative(dto, TypeMvtStk.CORRECTION_NEG);
    }

    /**
     * Gère les entrées positives de stock en validant le DTO et en enregistrant le mouvement de stock.
     *
     * @param dto le DTO contenant les informations sur l'entrée de stock
     * @param typeMvtStk le type de mouvement de stock (entrée)
     * @return le DTO mis à jour après l'enregistrement du mouvement de stock
     */
    private MvtStkDto entreePositive(MvtStkDto dto, TypeMvtStk typeMvtStk) {
        List<String> errors = MvtStkValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Produit is not valid {}", dto);
            throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
        }
        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue())
                )
        );
        dto.setTypeMvt(typeMvtStk);
        return MvtStkDto.fromEntity(
                repository.save(MvtStkDto.toEntity(dto))
        );
    }

    /**
     * Gère les sorties négatives de stock en validant le DTO et en enregistrant le mouvement de stock.
     *
     * @param dto le DTO contenant les informations sur la sortie de stock
     * @param typeMvtStk le type de mouvement de stock (sortie)
     * @return le DTO mis à jour après l'enregistrement du mouvement de stock
     */
    private MvtStkDto sortieNegative(MvtStkDto dto, TypeMvtStk typeMvtStk) {
        List<String> errors = MvtStkValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Produit is not valid {}", dto);
            throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
        }
        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue()) * -1
                )
        );
        dto.setTypeMvt(typeMvtStk);
        return MvtStkDto.fromEntity(
                repository.save(MvtStkDto.toEntity(dto))
        );
    }
}
