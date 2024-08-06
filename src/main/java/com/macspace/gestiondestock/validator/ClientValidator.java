package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe ClientValidator fournit des méthodes pour valider les objets {@link ClientDto}.
 * <p>
 * La validation permet de s'assurer que les données de l'entité respectent certaines règles de cohérence et d'intégrité.
 */
public class ClientValidator {

    /**
     * Valide un objet {@link ClientDto}.
     * <p>
     * Cette méthode vérifie que les champs obligatoires de l'objet sont renseignés. Si un champ obligatoire est vide ou nul,
     * un message d'erreur est ajouté à la liste des erreurs.
     * <p>
     * Les champs validés sont les suivants :
     * <ul>
     *     <li>Nom</li>
     *     <li>Prénom</li>
     *     <li>Email</li>
     *     <li>Numéro de téléphone</li>
     * </ul>
     *
     * @param dto l'objet {@link ClientDto} à valider
     * @return une liste de chaînes de caractères contenant les messages d'erreur.
     *         La liste est vide si aucune erreur n'est trouvée.
     */
    public static List<String> validate(ClientDto dto) {
        List<String> errors = new ArrayList<>();

        // Vérification des champs obligatoires
        if (dto == null) {
            errors.add("Veuillez renseigner le nom du Client");
            errors.add("Veuillez renseigner le prénom du Client");
            errors.add("Veuillez renseigner l'email du Client");
            errors.add("Veuillez renseigner le numéro de téléphone du Client");
            return errors;
        }

        if (!StringUtils.hasLength(dto.getNom())) {
            errors.add("Veuillez renseigner le nom du Client");
        }
        if (!StringUtils.hasLength(dto.getPrenom())) {
            errors.add("Veuillez renseigner le prénom du Client");
        }
        if (!StringUtils.hasLength(dto.getEmail())) {
            errors.add("Veuillez renseigner l'email du Client");
        }
        if (!StringUtils.hasLength(dto.getNumTel())) {
            errors.add("Veuillez renseigner le numéro de téléphone du Client");
        }

        return errors;
    }
}
