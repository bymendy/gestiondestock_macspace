package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.ClientDto;
import com.macspace.gestiondestock.dto.ProduitDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    public static List<String> validate( ClientDto dto ) {
        List<String> errors = new ArrayList<>();
        if(dto== null){
            errors.add("Veuillez renseigner le nom du Client");
            errors.add("Veuillez renseigner le prenom du Client");
            errors.add("Veuillez renseigner l'email du Client");
            errors.add("Veuillez renseigner le numero telephone du Client");

            return errors;
        }

        if(!StringUtils.hasLength(dto.getNom())){
            errors.add("Veuillez renseigner le nom du Client");
        }
        if(!StringUtils.hasLength(dto.getPrenom())){
            errors.add("Veuillez renseigner le prenom du Client");
        }
        if(!StringUtils.hasLength(dto.getEmail())){
            errors.add("Veuillez renseigner l'email du Client");
        }
        if(!StringUtils.hasLength(dto.getNumTel())){
            errors.add("Veuillez renseigner le numero client du Client");
        }

        return errors;
        }
}
