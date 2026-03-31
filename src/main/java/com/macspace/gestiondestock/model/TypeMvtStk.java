package com.macspace.gestiondestock.model;

/**
 * Enumération des types de mouvements de stock dans MacSpace.
 * <p>
 * Indique la nature d'un mouvement de stock :
 * <ul>
 *   <li>ENTREE : Entrée de produits en stock (ex: réception commande fournisseur).</li>
 *   <li>SORTIE : Sortie de produits du stock (ex: utilisation lors d'une intervention).</li>
 *   <li>CORRECTION_POS : Correction positive du stock (ex: inventaire).</li>
 *   <li>CORRECTION_NEG : Correction négative du stock (ex: inventaire).</li>
 * </ul>
 * </p>
 */
public enum TypeMvtStk {

    /** Entrée de produits en stock. */
    ENTREE,

    /** Sortie de produits du stock. */
    SORTIE,

    /** Correction positive du stock. */
    CORRECTION_POS,

    /** Correction négative du stock. */
    CORRECTION_NEG
}