package com.macspace.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import com.macspace.gestiondestock.dto.AdresseDto;
import org.springframework.util.StringUtils;

/**
 * La classe AdresseValidator fournit des méthodes pour valider les objets {@link AdresseDto}.
 * <p>
 * La validation permet de s'assurer que les données de l'entité respectent certaines règles de cohérence et d'intégrité.
 */
public class AdresseValidator {

    public static List<String> validate(AdresseDto adresseDto) {
        List<String> errors = new ArrayList<>();

        if (adresseDto == null) {
            errors.add("Veuillez renseigner l'adresse 1'");
            errors.add("Veuillez renseigner la ville'");
            errors.add("Veuillez renseigner le pays'");
            errors.add("Veuillez renseigner le code postal'");
            return errors;
        }
        if (!StringUtils.hasLength(adresseDto.getAdresse1())) {
            errors.add("Veuillez renseigner l'adresse 1'");
        }
        if (!StringUtils.hasLength(adresseDto.getVille())) {
            errors.add("Veuillez renseigner la ville'");
        }
        if (!StringUtils.hasLength(adresseDto.getPays())) {
            errors.add("Veuillez renseigner le pays'");
        }
        if (!StringUtils.hasLength(adresseDto.getAdresse1())) {
            errors.add("Veuillez renseigner le code postal'");
        }
        return errors;
    }

}