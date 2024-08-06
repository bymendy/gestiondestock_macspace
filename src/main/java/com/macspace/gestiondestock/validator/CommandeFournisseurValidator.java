package com.macspace.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import com.macspace.gestiondestock.dto.CommandeFournisseurDto;
import org.springframework.util.StringUtils;

/**
 * Validateur pour les objets {@link CommandeFournisseurDto}.
 * <p>
 * Cette classe fournit des méthodes pour valider les données d'une commande fournisseur afin
 * de s'assurer que toutes les informations requises sont présentes et correctes avant de procéder
 * aux opérations suivantes.
 * </p>
 */
public class CommandeFournisseurValidator {

    /**
     * Valide un objet {@link CommandeFournisseurDto}.
     * <p>
     * Cette méthode vérifie si les champs essentiels de la commande fournisseur sont correctement
     * remplis. Les erreurs de validation sont retournées sous forme de liste de chaînes de caractères.
     * </p>
     *
     * @param dto l'objet {@link CommandeFournisseurDto} à valider
     * @return une liste de chaînes de caractères contenant les messages d'erreur de validation
     */
    public static List<String> validate(CommandeFournisseurDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            errors.add("Veuillez renseigner l'etat de la commande");
            errors.add("Veuillez renseigner le fournisseur");
            return errors;
        }

        if (!StringUtils.hasLength(dto.getCode())) {
            errors.add("Veuillez renseigner le code de la commande");
        }
        if (dto.getDateCommande() == null) {
            errors.add("Veuillez renseigner la date de la commande");
        }
        if (dto.getEtatCommande() == null || !StringUtils.hasLength(dto.getEtatCommande().toString())) {
            errors.add("Veuillez renseigner l'etat de la commande");
        }
        if (dto.getFournisseur() == null || dto.getFournisseur().getId() == null) {
            errors.add("Veuillez renseigner le fournisseur");
        }

        return errors;
    }
}
