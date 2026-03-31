package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.InterventionClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator pour l'entité {@link InterventionClientDto} dans MacSpace.
 * Vérifie que les données d'intervention client sont complètes
 * et valides avant tout traitement ou persistance.
 */
public class InterventionClientValidator {

    /**
     * Valide un {@link InterventionClientDto}.
     * <p>
     * Champs validés :
     * <ul>
     *     <li>Code</li>
     *     <li>Date d'intervention</li>
     *     <li>État de l'intervention</li>
     *     <li>Client</li>
     * </ul>
     *
     * @param dto Le DTO à valider.
     * @return La liste des erreurs de validation,
     *         vide si toutes les données sont valides.
     */
    public static List<String> validate(InterventionClientDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le code de l'intervention");
            errors.add("Veuillez renseigner la date de l'intervention");
            errors.add("Veuillez renseigner l'état de l'intervention");
            errors.add("Veuillez renseigner le client");
            return errors;
        }
        if (!StringUtils.hasLength(dto.getCode())) {
            errors.add("Veuillez renseigner le code de l'intervention");
        }
        if (dto.getDateIntervention() == null) {
            errors.add("Veuillez renseigner la date de l'intervention");
        }
        if (dto.getEtatIntervention() == null) {
            errors.add("Veuillez renseigner l'état de l'intervention");
        }
        if (dto.getClient() == null) {
            errors.add("Veuillez renseigner le client");
        } else {
            errors.addAll(ClientValidator.validate(dto.getClient()));
        }

        return errors;
    }
}