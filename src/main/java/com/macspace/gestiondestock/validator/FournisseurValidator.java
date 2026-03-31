package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator pour l'entité {@link FournisseurDto} dans MacSpace.
 * Vérifie que les données fournisseur sont complètes et valides
 * avant tout traitement ou persistance.
 */
public class FournisseurValidator {

    /**
     * Valide un {@link FournisseurDto}.
     * <p>
     * Champs validés :
     * <ul>
     *     <li>Nom</li>
     *     <li>Email</li>
     *     <li>Numéro de téléphone</li>
     *     <li>Adresse</li>
     * </ul>
     *
     * @param dto Le DTO à valider.
     * @return La liste des erreurs de validation,
     *         vide si toutes les données sont valides.
     */
    public static List<String> validate(FournisseurDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le nom du Fournisseur");
            errors.add("Veuillez renseigner l'email du Fournisseur");
            errors.add("Veuillez renseigner le numéro de téléphone du Fournisseur");
            return errors;
        }
        if (!StringUtils.hasLength(dto.getNom())) {
            errors.add("Veuillez renseigner le nom du Fournisseur");
        }
        if (!StringUtils.hasLength(dto.getEmail())) {
            errors.add("Veuillez renseigner l'email du Fournisseur");
        } else if (!dto.getEmail().contains("@")) {
            errors.add("Veuillez renseigner un email valide pour le Fournisseur");
        }
        if (!StringUtils.hasLength(dto.getNumTel())) {
            errors.add("Veuillez renseigner le numéro de téléphone du Fournisseur");
        }
        if (dto.getAdresse() != null) {
            errors.addAll(AdresseValidator.validate(dto.getAdresse()));
        }

        return errors;
    }
}