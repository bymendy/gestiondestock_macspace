package com.macspace.gestiondestock.utils;

/**
 * Interface définissant toutes les constantes de routes de l'API MacSpace.
 * Centralise les URLs pour éviter la duplication et faciliter les modifications.
 */
public interface Constants {

    // ===== Racine de l'API =====

    /**
     * Préfixe de base pour toutes les routes de l'API MacSpace.
     */
    String APP_ROOT = "gestiondestock/v1";

    // ===== Authentification =====

    /**
     * Endpoint d'authentification JWT.
     */
    String AUTHENTICATION_ENDPOINT = APP_ROOT + "/auth";

    // ===== Catégorie =====

    /**
     * Endpoint de base pour les catégories.
     */
    String CATEGORY_ENDPOINT = APP_ROOT + "/categories";

    // ===== Client =====

    /**
     * Endpoint de base pour les clients.
     */
    String CLIENT_ENDPOINT = APP_ROOT + "/clients";

    // ===== Commande Fournisseur =====

    /**
     * Endpoint de base pour les commandes fournisseurs.
     */
    String COMMANDE_FOURNISSEUR_ENDPOINT =
            APP_ROOT + "/commandesfournisseurs";

    /**
     * Endpoint de création d'une commande fournisseur.
     */
    String CREATE_COMMANDE_FOURNISSEUR_ENDPOINT =
            COMMANDE_FOURNISSEUR_ENDPOINT + "/create";

    /**
     * Endpoint de recherche d'une commande fournisseur par ID.
     */
    String FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT =
            COMMANDE_FOURNISSEUR_ENDPOINT + "/{idCommandeFournisseur}";

    /**
     * Endpoint de recherche d'une commande fournisseur par code.
     */
    String FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT =
            COMMANDE_FOURNISSEUR_ENDPOINT + "/filter/{codeCommandeFournisseur}";

    /**
     * Endpoint de récupération de toutes les commandes fournisseurs.
     */
    String FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT =
            COMMANDE_FOURNISSEUR_ENDPOINT + "/all";

    /**
     * Endpoint de suppression d'une commande fournisseur.
     */
    String DELETE_COMMANDE_FOURNISSEUR_ENDPOINT =
            COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/{idCommandeFournisseur}";

    // ===== Entreprise =====

    /**
     * Endpoint de base pour les entreprises.
     */
    String ENTREPRISE_ENDPOINT = APP_ROOT + "/entreprises";

    // ===== Fournisseur =====

    /**
     * Endpoint de base pour les fournisseurs.
     */
    String FOURNISSEUR_ENDPOINT = APP_ROOT + "/fournisseurs";

    // ===== Intervention =====

    /**
     * Endpoint de base pour les interventions techniques.
     */
    String INTERVENTIONS_ENDPOINT = APP_ROOT + "/interventions";

    // ===== Intervention Client =====

    /**
     * Endpoint de base pour les interventions clients.
     */
    String INTERVENTION_CLIENT_ENDPOINT = APP_ROOT + "/interventionsclients";

    // ===== Mouvement Stock =====

    /**
     * Endpoint de base pour les mouvements de stock.
     */
    String MVT_STK_ENDPOINT = APP_ROOT + "/mvtstk";

    // ===== Photo =====

    /**
     * Endpoint de base pour la gestion des photos.
     */
    String PHOTO_ENDPOINT = APP_ROOT + "/photos";

    // ===== Produit =====

    /**
     * Endpoint de base pour les produits.
     */
    String PRODUIT_ENDPOINT = APP_ROOT + "/produits";

    // ===== Utilisateur =====

    /**
     * Endpoint de base pour les utilisateurs.
     */
    String UTILISATEUR_ENDPOINT = APP_ROOT + "/utilisateurs";
}