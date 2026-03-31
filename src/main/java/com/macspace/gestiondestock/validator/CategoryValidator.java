package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator pour l'entité {@link CategoryDto} dans MacSpace.
 * Vérifie que les données de catégorie sont complètes et valides
 * avant tout traitement ou persistance.
 */
public class CategoryValidator {

    /**
     * Valide un {@link CategoryDto}.
     *
     * @param categoryDto Le DTO à valider.
     * @return La liste des erreurs de validation,
     *         vide si toutes les données sont valides.
     */
    public static List<String> validate(CategoryDto categoryDto) {
        List<String> errors = new ArrayList<>();

        if (categoryDto == null) {
            errors.add("Veuillez renseigner le code de la catégorie");
            errors.add("Veuillez renseigner la désignation de la catégorie");
            return errors;
        }
        if (!StringUtils.hasLength(categoryDto.getCode())) {
            errors.add("Veuillez renseigner le code de la catégorie");
        }
        if (!StringUtils.hasLength(categoryDto.getDesignation())) {
            errors.add("Veuillez renseigner la désignation de la catégorie");
        }
        return errors;
    }
}