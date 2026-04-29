package com.macspace.gestiondestock.dto.datawarehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la vue vue_tableau_bord_global du Data Warehouse MacSpace.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableauBordGlobalDto {
    private Long totalInterventions;
    private Long nbTechniciensActifs;
    private Double tauxResolutionGlobal;
    private Long totalProduitsUtilises;
    private Long totalMouvementsStock;
}