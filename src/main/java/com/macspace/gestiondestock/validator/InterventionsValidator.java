package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.InterventionsDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class InterventionsValidator {
    public static List<String> validate(InterventionsDto dto) {
        List<String> errors = new ArrayList<>();
        if (dto == null) {
            errors.add("Veuillez renseigner le code de l'Intervention");
            errors.add("Veuillez renseigner la date de l'Intervention");
            return errors;
        }

        if (!StringUtils.hasLength(dto.getCode())) {
            errors.add("Veuillez renseigner le code de l'Intervention'");
        }
        if (dto.getDateIntervention() == null) {
            errors.add("Veuillez renseigner la date de l'Intervention'");
        }

        return errors;
    }
}
