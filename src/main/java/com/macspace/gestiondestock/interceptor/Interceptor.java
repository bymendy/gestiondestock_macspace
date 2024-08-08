package com.macspace.gestiondestock.interceptor;

import org.hibernate.EmptyInterceptor;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

/**
 * Un intercepteur Hibernate personnalisé qui modifie les requêtes SQL de type SELECT
 * pour ajouter une condition basée sur l'identifiant d'entreprise.
 * <p>
 * Cet intercepteur ajoute une condition de filtre à toutes les requêtes SELECT qui n'ont pas
 * déjà une clause WHERE et pour lesquelles le nom de l'entité ne contient pas les mots
 * "entreprise" ou "roles". La condition ajoutée est basée sur la valeur de l'identifiant
 * d'entreprise stockée dans le contexte de logging Mapped Diagnostic Context (MDC).
 * </p>
 */
public class Interceptor extends EmptyInterceptor {

    /**
     * Modifie la requête SQL avant qu'elle ne soit envoyée à la base de données.
     *
     * @param sql La requête SQL à modifier.
     * @return La requête SQL modifiée avec une condition basée sur l'identifiant d'entreprise,
     *         si les conditions sont remplies.
     */

    public String onPrepareStatement(String sql) {
        if (StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")) {
            // Extraction du nom de l'entité à partir de la requête SQL
            final String entityName = sql.substring(7, sql.indexOf("."));
            final String idEntreprise = MDC.get("idEntreprise");
            if (StringUtils.hasLength(entityName)
                    && !entityName.toLowerCase().contains("entreprise")
                    && !entityName.toLowerCase().contains("roles")
                    && StringUtils.hasLength(idEntreprise)) {

                // Ajout de la condition basée sur l'identifiant d'entreprise
                if (sql.contains("where")) {
                    sql = sql + " and " + entityName + ".identreprise = " + idEntreprise;
                } else {
                    sql = sql + " where " + entityName + ".identreprise = " + idEntreprise;
                }
            }
        }
        return onPrepareStatement(sql);
    }
}
