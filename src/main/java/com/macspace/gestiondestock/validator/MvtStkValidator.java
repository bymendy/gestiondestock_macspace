package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.MvtStkDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator pour l'entité {@link MvtStkDto} dans MacSpace.
 * Vérifie que les données de mouvement de stock sont complètes
 * et valides avant tout traitement ou persistance.
 */
public class MvtStkValidator {

    /**
     * Valide un {@link MvtStkDto}.
     * <p>
     * Champs validés :
     * <ul>
     *     <li>Date du mouvement</li>
     *     <li>Quantité</li>
     *     <li>Produit</li>
     *     <li>Type de mouvement</li>
     *     <li>Source du mouvement</li>
     * </ul>
     *
     * @param dto Le DTO à valider.
     * @return La liste des erreurs de validation,
     *         vide si toutes les données sont valides.
     */
    public static List<String> validate(MvtStkDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner la date du mouvement");
            errors.add("Veuillez renseigner la quantité du mouvement");
            errors.add("Veuillez renseigner le produit");
            errors.add("Veuillez renseigner le type du mouvement");
            errors.add("Veuillez renseigner la source du mouvement");
            return errors;
        }
        if (dto.getDateMvt() == null) {
            errors.add("Veuillez renseigner la date du mouvement");
        }
        if (dto.getQuantite() == null ||
                dto.getQuantite().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Veuillez renseigner une quantité valide supérieure à 0");
        }
        if (dto.getProduit() == null) {
            errors.add("Veuillez renseigner le produit");
        }
        if (dto.getTypeMvt() == null) {
            errors.add("Veuillez renseigner le type du mouvement");
        }
        if (dto.getSourceMvt() == null) {
            errors.add("Veuillez renseigner la source du mouvement");
        }

        return errors;
    }
}