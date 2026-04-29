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
}