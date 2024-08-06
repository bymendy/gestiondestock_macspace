package com.macspace.gestiondestock.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.macspace.gestiondestock.dto.MvtStkDto;
import org.springframework.util.StringUtils;

/**
 * Classe de validation pour les objets {@link MvtStkDto}.
 */
public class MvtStkValidator {

    /**
     * Valide un objet {@link MvtStkDto}.
     *
     * Cette méthode vérifie si les champs essentiels de l'objet {@link MvtStkDto} sont renseignés.
     * Si l'objet est nul ou si certains champs sont manquants ou invalides, des messages d'erreur
     * sont ajoutés à la liste retournée.
     *
     * @param dto l'objet {@link MvtStkDto} à valider
     * @return une liste de messages d'erreur si des validations échouent, sinon une liste vide
     */
    public static List<String> validate(MvtStkDto dto) {
        List<String> errors = new ArrayList<>();
        if (dto == null) {
            errors.add("Veuillez renseigner la date du mouvement");
            errors.add("Veuillez renseigner la quantité du mouvement");
            errors.add("Veuillez renseigner le produit");
            errors.add("Veuillez renseigner le type du mouvement");

            return errors;
        }
        if (dto.getDateMvt() == null) {
            errors.add("Veuillez renseigner la date du mouvement");
        }
        if (dto.getQuantite() == null || dto.getQuantite().compareTo(BigDecimal.ZERO) == 0) {
            errors.add("Veuillez renseigner la quantité du mouvement");
        }
        if (dto.getProduit() == null || dto.getProduit().getId() == null) {
            errors.add("Veuillez renseigner le produit");
        }
        if (!StringUtils.hasLength(dto.getTypeMvt().name())) {
            errors.add("Veuillez renseigner le type du mouvement");
        }

        return errors;
    }
}
