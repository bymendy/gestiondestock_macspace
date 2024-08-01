package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.CategoryDto;
import com.macspace.gestiondestock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

//Toujours Preférable d'avoir une validation Manuel
public class UtilisateurValidator {
    public static List<String> validate(UtilisateurDto utilisateurDto){
        List<String> errors = new ArrayList<>();

        if (utilisateurDto == null){
            errors.add("Veuillez renseigner le nom d'utilisateur");
            errors.add("Veuillez renseigner l'email utilisateur");
            errors.add("Veuillez renseigner le mot de passe d'utilisateur");
            return errors;

        }

        if (!StringUtils.hasLength(utilisateurDto.getNom())){
            errors.add("Veuillez renseigner le nom d'utilisateur");
        }
        //if (!StringUtils.hasLength(utilisateurDto.getPrenom())){
           //errors.add("Veuillez renseigner le prenom d'utilisateur");
        //}
        if (!StringUtils.hasLength(utilisateurDto.getEmail())){
            errors.add("Veuillez renseigner l'email utilisateur");
        }
        if (!StringUtils.hasLength(utilisateurDto.getPassword())){
            errors.add("Veuillez renseigner le mot de passe d'utilisateur");
        }
        // if (utilisateurDto.getAdresse() == null){
        //  errors.add("Veuillez renseigner l'adresse utilisateur'");
        //}else {
        //   if (!StringUtils.hasLength(utilisateurDto.getAdresse().getAdresse1())){
        //       errors.add("Le champs 'Adresse 1 ' est obligatoire");
        //    }
        //   if (!StringUtils.hasLength(utilisateurDto.getFonction())){
        //        errors.add("Le champs 'Fonction' est obligatoire");
        //   }
        //   if (!StringUtils.hasLength(utilisateurDto.getAdresse().getVille())){
        //        errors.add("Le champs 'Ville' est obligatoire");
        //   }
        //   if (!StringUtils.hasLength(utilisateurDto.getAdresse().getCodePostale())){
        //        errors.add("Le champs 'Code Postale' est obligatoire");
        //   }
        //}
        return errors;
    }

}
