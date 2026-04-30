package com.macspace.gestiondestock.repository;

import com.macspace.gestiondestock.dto.datawarehouse.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository Data Warehouse MacSpace.
 * Interroge les vues analytiques MySQL via des requêtes natives.
 * Désactive le filtre multi-tenant pour les requêtes analytiques.
 * Accès réservé aux administrateurs.
 */
@Repository
public class DataWarehouseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Désactive le filtre multi-tenant Hibernate pour les requêtes DW.
     */
    private void disableMultiTenantFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.disableFilter("idEntrepriseFilter");
    }

    /**
     * Récupère les interventions par mois depuis vue_interventions_par_mois.
     */
    public List<InterventionParMoisDto> getInterventionsParMois() {
        try { disableMultiTenantFilter(); } catch (Exception ignored) {}
        Query query = entityManager.createNativeQuery(
                "SELECT annee, nom_mois, mois, nb_interventions, " +
                        "total_produits_utilises, nb_terminees, taux_resolution " +
                        "FROM vue_interventions_par_mois ORDER BY annee, mois"
        );
        List<Object[]> results = query.getResultList();
        List<InterventionParMoisDto> dtos = new ArrayList<>();
        for (Object[] row : results) {
            dtos.add(InterventionParMoisDto.builder()
                    .annee(row[0] != null ? ((Number) row[0]).intValue() : null)
                    .nomMois(row[1] != null ? row[1].toString() : null)
                    .mois(row[2] != null ? ((Number) row[2]).intValue() : null)
                    .nbInterventions(row[3] != null ? ((Number) row[3]).longValue() : 0L)
                    .totalProduitsUtilises(row[4] != null ? ((Number) row[4]).longValue() : 0L)
                    .nbTerminees(row[5] != null ? ((Number) row[5]).longValue() : 0L)
                    .tauxResolution(row[6] != null ? ((Number) row[6]).doubleValue() : 0.0)
                    .build());
        }
        return dtos;
    }

    /**
     * Récupère la performance des techniciens depuis vue_performance_techniciens.
     */
    public List<PerformanceTechnicienDto> getPerformanceTechniciens() {
        try { disableMultiTenantFilter(); } catch (Exception ignored) {}
        Query query = entityManager.createNativeQuery(
                "SELECT nom, prenom, fonction, nb_interventions, " +
                        "nb_terminees, nb_en_attente, nb_en_cours, taux_resolution " +
                        "FROM vue_performance_techniciens ORDER BY nb_interventions DESC"
        );
        List<Object[]> results = query.getResultList();
        List<PerformanceTechnicienDto> dtos = new ArrayList<>();
        for (Object[] row : results) {
            dtos.add(PerformanceTechnicienDto.builder()
                    .nom(row[0] != null ? row[0].toString() : null)
                    .prenom(row[1] != null ? row[1].toString() : null)
                    .fonction(row[2] != null ? row[2].toString() : null)
                    .nbInterventions(row[3] != null ? ((Number) row[3]).longValue() : 0L)
                    .nbTerminees(row[4] != null ? ((Number) row[4]).longValue() : 0L)
                    .nbEnAttente(row[5] != null ? ((Number) row[5]).longValue() : 0L)
                    .nbEnCours(row[6] != null ? ((Number) row[6]).longValue() : 0L)
                    .tauxResolution(row[7] != null ? ((Number) row[7]).doubleValue() : 0.0)
                    .build());
        }
        return dtos;
    }

    /**
     * Récupère les produits les plus utilisés depuis vue_produits_plus_utilises.
     */
    public List<ProduitPlusUtiliseDto> getProduitsLesPlus() {
        try { disableMultiTenantFilter(); } catch (Exception ignored) {}
        Query query = entityManager.createNativeQuery(
                "SELECT code_produit, designation, categorie, nb_mouvements, " +
                        "total_sorties, total_entrees, stock_net " +
                        "FROM vue_produits_plus_utilises ORDER BY nb_mouvements DESC LIMIT 10"
        );
        List<Object[]> results = query.getResultList();
        List<ProduitPlusUtiliseDto> dtos = new ArrayList<>();
        for (Object[] row : results) {
            dtos.add(ProduitPlusUtiliseDto.builder()
                    .codeProduit(row[0] != null ? row[0].toString() : null)
                    .designation(row[1] != null ? row[1].toString() : null)
                    .categorie(row[2] != null ? row[2].toString() : null)
                    .nbMouvements(row[3] != null ? ((Number) row[3]).longValue() : 0L)
                    .totalSorties(row[4] != null ? ((Number) row[4]).doubleValue() : 0.0)
                    .totalEntrees(row[5] != null ? ((Number) row[5]).doubleValue() : 0.0)
                    .stockNet(row[6] != null ? ((Number) row[6]).doubleValue() : 0.0)
                    .build());
        }
        return dtos;
    }

    /**
     * Récupère le tableau de bord global depuis vue_tableau_bord_global.
     */
    public TableauBordGlobalDto getTableauBordGlobal() {
        try { disableMultiTenantFilter(); } catch (Exception ignored) {}
        Query query = entityManager.createNativeQuery(
                "SELECT total_interventions, nb_techniciens_actifs, " +
                        "taux_resolution_global, total_produits_utilises, " +
                        "total_mouvements_stock FROM vue_tableau_bord_global"
        );
        Object[] row = (Object[]) query.getSingleResult();
        return TableauBordGlobalDto.builder()
                .totalInterventions(row[0] != null ? ((Number) row[0]).longValue() : 0L)
                .nbTechniciensActifs(row[1] != null ? ((Number) row[1]).longValue() : 0L)
                .tauxResolutionGlobal(row[2] != null ? ((Number) row[2]).doubleValue() : 0.0)
                .totalProduitsUtilises(row[3] != null ? ((Number) row[3]).longValue() : 0L)
                .totalMouvementsStock(row[4] != null ? ((Number) row[4]).longValue() : 0L)
                .build();
    }

    public void initDataWarehouse() {
        // Alimentation dimension temps
        entityManager.createNativeQuery(
                "INSERT INTO dw_dim_temps (date_full, jour, mois, trimestre, annee, semaine, nom_mois, nom_jour) " +
                        "SELECT DISTINCT DATE(date_intervention), DAY(date_intervention), MONTH(date_intervention), " +
                        "QUARTER(date_intervention), YEAR(date_intervention), WEEK(date_intervention), " +
                        "MONTHNAME(date_intervention), DAYNAME(date_intervention) " +
                        "FROM interventions WHERE date_intervention IS NOT NULL " +
                        "AND DATE(date_intervention) NOT IN (SELECT date_full FROM dw_dim_temps)"
        ).executeUpdate();

        // Alimentation dimension temps depuis stock
        entityManager.createNativeQuery(
                "INSERT INTO dw_dim_temps (date_full, jour, mois, trimestre, annee, semaine, nom_mois, nom_jour) " +
                        "SELECT DISTINCT DATE(date_mvt), DAY(date_mvt), MONTH(date_mvt), " +
                        "QUARTER(date_mvt), YEAR(date_mvt), WEEK(date_mvt), " +
                        "MONTHNAME(date_mvt), DAYNAME(date_mvt) " +
                        "FROM mvtstk WHERE date_mvt IS NOT NULL " +
                        "AND DATE(date_mvt) NOT IN (SELECT date_full FROM dw_dim_temps)"
        ).executeUpdate();

        // Alimentation dimension technicien
        entityManager.createNativeQuery(
                "INSERT INTO dw_dim_technicien (technicien_id, nom, prenom, fonction, id_entreprise) " +
                        "SELECT DISTINCT u.id, u.nom, u.prenom, u.fonction, u.id_entreprise " +
                        "FROM utilisateur u WHERE u.id IN " +
                        "(SELECT DISTINCT id_technicien FROM interventions WHERE id_technicien IS NOT NULL) " +
                        "AND u.id NOT IN (SELECT technicien_id FROM dw_dim_technicien)"
        ).executeUpdate();

        // Alimentation dimension client
        entityManager.createNativeQuery(
                "INSERT INTO dw_dim_client (client_id, nom, prenom, ville, pays, id_entreprise) " +
                        "SELECT c.id, c.nom, c.prenom, a.ville, a.pays, c.id_entreprise " +
                        "FROM client c LEFT JOIN adresse a ON c.id_adresse = a.id " +
                        "WHERE c.id NOT IN (SELECT client_id FROM dw_dim_client)"
        ).executeUpdate();

        // Alimentation dimension produit
        entityManager.createNativeQuery(
                "INSERT INTO dw_dim_produit (produit_id, code_produit, designation, categorie, id_entreprise) " +
                        "SELECT p.id, p.code_produit, p.designation, cat.code, p.id_entreprise " +
                        "FROM produits p LEFT JOIN category cat ON p.id_category = cat.id " +
                        "WHERE p.id NOT IN (SELECT produit_id FROM dw_dim_produit)"
        ).executeUpdate();

        // Alimentation faits interventions
        entityManager.createNativeQuery(
                "INSERT INTO dw_faits_interventions " +
                        "(date_id, technicien_id, etat, nb_produits, id_entreprise, intervention_id, date_intervention) " +
                        "SELECT dt.id, dtech.id, i.etat_intervention, COUNT(li.id), i.id_entreprise, i.id, DATE(i.date_intervention) " +
                        "FROM interventions i " +
                        "LEFT JOIN dw_dim_temps dt ON DATE(i.date_intervention) = dt.date_full " +
                        "LEFT JOIN dw_dim_technicien dtech ON i.id_technicien = dtech.technicien_id " +
                        "LEFT JOIN ligneintervention li ON i.id = li.id_intervention " +
                        "WHERE i.id NOT IN (SELECT DISTINCT intervention_id FROM dw_faits_interventions) " +
                        "GROUP BY i.id, dt.id, dtech.id, i.etat_intervention, i.id_entreprise, i.date_intervention"
        ).executeUpdate();

        // Alimentation faits stock
        entityManager.createNativeQuery(
                "INSERT INTO dw_faits_stock (date_id, produit_id, type_mvt, quantite, source_mvt, id_entreprise, mvt_id) " +
                        "SELECT dt.id, dp.id, m.type_mvt, m.quantite, m.source_mvt, m.id_entreprise, m.id " +
                        "FROM mvtstk m " +
                        "LEFT JOIN dw_dim_temps dt ON DATE(m.date_mvt) = dt.date_full " +
                        "LEFT JOIN dw_dim_produit dp ON m.id_produit = dp.produit_id " +
                        "WHERE m.id NOT IN (SELECT mvt_id FROM dw_faits_stock)"
        ).executeUpdate();

        // Création des vues analytiques
        entityManager.createNativeQuery(
                "CREATE OR REPLACE VIEW vue_interventions_par_mois AS " +
                        "SELECT dt.annee, dt.nom_mois, dt.mois, COUNT(fi.id) as nb_interventions, " +
                        "SUM(fi.nb_produits) as total_produits_utilises, " +
                        "SUM(CASE WHEN fi.etat = 'TERMINEE' THEN 1 ELSE 0 END) as nb_terminees, " +
                        "ROUND(SUM(CASE WHEN fi.etat = 'TERMINEE' THEN 1 ELSE 0 END) * 100.0 / COUNT(fi.id), 2) as taux_resolution " +
                        "FROM dw_faits_interventions fi JOIN dw_dim_temps dt ON fi.date_id = dt.id " +
                        "GROUP BY dt.annee, dt.mois, dt.nom_mois ORDER BY dt.annee, dt.mois"
        ).executeUpdate();

        entityManager.createNativeQuery(
                "CREATE OR REPLACE VIEW vue_performance_techniciens AS " +
                        "SELECT dtech.nom, dtech.prenom, dtech.fonction, COUNT(fi.id) as nb_interventions, " +
                        "SUM(CASE WHEN fi.etat = 'TERMINEE' THEN 1 ELSE 0 END) as nb_terminees, " +
                        "SUM(CASE WHEN fi.etat = 'EN_ATTENTE' THEN 1 ELSE 0 END) as nb_en_attente, " +
                        "SUM(CASE WHEN fi.etat = 'EN_COURS' THEN 1 ELSE 0 END) as nb_en_cours, " +
                        "ROUND(SUM(CASE WHEN fi.etat = 'TERMINEE' THEN 1 ELSE 0 END) * 100.0 / COUNT(fi.id), 2) as taux_resolution " +
                        "FROM dw_faits_interventions fi JOIN dw_dim_technicien dtech ON fi.technicien_id = dtech.id " +
                        "GROUP BY dtech.id, dtech.nom, dtech.prenom, dtech.fonction ORDER BY nb_interventions DESC"
        ).executeUpdate();

        entityManager.createNativeQuery(
                "CREATE OR REPLACE VIEW vue_produits_plus_utilises AS " +
                        "SELECT dp.code_produit, dp.designation, dp.categorie, COUNT(fs.id) as nb_mouvements, " +
                        "SUM(CASE WHEN fs.type_mvt = 'SORTIE' THEN ABS(fs.quantite) ELSE 0 END) as total_sorties, " +
                        "SUM(CASE WHEN fs.type_mvt = 'ENTREE' THEN fs.quantite ELSE 0 END) as total_entrees, " +
                        "SUM(fs.quantite) as stock_net " +
                        "FROM dw_faits_stock fs JOIN dw_dim_produit dp ON fs.produit_id = dp.id " +
                        "GROUP BY dp.id, dp.code_produit, dp.designation, dp.categorie ORDER BY total_sorties DESC"
        ).executeUpdate();

        entityManager.createNativeQuery(
                "CREATE OR REPLACE VIEW vue_tableau_bord_global AS " +
                        "SELECT COUNT(DISTINCT fi.intervention_id) as total_interventions, " +
                        "COUNT(DISTINCT fi.technicien_id) as nb_techniciens_actifs, " +
                        "ROUND(SUM(CASE WHEN fi.etat = 'TERMINEE' THEN 1 ELSE 0 END) * 100.0 / COUNT(fi.id), 2) as taux_resolution_global, " +
                        "SUM(fi.nb_produits) as total_produits_utilises, " +
                        "COUNT(DISTINCT fs.mvt_id) as total_mouvements_stock " +
                        "FROM dw_faits_interventions fi " +
                        "CROSS JOIN (SELECT COUNT(*) as total_mouvements_stock, COUNT(DISTINCT mvt_id) as mvt_id FROM dw_faits_stock) fs"
        ).executeUpdate();
    }
}