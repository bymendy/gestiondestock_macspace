package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.LigneInterventionClientDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator pour l'entité {@link LigneInterventionClientDto} dans MacSpace.
 * Vérifie que les données de ligne d'intervention client sont complètes
 * et valides avant tout traitement ou persistance.
 */
public class LigneInterventionClientValidator {

    /**
     * Valide un {@link LigneInterventionClientDto}.
     * <p>
     * Champs validés :
     * <ul>
     *     <li>Produit</li>
     *     <li>Intervention client</li>
     *     <li>Quantité</li>
     * </ul>
     *
     * @param dto Le DTO à valider.
     * @return La liste des erreurs de validation,
     *         vide si toutes les données sont valides.
     */
    public static List<String> validate(LigneInterventionClientDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le produit de la ligne d'intervention");
            errors.add("Veuillez renseigner l'intervention client");
            errors.add("Veuillez renseigner la quantité");
            return errors;
        }
        if (dto.getProduit() == null) {
            errors.add("Veuillez renseigner le produit de la ligne d'intervention");
        }
        if (dto.getInterventionClient() == null) {
            errors.add("Veuillez renseigner l'intervention client");
        }
        if (dto.getQuantite() == null) {
            errors.add("Veuillez renseigner la quantité");
        } else if (dto.getQuantite().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("La quantité doit être supérieure à 0");
        }

        return errors;
    }
}