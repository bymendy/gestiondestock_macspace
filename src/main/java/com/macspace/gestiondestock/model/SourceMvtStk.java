package com.macspace.gestiondestock.model;

/**
 * Enumération des sources possibles d'un mouvement de stock dans MacSpace.
 * <p>
 * Indique l'origine d'un mouvement de stock :
 * <ul>
 *   <li>INTERVENTION_CLIENT : Mouvement lié à une intervention chez un client.</li>
 *   <li>COMMANDE_FOURNISSEUR : Mouvement lié à une commande fournisseur.</li>
 *   <li>INTERVENTION : Mouvement lié à une intervention interne.</li>
 * </ul>
 * </p>
 */
public enum SourceMvtStk {

    /** Mouvement issu d'une intervention client. */
    INTERVENTION_CLIENT,

    /** Mouvement issu d'une commande fournisseur. */
    COMMANDE_FOURNISSEUR,

    /** Mouvement issu d'une intervention interne. */
    INTERVENTION
}