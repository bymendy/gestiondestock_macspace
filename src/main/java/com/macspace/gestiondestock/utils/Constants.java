package com.macspace.gestiondestock.utils;

/**
 * Interface Constants
 * <p>
 * Cette interface définit des constantes globales utilisées à travers l'application.
 * Les constantes définies ici peuvent être utilisées pour maintenir une cohérence
 * dans les chemins d'accès et autres valeurs partagées.
 * </p>
 */
public interface Constants {

    /**
     * Le préfixe de base pour toutes les routes d'API.
     * <p>
     * Cette constante est utilisée pour définir la racine de toutes les URL
     * de l'API de gestion des stocks.
     * </p>
     */
    String APP_ROOT = "gestiondestock/v1";

    String COMMANDE_FOURNISSEUR_ENDPOINT = APP_ROOT + "/commandesfournisseurs";
    String CREATE_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/create";
    String FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/{idCommandeFournisseur}";
    String FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/filter/{codeCommandeFournisseur}";
    String FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/all";
    String DELETE_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/{idCommandeFournisseur}";

    String ENTREPRISE_ENDPOINT = APP_ROOT + "/entreprises";

    String FOURNISSEUR_ENDPOINT = APP_ROOT + "/fournisseurs";

    String UTILISATEUR_ENDPOINT = APP_ROOT + "/utilisateurs";

    String INTERVENTIONS_ENDPOINT = APP_ROOT + "/interventions";

    String AUTHENTICATION_ENDPOINT = APP_ROOT + "/auth";
}
