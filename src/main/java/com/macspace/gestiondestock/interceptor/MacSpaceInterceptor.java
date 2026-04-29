package com.macspace.gestiondestock.interceptor;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

/**
 * Intercepteur SQL pour le support multi-tenant dans MacSpace.
 * Utilise StatementInspector (Hibernate 6.x) pour filtrer
 * automatiquement les requêtes SELECT par idEntreprise.
 *
 * <p>Tables exclues du filtre multi-tenant :</p>
 * <ul>
 *     <li>entreprise — racine du système</li>
 *     <li>roles — liés aux utilisateurs</li>
 *     <li>utilisateur — nécessaire pour l'authentification</li>
 *     <li>adresse — pas d'idEntreprise</li>
 * </ul>
 *
 * <p>Requêtes exclues :</p>
 * <ul>
 *     <li>Requêtes d'agrégation (coalesce, count, sum)</li>
 *     <li>idEntreprise null ou "0"</li>
 * </ul>
 */
public class MacSpaceInterceptor implements StatementInspector {

    private static final long serialVersionUID = 1L;

    /**
     * Tables exclues du filtre multi-tenant.
     */
    private static final String[] EXCLUDED_TABLES = {
            "from entreprise",
            "from roles",
            "from utilisateur",
            "from adresse",
            "from vue_interventions_par_mois",
            "from vue_performance_techniciens",
            "from vue_produits_plus_utilises",
            "from vue_tableau_bord_global"
    };

    /**
     * Fonctions d'agrégation exclues du filtre.
     */
    private static final String[] EXCLUDED_FUNCTIONS = {
            "coalesce",
            "count(",
            "sum("
    };

    /**
     * Inspecte et modifie la requête SQL avant envoi à la DB.
     * Ajoute automatiquement un filtre WHERE par id_entreprise
     * pour toutes les entités éligibles au multi-tenant.
     *
     * @param sql La requête SQL originale générée par Hibernate.
     * @return La requête SQL avec le filtre multi-tenant si applicable,
     *         ou la requête originale inchangée sinon.
     */
    @Override
    public String inspect(String sql) {
        if (!StringUtils.hasLength(sql)
                || !sql.toLowerCase().startsWith("select")) {
            return sql;
        }

        final String sqlLower = sql.toLowerCase();

        // Exclure les requêtes d'agrégation
        for (String function : EXCLUDED_FUNCTIONS) {
            if (sqlLower.contains(function)) {
                return sql;
            }
        }

        // Exclure les tables non filtrées
        for (String table : EXCLUDED_TABLES) {
            if (sqlLower.contains(table)) {
                return sql;
            }
        }

        // Vérifier l'idEntreprise dans le MDC
        final String idEntreprise = MDC.get("idEntreprise");
        if (!StringUtils.hasLength(idEntreprise)
                || "0".equals(idEntreprise)) {
            return sql;
        }

        // Extraire l'alias de la table principale via FROM
        int fromIndex = sqlLower.indexOf(" from ");
        if (fromIndex == -1) return sql;

        // Trouver l'alias après le nom de la table
        String afterFrom = sql.substring(fromIndex + 6).trim();
        String[] parts = afterFrom.split("\\s+");
        if (parts.length < 2) return sql;

        // L'alias est le 2ème token après FROM (ex: "client c1_0" → alias = "c1_0")
        String alias = parts[1].replaceAll("[^a-zA-Z0-9_]", "");
        if (!StringUtils.hasLength(alias)) return sql;

        // Ajout du filtre multi-tenant
        if (sql.contains("where")) {
            sql = sql + " and " + alias + ".id_entreprise = " + idEntreprise;
        } else {
            sql = sql + " where " + alias + ".id_entreprise = " + idEntreprise;
        }

        return sql;
    }
}