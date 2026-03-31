package com.macspace.gestiondestock.exception;

/**
 * Enumération des codes d'erreur de MacSpace.
 * Chaque série de codes correspond à une entité métier.
 * <p>
 * Séries :
 * 1000 → Produit
 * 2000 → Catégorie
 * 3000 → Client
 * 5000 → Commande Fournisseur (série 9000)
 * 6000 → Entreprise
 * 7000 → Fournisseur
 * 8000 → Intervention Client
 * 9000 → Commande Fournisseur
 * 10000 → Ligne Intervention
 * 11000 → Mouvement Stock
 * 12000 → Utilisateur
 * 13000 → Intervention
 * 14000 → Technique
 * </p>
 */
public enum ErrorCodes {

        // ===== Produit (1000) =====
        PRODUIT_NOT_FOUND(1000),
        PRODUIT_NOT_VALID(1001),

        // ===== Catégorie (2000) =====
        CATEGORY_NOT_FOUND(2000),
        CATEGORY_NOT_VALID(2001),

        // ===== Client (3000) =====
        CLIENT_NOT_FOUND(3000),
        CLIENT_NOT_VALID(3001),
        CLIENT_ALREADY_IN_USE(3002),

        // ===== Entreprise (6000) =====
        ENTREPRISE_NOT_FOUND(6000),
        ENTREPRISE_NOT_VALID(6001),

        // ===== Fournisseur (7000) =====
        FOURNISSEUR_NOT_FOUND(7000),
        FOURNISSEUR_NOT_VALID(7001),
        FOURNISSEUR_ALREADY_IN_USE(7002),

        // ===== Intervention Client (8000) =====
        LIGNE_INTERVENTION_CLIENT_NOT_FOUND(8000),
        INTERVENTION_CLIENT_NOT_VALID(8001),
        INTERVENTION_CLIENT_NON_MODIFIABLE(8002),
        INTERVENTION_CLIENT_NOT_FOUND(8003),
        INTERVENTION_CLIENT_ALREADY_IN_USE(8004),

        // ===== Commande Fournisseur (9000) =====
        LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND(9000),
        COMMANDE_FOURNISSEUR_NOT_VALID(9001),
        COMMANDE_FOURNISSEUR_NON_MODIFIABLE(9002),
        COMMANDE_FOURNISSEUR_NOT_FOUND(9003),
        COMMANDE_FOURNISSEUR_ALREADY_IN_USE(9004),

        // ===== Ligne Intervention (10000) =====
        LIGNE_INTERVENTION_NOT_FOUND(10000),

        // ===== Mouvement Stock (11000) =====
        MVT_STK_NOT_FOUND(11000),
        MVT_STK_NOT_VALID(11001),

        // ===== Utilisateur (12000) =====
        UTILISATEUR_NOT_FOUND(12000),
        UTILISATEUR_NOT_VALID(12001),
        UTILISATEUR_ALREADY_EXISTS(12002),
        UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID(12003),

        // ===== Intervention (13000) =====
        INTERVENTION_NOT_FOUND(13000),
        INTERVENTION_NOT_VALID(13001),
        INTERVENTION_ALREADY_IN_USE(13002),

        // ===== Technique (14000) =====
        UPDATE_PHOTO_EXCEPTION(14000),
        UNKNOWN_CONTEXT(14001);

        private final int code;

        /**
         * Constructeur pour l'énumération ErrorCodes.
         *
         * @param code Le code d'erreur associé.
         */
        ErrorCodes(int code) {
                this.code = code;
        }

        /**
         * Retourne le code d'erreur.
         *
         * @return Le code d'erreur.
         */
        public int getCode() {
                return code;
        }
}