package com.macspace.gestiondestock.dto.datawarehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la vue vue_performance_techniciens du Data Warehouse MacSpace.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceTechnicienDto {
    private String nom;
    private String prenom;
    private String fonction;
    private Long nbInterventions;
    private Long nbTerminees;
    private Long nbEnAttente;
    private Long nbEnCours;
    private Double tauxResolution;
}