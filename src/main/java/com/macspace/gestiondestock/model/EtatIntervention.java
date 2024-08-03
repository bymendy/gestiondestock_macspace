package com.macspace.gestiondestock.model;

/**
 * Enumération représentant les différents états possibles d'une intervention.
 */
public enum EtatIntervention {

    /**
     * L'intervention est en cours de traitement.
     */
    EN_COURS,

    /**
     * L'intervention est en attente d'action ou de décision.
     */
    EN_ATTENTE,

    /**
     * L'intervention est terminée.
     */
    TERMINEE
}
