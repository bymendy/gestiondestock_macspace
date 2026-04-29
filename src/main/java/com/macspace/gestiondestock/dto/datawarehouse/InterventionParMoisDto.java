package com.macspace.gestiondestock.dto.datawarehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la vue vue_interventions_par_mois du Data Warehouse MacSpace.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterventionParMoisDto {
    private Integer annee;
    private String nomMois;
    private Integer mois;
    private Long nbInterventions;
    private Long totalProduitsUtilises;
    private Long nbTerminees;
    private Double tauxResolution;
}