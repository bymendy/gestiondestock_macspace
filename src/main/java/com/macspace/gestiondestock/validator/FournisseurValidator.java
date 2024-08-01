package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {

    public static List<String> validate(FournisseurDto dto ) {
        List<String> errors = new ArrayList<>();
        if (dto == null){
            errors.add("Veuillez renseigner le nom du Fournisseur");
            errors.add("Veuillez renseigner le prenom du Fournisseur");
            errors.add("Veuillez renseigner l'email du Fournisseur");
            errors.add("Veuillez renseigner le numero de telephone du Fournisseur");

            return errors;
        }

        if(!StringUtils.hasLength(dto.getNom())){
            errors.add("Veuillez renseigner le nom du Fournisseur");
        }
        if(!StringUtils.hasLength(dto.getPrenom())){
            errors.add("Veuillez renseigner le prenom du Fournisseur");
        }
        if(!StringUtils.hasLength(dto.getMail())){
            errors.add("Veuillez renseigner l'email du Fournisseur");
        }
        if(!StringUtils.hasLength(dto.getNumTel())){
            errors.add("Veuillez renseigner le numero de telephone du Fournisseur");
        }
        return errors;
    }
}
