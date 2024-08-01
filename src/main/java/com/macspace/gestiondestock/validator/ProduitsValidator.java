package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.ProduitDto;
import com.macspace.gestiondestock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ProduitsValidator {
    public static List<String> validate(ProduitDto dto) {
        List<String> errors = new ArrayList<>();

        if(dto== null){
            errors.add("Veuillez renseigner le code produit");
            errors.add("Veuillez renseigner la designation produit");
            errors.add("Veuillez renseigner le prix HT produit");
            errors.add("Veuillez renseigner la TVA produit");
            errors.add("Veuillez renseigner le prix TTC produit");
            errors.add("Veuillez selectionner une categorie produit");
            return errors;

        }

        if (!StringUtils.hasLength(dto.getCodeProduit())){
            errors.add("Veuillez renseigner le code produit");
        }
        if (!StringUtils.hasLength(dto.getDesignation())){
            errors.add("Veuillez renseigner la designation produit");
        }
        if (dto.getPrixUnitaireHt() == null){
            errors.add("Veuillez renseigner le prix HT produit");
        }
        if (dto.getTauxTva() == null){
            errors.add("Veuillez renseigner la TVA produit");
        }
        if (dto.getPrixUnitaireTtc() == null){
            errors.add("Veuillez renseigner le prix TTC produit");
        }
        if (dto.getCategory() == null){
            errors.add("Veuillez selectionner une categorie produit");
        }

        return errors;
    }
}
