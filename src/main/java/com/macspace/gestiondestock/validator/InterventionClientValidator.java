package com.macspace.gestiondestock.validator;
import java.util.ArrayList;
import java.util.List;

import com.macspace.gestiondestock.dto.InterventionClientDto;
import org.springframework.util.StringUtils;
public class InterventionClientValidator {
    public static List<String> validate(InterventionClientDto dto) {
        List<String> errors = new ArrayList<>();
        if (dto == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            errors.add("Veuillez renseigner l'etat de la commande");
            errors.add("Veuillez renseigner le client");
            return errors;
        }

        if (!StringUtils.hasLength(dto.getCode())) {
            errors.add("Veuillez renseigner le code de la commande");
        }
        if (dto.getDateIntervention() == null) {
            errors.add("Veuillez renseigner la date de la commande");
        }
        if (!StringUtils.hasLength(dto.getEtatIntervention().toString())) {
            errors.add("Veuillez renseigner l'etat de la commande");
        }
        if (dto.getClient() == null || dto.getClient().getId() == null) {
            errors.add("Veuillez renseigner le client");
        }

        return errors;
    }
}
