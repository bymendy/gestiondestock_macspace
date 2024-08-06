package com.macspace.gestiondestock.validator;

import com.macspace.gestiondestock.dto.LigneInterventionClientDto;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe LigneInterventionClientValidator fournit des méthodes pour valider les objets {@link LigneInterventionClientDto}.
 * <p>
 * La validation permet de s'assurer que les données de l'entité respectent certaines règles de cohérence et d'intégrité.
 */
public class LigneInterventionClientValidator {

    /**
     * Valide un objet {@link LigneInterventionClientDto}.
     * <p>
     * Cette méthode vérifie que les champs obligatoires de l'objet sont renseignés. Si un champ obligatoire est vide ou nul,
     * un message d'erreur est ajouté à la liste des erreurs.
     * <p>
     * La validation actuelle est une ébauche et doit être implémentée pour vérifier les champs suivants :
     * <ul>
     *     <li>Produit</li>
     *     <li>Numéro de contrat</li>
     *     <li>Date de création</li>
     *     <li>Problématique</li>
     * </ul>
     *
     * @param dto l'objet {@link LigneInterventionClientDto} à valider
     * @return une liste de chaînes de caractères contenant les messages d'erreur.
     *         La liste est vide si aucune erreur n'est trouvée.
     */
    // TODO: Implémenter la logique de validation pour LigneInterventionClientDto
    public static List<String> validate(LigneInterventionClientDto dto) {
        List<String> errors = new ArrayList<>();

        // Ajouter ici la logique de validation pour les champs de LigneInterventionClientDto.
        // Par exemple :
        // if (dto.getProduit() == null) {
        //     errors.add("Veuillez renseigner le produit");
        // }
        // if (dto.getNumeroContrat() == null || dto.getNumeroContrat().isEmpty()) {
        //     errors.add("Veuillez renseigner le numéro de contrat");
        // }
        // if (dto.getCreationdate() == null) {
        //     errors.add("Veuillez renseigner la date de création");
        // }
        // if (dto.getProblematique() == null || dto.getProblematique().isEmpty()) {
        //     errors.add("Veuillez renseigner la problématique");
        // }

        return errors;
    }
}
