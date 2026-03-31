package com.macspace.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumération représentant les états possibles d'une commande fournisseur.
 *
 * Cycle de vie d'une commande :
 * EN_PREPARATION → VALIDEE → LIVREE
 *                          → ANNULEE
 */
public enum EtatCommande {

    EN_PREPARATION("En préparation"),
    VALIDEE("Validée"),
    LIVREE("Livrée"),
    ANNULEE("Annulée");

    /**
     * Libellé lisible de l'état, utilisé pour l'affichage frontend.
     */
    private final String libelle;

    EtatCommande(String libelle) {
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
     * Désérialise une valeur JSON en {@link EtatCommande}.
     * Accepte aussi bien le libellé ("En préparation")
     * que le nom de l'enum ("EN_PREPARATION").
     *
     * @param value La valeur JSON à désérialiser.
     * @return L'enum correspondant.
     * @throws IllegalArgumentException Si la valeur est inconnue.
     */
    @JsonCreator
    public static EtatCommande fromValue(String value) {
        for (EtatCommande etat : EtatCommande.values()) {
            if (etat.libelle.equalsIgnoreCase(value)
                    || etat.name().equalsIgnoreCase(value)) {
                return etat;
            }
        }
        throw new IllegalArgumentException(
                "Valeur inconnue pour EtatCommande : " + value);
    }
}