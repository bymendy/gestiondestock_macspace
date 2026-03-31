package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.AdresseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator pour l'entité {@link AdresseDto} dans MacSpace.
 * Vérifie que les données d'adresse sont complètes et valides
 * avant tout traitement ou persistance.
 */
public class AdresseValidator {

    /**
     * Valide un {@link AdresseDto}.
     *
     * @param adresseDto Le DTO à valider.
     * @return La liste des erreurs de validation,
     *         vide si toutes les données sont valides.
     */
    public static List<String> validate(AdresseDto adresseDto) {
        List<String> errors = new ArrayList<>();

        if (adresseDto == null) {
            errors.add("Veuillez renseigner l'adresse 1");
            errors.add("Veuillez renseigner la ville");
            errors.add("Veuillez renseigner le pays");
            errors.add("Veuillez renseigner le code postal");
            return errors;
        }
        if (!StringUtils.hasLength(adresseDto.getAdresse1())) {
            errors.add("Veuillez renseigner l'adresse 1");
        }
        if (!StringUtils.hasLength(adresseDto.getVille())) {
            errors.add("Veuillez renseigner la ville");
        }
        if (!StringUtils.hasLength(adresseDto.getPays())) {
            errors.add("Veuillez renseigner le pays");
        }
        if (!StringUtils.hasLength(adresseDto.getCodePostal())) {
            errors.add("Veuillez renseigner le code postal");
        }
        return errors;
    }
}