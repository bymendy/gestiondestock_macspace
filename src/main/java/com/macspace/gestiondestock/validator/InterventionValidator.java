package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.InterventionDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator pour l'entité {@link InterventionDto} dans MacSpace.
 * Vérifie que les données d'intervention sont complètes et valides
 * avant tout traitement ou persistance.
 */
public class InterventionValidator {

    /**
     * Valide un {@link InterventionDto}.
     * <p>
     * Champs validés :
     * <ul>
     *     <li>Code</li>
     *     <li>Date d'intervention</li>
     *     <li>État de l'intervention</li>
     *     <li>Technicien</li>
     * </ul>
     *
     * @param dto Le DTO à valider.
     * @return La liste des erreurs de validation,
     *         vide si toutes les données sont valides.
     */
    public static List<String> validate(InterventionDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le code de l'Intervention");
            errors.add("Veuillez renseigner la date de l'Intervention");
            errors.add("Veuillez renseigner l'état de l'Intervention");
            errors.add("Veuillez renseigner le technicien de l'Intervention");
            return errors;
        }
        if (!StringUtils.hasLength(dto.getCode())) {
            errors.add("Veuillez renseigner le code de l'Intervention");
        }
        if (dto.getDateIntervention() == null) {
            errors.add("Veuillez renseigner la date de l'Intervention");
        }
        if (dto.getEtatIntervention() == null) {
            errors.add("Veuillez renseigner l'état de l'Intervention");
        }
        if (dto.getTechnicien() == null) {
            errors.add("Veuillez renseigner le technicien de l'Intervention");
        }

        return errors;
    }
}