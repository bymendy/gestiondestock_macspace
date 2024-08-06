package com.macspace.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import com.macspace.gestiondestock.dto.InterventionClientDto;
import org.springframework.util.StringUtils;

/**
 * Classe de validation pour les objets InterventionClientDto.
 * Cette classe fournit une méthode statique pour valider les données d'une InterventionClientDto.
 */
public class InterventionClientValidator {

    /**
     * Valide les informations d'un InterventionClientDto.
     *
     * @param dto l'objet InterventionClientDto à valider
     * @return une liste de messages d'erreur s'il y a des champs invalides ou manquants,
     *         une liste vide si l'objet est valide
     */
    public static List<String> validate(InterventionClientDto dto) {
        List<String> errors = new ArrayList<>();

        // Vérifie si l'objet dto est null
        if (dto == null) {
            errors.add("Veuillez renseigner le code de l'intervention");
            errors.add("Veuillez renseigner la date de l'intervention");
            errors.add("Veuillez renseigner l'etat de l'intervention");
            errors.add("Veuillez renseigner le client");
            return errors;
        }

        // Vérifie si le code de l'intervention est renseigné
        if (!StringUtils.hasLength(dto.getCode())) {
            errors.add("Veuillez renseigner le code de l'intervention");
        }

        // Vérifie si la date de l'intervention est renseignée
        if (dto.getDateIntervention() == null) {
            errors.add("Veuillez renseigner la date de l'intervention");
        }

        // Vérifie si l'état de l'intervention est renseigné
        if (dto.getEtatIntervention() == null || !StringUtils.hasLength(dto.getEtatIntervention().toString())) {
            errors.add("Veuillez renseigner l'état de l'intervention");
        }

        // Vérifie si le client est renseigné
        if (dto.getClient() == null || dto.getClient().getId() == null) {
            errors.add("Veuillez renseigner le client");
        }

        return errors;
    }
}
