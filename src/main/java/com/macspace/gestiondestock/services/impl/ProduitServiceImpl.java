package com.macspace.gestiondestock.services.impl;

import com.macspace.gestiondestock.dto.ProduitDto;
import com.macspace.gestiondestock.annotation.Auditable;

import com.macspace.gestiondestock.exception.EntityNotFoundException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidEntityException;
import com.macspace.gestiondestock.model.Category;
import com.macspace.gestiondestock.model.Fournisseur;
import com.macspace.gestiondestock.model.Produit;
import com.macspace.gestiondestock.repository.CategoryRepository;
import com.macspace.gestiondestock.repository.FournisseurRepository;
import com.macspace.gestiondestock.repository.ProduitRepository;
import com.macspace.gestiondestock.services.ProduitService;
import com.macspace.gestiondestock.validator.ProduitValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Implémentation du service pour la gestion des produits dans MacSpace.
 * <p>
 * La {@link Category} et le {@link Fournisseur} sont récupérés
 * via leurs repositories avant la persistance du produit,
 * pour éviter les TransientPropertyValueException JPA.
 * </p>
 */
@Service
@Slf4j
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final CategoryRepository categoryRepository;
    private final FournisseurRepository fournisseurRepository;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param produitRepository     Repository JPA des produits.
     * @param categoryRepository    Repository JPA des catégories.
     * @param fournisseurRepository Repository JPA des fournisseurs.
     */
    @Autowired
    public ProduitServiceImpl(
            ProduitRepository produitRepository,
            CategoryRepository categoryRepository,
            FournisseurRepository fournisseurRepository) {
        this.produitRepository = produitRepository;
        this.categoryRepository = categoryRepository;
        this.fournisseurRepository = fournisseurRepository;
    }

    /**
     * {@inheritDoc}
     * Récupère la Category et le Fournisseur via leurs repositories
     * avant de persister le produit.
     * UPDATE si id présent, INSERT sinon.
     */
    @Auditable(entite = "produit", action = "CREATE_UPDATE")
    @Override
    public ProduitDto save(ProduitDto dto) {
        List<String> errors = ProduitValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Le produit n'est pas valide {}", dto);
            throw new InvalidEntityException(
                    "Le produit n'est pas valide",
                    ErrorCodes.PRODUIT_NOT_VALID,
                    errors
            );
        }

        /* Récupérer la Category depuis la DB */
        Category category = categoryRepository
                .findById(dto.getCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune catégorie avec l'ID = "
                                + dto.getCategory().getId() + " n'a été trouvée",
                        ErrorCodes.CATEGORY_NOT_FOUND
                ));

        /* Récupérer le Fournisseur depuis la DB */
        Fournisseur fournisseur = fournisseurRepository
                .findById(dto.getFournisseur().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun fournisseur avec l'ID = "
                                + dto.getFournisseur().getId() + " n'a été trouvé",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND
                ));

        /* UPDATE si id présent, INSERT sinon */
        Produit produit;
        if (dto.getId() != null) {
            /* Mode modification - récupérer le produit existant */
            produit = produitRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Aucun produit avec l'ID = " + dto.getId(),
                            ErrorCodes.PRODUIT_NOT_FOUND
                    ));
        } else {
            /* Mode création */
            produit = new Produit();
        }

        produit.setCodeProduit(dto.getCodeProduit());
        produit.setDesignation(dto.getDesignation());
        produit.setPrixUnitaireHt(dto.getPrixUnitaireHt());
        produit.setTauxTva(dto.getTauxTva());
        produit.setPrixUnitaireTtc(dto.getPrixUnitaireTtc());
        produit.setPhoto(dto.getPhoto());
        produit.setCategory(category);
        produit.setFournisseur(fournisseur);
        produit.setIdEntreprise(dto.getIdEntreprise());

        return ProduitDto.fromEntity(produitRepository.save(produit));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProduitDto findById(Integer id) {
        if (id == null) {
            log.error("L'ID du produit est nul");
            return null;
        }
        return produitRepository.findById(id)
                .map(ProduitDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun produit avec l'ID = " + id + " n'a été trouvé",
                        ErrorCodes.PRODUIT_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProduitDto findByCodeProduit(String codeProduit) {
        if (!StringUtils.hasLength(codeProduit)) {
            log.error("Le code du produit est nul");
            return null;
        }
        return produitRepository.findProduitByCodeProduit(codeProduit)
                .map(ProduitDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun produit avec le CODE = " + codeProduit
                                + " n'a été trouvé",
                        ErrorCodes.PRODUIT_NOT_FOUND
                ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProduitDto> findAll() {
        return produitRepository.findAll().stream()
                .map(ProduitDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProduitDto> findAllByCategory(Integer idCategory) {
        return produitRepository.findAllByCategoryId(idCategory)
                .stream()
                .map(ProduitDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProduitDto> findAllByFournisseur(Integer idFournisseur) {
        return produitRepository.findAllByFournisseurId(idFournisseur)
                .stream()
                .map(ProduitDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProduitDto> findAllByIdEntreprise(Integer idEntreprise) {
        return produitRepository.findAllByIdEntreprise(idEntreprise)
                .stream()
                .map(ProduitDto::fromEntity)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Auditable(entite = "produit", action = "DELETE")
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'ID du produit est nul");
            return;
        }
        produitRepository.deleteById(id);
    }
}