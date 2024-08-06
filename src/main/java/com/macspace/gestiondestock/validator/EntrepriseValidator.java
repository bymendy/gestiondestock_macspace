package com.macspace.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import com.macspace.gestiondestock.dto.EntrepriseDto;
import org.springframework.util.StringUtils;

/**
 * Validateur pour les objets {@link EntrepriseDto}.
 * <p>
 * Cette classe fournit des méthodes pour valider les données d'une entreprise afin
 * de s'assurer que toutes les informations requises sont présentes et correctes avant de procéder
 * aux opérations suivantes.
 * </p>
 */
public class EntrepriseValidator {

    /**
     * Valide un objet {@link EntrepriseDto}.
     * <p>
     * Cette méthode vérifie si les champs essentiels de l'entreprise sont correctement
     * remplis. Les erreurs de validation sont retournées sous forme de liste de chaînes de caractères.
     * La validation de l'adresse associée à l'entreprise est également effectuée en utilisant
     * le {@link AdresseValidator}.
     * </p>
     *
     * @param dto l'objet {@link EntrepriseDto} à valider
     * @return une liste de chaînes de caractères contenant les messages d'erreur de validation
     */
    public static List<String> validate(EntrepriseDto dto) {
        List<String> errors = new ArrayList<>();

        // Si l'objet DTO est nul, ajoute des messages d'erreur pour chaque champ obligatoire
        if (dto == null) {
            errors.add("Veuillez renseigner le nom de l'entreprise");
            errors.add("Veuillez renseigner la description de l'entreprise");
            errors.add("Veuillez renseigner le code fiscal de l'entreprise");
            errors.add("Veuillez renseigner l'email de l'entreprise");
            errors.add("Veuillez renseigner le numéro de téléphone de l'entreprise");
            // Validation de l'adresse (qui est nulle ici) ajoutera des erreurs spécifiques
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        // Validation des champs de l'objet DTO
        if (!StringUtils.hasLength(dto.getNom())) {
            errors.add("Veuillez renseigner le nom de l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getDescription())) {
            errors.add("Veuillez renseigner la description de l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getCodeFiscal())) {
            errors.add("Veuillez renseigner le code fiscal de l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getEmail())) {
            errors.add("Veuillez renseigner l'email de l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getNumTel())) {
            errors.add("Veuillez renseigner le numéro de téléphone de l'entreprise");
        }

        // Validation de l'adresse associée à l'entreprise
        errors.addAll(AdresseValidator.validate(dto.getAdresse()));

        return errors;
    }
}
