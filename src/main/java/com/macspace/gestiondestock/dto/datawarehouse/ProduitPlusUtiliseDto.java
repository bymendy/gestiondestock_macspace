package com.macspace.gestiondestock.dto.datawarehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la vue vue_produits_plus_utilises du Data Warehouse MacSpace.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProduitPlusUtiliseDto {
    private String codeProduit;
    private String designation;
    private String categorie;
    private Long nbMouvements;
    private Double totalSorties;
    private Double totalEntrees;
    private Double stockNet;
}