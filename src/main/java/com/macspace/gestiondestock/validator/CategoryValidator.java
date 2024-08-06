package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe CategoryValidator fournit des méthodes pour valider les objets {@link CategoryDto}.
 * <p>
 * La validation permet de s'assurer que les données de l'entité respectent certaines règles de cohérence.
 */
public class CategoryValidator {

    /**
     * Valide un objet {@link CategoryDto}.
     * <p>
     * Cette méthode vérifie que le champ 'code' de la catégorie est renseigné. Si ce champ est vide ou nul,
     * un message d'erreur est ajouté à la liste des erreurs.
     *
     * @param categoryDto l'objet {@link CategoryDto} à valider
     * @return une liste de chaînes de caractères contenant les messages d'erreur.
     *         La liste est vide si aucune erreur n'est trouvée.
     */
    public static List<String> validate(CategoryDto categoryDto) {
        List<String> errors = new ArrayList<>();
        if (!StringUtils.hasLength(categoryDto.getCode())) {
            errors.add("Veuillez renseigner le code de la categorie");
        }
        return errors;
    }
}
