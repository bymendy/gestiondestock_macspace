package com.macspace.gestiondestock.controller;

import com.macspace.gestiondestock.dto.datawarehouse.*;
import com.macspace.gestiondestock.services.DataWarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST Data Warehouse MacSpace.
 * Expose les endpoints analytiques réservés aux administrateurs.
 * Accès restreint aux utilisateurs ayant le rôle ROLE_ADMIN.
 */
@Slf4j
@RestController
@RequestMapping("/gestiondestock/v1/datawarehouse")
@RequiredArgsConstructor
public class DataWarehouseController {

    private final DataWarehouseService dataWarehouseService;

    /**
     * Vérifie si l'utilisateur connecté est ADMIN.
     */
    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    /**
     * Récupère les interventions par mois.
     * Accès réservé aux ROLE_ADMIN.
     */
    @GetMapping("/interventions-par-mois")
    public ResponseEntity<List<InterventionParMoisDto>> getInterventionsParMois() {
        if (!isAdmin()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(dataWarehouseService.getInterventionsParMois());
    }

    /**
     * Récupère la performance des techniciens.
     * Accès réservé aux ROLE_ADMIN.
     */
    @GetMapping("/performance-techniciens")
    public ResponseEntity<List<PerformanceTechnicienDto>> getPerformanceTechniciens() {
        if (!isAdmin()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(dataWarehouseService.getPerformanceTechniciens());
    }

    /**
     * Récupère les produits les plus utilisés.
     * Accès réservé aux ROLE_ADMIN.
     */
    @GetMapping("/produits-plus-utilises")
    public ResponseEntity<List<ProduitPlusUtiliseDto>> getProduitsLesPlusUtilises() {
        if (!isAdmin()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(dataWarehouseService.getProduitsLesPlusUtilises());
    }

    /**
     * Récupère le tableau de bord global.
     * Accès réservé aux ROLE_ADMIN.
     */
    @GetMapping("/tableau-bord-global")
    public ResponseEntity<TableauBordGlobalDto> getTableauBordGlobal() {
        if (!isAdmin()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(dataWarehouseService.getTableauBordGlobal());
    }

    /**
     * Initialise le Data Warehouse — endpoint temporaire ADMIN.
     * À supprimer après la première exécution en production.
     */
    @PostMapping("/init")
    public ResponseEntity<String> initDataWarehouse() {
        if (!isAdmin()) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try {
            dataWarehouseService.initDataWarehouse();
            return ResponseEntity.ok("Data Warehouse initialisé avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur : " + e.getMessage());
        }
    }
}