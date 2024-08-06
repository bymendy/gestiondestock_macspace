package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validateur pour les objets {@link FournisseurDto}.
 * <p>
 * Cette classe fournit des méthodes pour valider les données d'un fournisseur afin de
 * garantir que toutes les informations requises sont présentes et correctement formatées.
 * </p>
 */
public class FournisseurValidator {

    /**
     * Valide un objet {@link FournisseurDto}.
     * <p>
     * Cette méthode vérifie si les champs essentiels du fournisseur sont correctement
     * remplis. Les erreurs de validation sont retournées sous forme de liste de chaînes de caractères.
     * </p>
     *
     * @param dto l'objet {@link FournisseurDto} à valider
     * @return une liste de chaînes de caractères contenant les messages d'erreur de validation
     */
    public static List<String> validate(FournisseurDto dto ) {
        List<String> errors = new ArrayList<>();

        // Si l'objet DTO est nul, ajoute des messages d'erreur pour chaque champ obligatoire
        if (dto == null){
            errors.add("Veuillez renseigner le nom du Fournisseur");
            errors.add("Veuillez renseigner le prénom du Fournisseur");
            errors.add("Veuillez renseigner l'email du Fournisseur");
            errors.add("Veuillez renseigner le numéro de téléphone du Fournisseur");
            return errors;
        }

        // Validation des champs de l'objet DTO
        if (!StringUtils.hasLength(dto.getNom())){
            errors.add("Veuillez renseigner le nom du Fournisseur");
        }
        if (!StringUtils.hasLength(dto.getPrenom())){
            errors.add("Veuillez renseigner le prénom du Fournisseur");
        }
        if (!StringUtils.hasLength(dto.getMail())){
            errors.add("Veuillez renseigner l'email du Fournisseur");
        }
        if (!StringUtils.hasLength(dto.getNumTel())){
            errors.add("Veuillez renseigner le numéro de téléphone du Fournisseur");
        }

        return errors;
    }
}
