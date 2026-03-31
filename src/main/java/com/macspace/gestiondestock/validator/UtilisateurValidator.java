package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator pour l'entité {@link UtilisateurDto} dans MacSpace.
 * Vérifie que les données utilisateur sont complètes et valides
 * avant tout traitement ou persistance.
 * Note : La validation manuelle est préférable pour un contrôle précis.
 */
public class UtilisateurValidator {

    /**
     * Valide un {@link UtilisateurDto}.
     * <p>
     * Champs validés :
     * <ul>
     *     <li>Nom</li>
     *     <li>Email</li>
     *     <li>Mot de passe</li>
     *     <li>Entreprise</li>
     *     <li>Adresse</li>
     * </ul>
     *
     * @param utilisateurDto Le DTO à valider.
     * @return La liste des erreurs de validation,
     *         vide si toutes les données sont valides.
     */
    public static List<String> validate(UtilisateurDto utilisateurDto) {
        List<String> errors = new ArrayList<>();

        if (utilisateurDto == null) {
            errors.add("Veuillez renseigner le nom de l'utilisateur");
            errors.add("Veuillez renseigner l'email de l'utilisateur");
            errors.add("Veuillez renseigner le mot de passe de l'utilisateur");
            errors.add("Veuillez renseigner l'entreprise de l'utilisateur");
            return errors;
        }
        if (!StringUtils.hasLength(utilisateurDto.getNom())) {
            errors.add("Veuillez renseigner le nom de l'utilisateur");
        }
        if (!StringUtils.hasLength(utilisateurDto.getEmail())) {
            errors.add("Veuillez renseigner l'email de l'utilisateur");
        } else if (!utilisateurDto.getEmail().contains("@")) {
            errors.add("Veuillez renseigner un email valide pour l'utilisateur");
        }
        if (!StringUtils.hasLength(utilisateurDto.getPassword())) {
            errors.add("Veuillez renseigner le mot de passe de l'utilisateur");
        }
        if (utilisateurDto.getEntreprise() == null) {
            errors.add("Veuillez renseigner l'entreprise de l'utilisateur");
        }
        if (utilisateurDto.getAdresse() != null) {
            errors.addAll(AdresseValidator.validate(utilisateurDto.getAdresse()));
        }

        return errors;
    }
}