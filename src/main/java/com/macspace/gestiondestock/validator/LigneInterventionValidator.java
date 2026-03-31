package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.LigneInterventionDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator pour l'entité {@link LigneInterventionDto} dans MacSpace.
 * Vérifie que les données de ligne d'intervention sont complètes
 * et valides avant tout traitement ou persistance.
 */
public class LigneInterventionValidator {

    /**
     * Valide un {@link LigneInterventionDto}.
     * <p>
     * Champs validés :
     * <ul>
     *     <li>Produit</li>
     *     <li>Intervention</li>
     *     <li>Quantité</li>
     * </ul>
     *
     * @param dto Le DTO à valider.
     * @return La liste des erreurs de validation,
     *         vide si toutes les données sont valides.
     */
    public static List<String> validate(LigneInterventionDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le produit de la ligne d'intervention");
            errors.add("Veuillez renseigner l'intervention");
            errors.add("Veuillez renseigner la quantité");
            return errors;
        }
        if (dto.getProduit() == null) {
            errors.add("Veuillez renseigner le produit de la ligne d'intervention");
        }
        if (dto.getIntervention() == null) {
            errors.add("Veuillez renseigner l'intervention");
        }
        if (dto.getQuantite() == null) {
            errors.add("Veuillez renseigner la quantité");
        } else if (dto.getQuantite().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("La quantité doit être supérieure à 0");
        }

        return errors;
    }
}
