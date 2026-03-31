package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.CommandeFournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator pour l'entité {@link CommandeFournisseurDto} dans MacSpace.
 * Vérifie que les données de commande fournisseur sont complètes
 * et valides avant tout traitement ou persistance.
 */
public class CommandeFournisseurValidator {

    /**
     * Valide un {@link CommandeFournisseurDto}.
     * <p>
     * Champs validés :
     * <ul>
     *     <li>Code</li>
     *     <li>Date de commande</li>
     *     <li>État de la commande</li>
     *     <li>Fournisseur</li>
     * </ul>
     *
     * @param dto Le DTO à valider.
     * @return La liste des erreurs de validation,
     *         vide si toutes les données sont valides.
     */
    public static List<String> validate(CommandeFournisseurDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            errors.add("Veuillez renseigner l'état de la commande");
            errors.add("Veuillez renseigner le fournisseur");
            return errors;
        }
        if (!StringUtils.hasLength(dto.getCode())) {
            errors.add("Veuillez renseigner le code de la commande");
        }
        if (dto.getDateCommande() == null) {
            errors.add("Veuillez renseigner la date de la commande");
        }
        if (dto.getEtatCommande() == null) {
            errors.add("Veuillez renseigner l'état de la commande");
        }
        if (dto.getFournisseur() == null) {
            errors.add("Veuillez renseigner le fournisseur");
        } else {
            errors.addAll(FournisseurValidator.validate(dto.getFournisseur()));
        }

        return errors;
    }
}