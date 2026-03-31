package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.ProduitDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator pour l'entité {@link ProduitDto} dans MacSpace.
 * Vérifie que les données produit sont complètes et valides
 * avant tout traitement ou persistance.
 */
public class ProduitValidator {

    /**
     * Valide un {@link ProduitDto}.
     * <p>
     * Champs validés :
     * <ul>
     *     <li>Code produit</li>
     *     <li>Désignation</li>
     *     <li>Prix HT</li>
     *     <li>Taux TVA</li>
     *     <li>Prix TTC</li>
     *     <li>Catégorie</li>
     *     <li>Fournisseur</li>
     * </ul>
     *
     * @param dto Le DTO à valider.
     * @return La liste des erreurs de validation,
     *         vide si toutes les données sont valides.
     */
    public static List<String> validate(ProduitDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le code du produit");
            errors.add("Veuillez renseigner la désignation du produit");
            errors.add("Veuillez renseigner le prix HT du produit");
            errors.add("Veuillez renseigner la TVA du produit");
            errors.add("Veuillez renseigner le prix TTC du produit");
            errors.add("Veuillez sélectionner une catégorie pour le produit");
            errors.add("Veuillez sélectionner un fournisseur pour le produit");
            return errors;
        }
        if (!StringUtils.hasLength(dto.getCodeProduit())) {
            errors.add("Veuillez renseigner le code du produit");
        }
        if (!StringUtils.hasLength(dto.getDesignation())) {
            errors.add("Veuillez renseigner la désignation du produit");
        }
        if (dto.getPrixUnitaireHt() == null ||
                dto.getPrixUnitaireHt().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Veuillez renseigner un prix HT valide supérieur à 0");
        }
        if (dto.getTauxTva() == null ||
                dto.getTauxTva().compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Veuillez renseigner un taux de TVA valide");
        }
        if (dto.getPrixUnitaireTtc() == null ||
                dto.getPrixUnitaireTtc().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Veuillez renseigner un prix TTC valide supérieur à 0");
        }
        if (dto.getCategory() == null) {
            errors.add("Veuillez sélectionner une catégorie pour le produit");
        } else {
            errors.addAll(CategoryValidator.validate(dto.getCategory()));
        }
        if (dto.getFournisseur() == null) {
            errors.add("Veuillez sélectionner un fournisseur pour le produit");
        }

        return errors;
    }
}