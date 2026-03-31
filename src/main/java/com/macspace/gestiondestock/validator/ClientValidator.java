package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator pour l'entité {@link ClientDto} dans MacSpace.
 * Vérifie que les données client sont complètes et valides
 * avant tout traitement ou persistance.
 */
public class ClientValidator {

    /**
     * Valide un {@link ClientDto}.
     * <p>
     * Champs validés :
     * <ul>
     *     <li>Nom</li>
     *     <li>Prénom</li>
     *     <li>Email</li>
     *     <li>Numéro de téléphone</li>
     *     <li>Adresse</li>
     * </ul>
     *
     * @param dto Le DTO à valider.
     * @return La liste des erreurs de validation,
     *         vide si toutes les données sont valides.
     */
    public static List<String> validate(ClientDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le nom du Client");
            errors.add("Veuillez renseigner le prénom du Client");
            errors.add("Veuillez renseigner l'email du Client");
            errors.add("Veuillez renseigner le numéro de téléphone du Client");
            return errors;
        }
        if (!StringUtils.hasLength(dto.getNom())) {
            errors.add("Veuillez renseigner le nom du Client");
        }
        if (!StringUtils.hasLength(dto.getPrenom())) {
            errors.add("Veuillez renseigner le prénom du Client");
        }
        if (!StringUtils.hasLength(dto.getEmail())) {
            errors.add("Veuillez renseigner l'email du Client");
        } else if (!dto.getEmail().contains("@")) {
            errors.add("Veuillez renseigner un email valide pour le Client");
        }
        if (!StringUtils.hasLength(dto.getNumTel())) {
            errors.add("Veuillez renseigner le numéro de téléphone du Client");
        }
        if (dto.getAdresse() != null) {
            errors.addAll(AdresseValidator.validate(dto.getAdresse()));
        }

        return errors;
    }
}