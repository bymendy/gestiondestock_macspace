package com.macspace.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumération représentant les états possibles d'une intervention dans MacSpace.
 *
 * Cycle de vie d'une intervention :
 * EN_ATTENTE → EN_COURS → TERMINEE
 *                       → ANNULEE
 */
public enum EtatIntervention {

    EN_ATTENTE("En attente"),
    EN_COURS("En cours"),
    TERMINEE("Terminée"),
    ANNULEE("Annulée");

    /**
     * Libellé lisible de l'état, utilisé pour l'affichage frontend.
     */
    private final String libelle;

    EtatIntervention(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Retourne le libellé de l'état.
     * Utilisé par Jackson lors de la sérialisation JSON.
     */
    @JsonValue
    public String getLibelle() {
        return libelle;
    }

    /**
     * Désérialise une valeur JSON en {@link EtatIntervention}.
     * Accepte aussi bien le libellé ("En attente")
     * que le nom de l'enum ("EN_ATTENTE").
     *
     * @param value La valeur JSON à désérialiser.
     * @return L'enum correspondant.
     * @throws IllegalArgumentException Si la valeur est inconnue.
     */
    @JsonCreator
    public static EtatIntervention fromValue(String value) {
        for (EtatIntervention etat : EtatIntervention.values()) {
            if (etat.libelle.equalsIgnoreCase(value)
                    || etat.name().equalsIgnoreCase(value)) {
                return etat;
            }
        }
        throw new IllegalArgumentException(
                "Valeur inconnue pour EtatIntervention : " + value);
    }
}