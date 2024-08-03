package com.macspace.gestiondestock.exception;

/**
 * Enumération des différents codes d'erreur possibles dans l'application.
 * Chaque code d'erreur est associé à une situation ou une entité spécifique,
 * facilitant la gestion et l'identification des erreurs.
 */
public enum ErrorCodes {

        PRODUIT_NOT_FOUND(1000),
        PRODUIT_NOT_VALID(1001),
        CATEGORY_NOT_FOUND(2000),
        CATEGORY_NOT_VALID(2001),
        // TODO complete the rest of the Error Codes
        CLIENT_NOT_FOUND(3000),
        CLIENT_NOT_VALID(3001),
        CLIENT_ALREADY_IN_USE(3002),

        COMMANDE_CLIENT_NOT_FOUND(4000),
        ENTREPRISE_NOT_FOUND(6000),
        ENTREPRISE_NOT_VALID(6001),

        FOURNISSEUR_NOT_FOUND(7000),
        LIGNE_INTERVENTION_CLIENT_NOT_FOUND(8000),
        INTERVENTION_CLIENT_NOT_VALID(8001),
        INTERVENTION_CLIENT_NON_MODIFIABLE(8002),
        INTERVENTION_CLIENT_NOT_FOUND(8003),
        INTERVENTION_CLIENT_ALREADY_IN_USE(8004),
        LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND(9000),
        LIGNE_INTERVENTION_NOT_FOUND(10000),
        MVT_STK_NOT_FOUND(11000),
        MVT_STK_NOT_VALID(11001),

    UTILISATEUR_NOT_FOUND(12000),
        INTERVENTION_NOT_FOUND(13000),
        ;

        private int code;

    /**
     * Constructeur pour l'énumération ErrorCodes.
     *
     * @param code Le code d'erreur associé.
     */
    ErrorCodes(int code) {
        this.code = code;
    }

    /**
     * Obtient le code d'erreur associé.
     *
     * @return Le code d'erreur.
     */
    public int getCode() {
        return code;
    }

}
