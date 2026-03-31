package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.EntrepriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator pour l'entité {@link EntrepriseDto} dans MacSpace.
 * Vérifie que les données d'entreprise sont complètes et valides
 * avant tout traitement ou persistance.
 */
public class EntrepriseValidator {

    /**
     * Valide un {@link EntrepriseDto}.
     * <p>
     * Champs validés :
     * <ul>
     *     <li>Nom</li>
     *     <li>Code fiscal</li>
     *     <li>Email</li>
     *     <li>Numéro de téléphone</li>
     *     <li>Adresse</li>
     * </ul>
     *
     * @param dto Le DTO à valider.
     * @return La liste des erreurs de validation,
     *         vide si toutes les données sont valides.
     */
    public static List<String> validate(EntrepriseDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le nom de l'entreprise");
            errors.add("Veuillez renseigner le code fiscal de l'entreprise");
            errors.add("Veuillez renseigner l'email de l'entreprise");
            errors.add("Veuillez renseigner le numéro de téléphone de l'entreprise");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }
        if (!StringUtils.hasLength(dto.getNom())) {
            errors.add("Veuillez renseigner le nom de l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getCodeFiscal())) {
            errors.add("Veuillez renseigner le code fiscal de l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getEmail())) {
            errors.add("Veuillez renseigner l'email de l'entreprise");
        } else if (!dto.getEmail().contains("@")) {
            errors.add("Veuillez renseigner un email valide pour l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getNumTel())) {
            errors.add("Veuillez renseigner le numéro de téléphone de l'entreprise");
        }
        errors.addAll(AdresseValidator.validate(dto.getAdresse()));

        return errors;
    }
}