package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Category;
import com.macspace.gestiondestock.model.Produits;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;

@Builder
@Data
public class ProduitDto {
    private Integer id;

    /**
     * Le code unique du produit.
     */
    private String codeProduit;

    /**
     * La désignation ou le nom du produit.
     */
    private String designation;

    /**
     * Le prix unitaire hors taxes du produit.
     */
    private BigDecimal prixUnitaireHt;

    /**
     * Le taux de TVA appliqué au produit.
     */
    private BigDecimal tauxTva;

    /**
     * Le prix unitaire toutes taxes comprises du produit.
     */
    private BigDecimal prixUnitaireTtc;

    /**
     * Une photo du produit.
     */
    private String photo;

    /**
     * La catégorie à laquelle appartient le produit.
     * Relation ManyToOne avec l'entité {@link Category}.
     */
    private CategoryDto category;

    /**
     * Convertit une entité Produit en DTO ProduitDto.
     *
     * @param produit L'entité Produit à convertir.
     * @return Le DTO ProduitDto correspondant.
     */
    public static ProduitDto fromEntity(Produits produit) {
        if (produit == null) {
            return null;
        }

        return ProduitDto.builder()
                .id(produit.getId())
                .codeProduit(produit.getCodeProduit())
                .designation(produit.getDesignation())
                .prixUnitaireHt(produit.getPrixUnitaireHt())
                .prixUnitaireTtc(produit.getPrixUnitaireTtc())

                // Ajoutez d'autres champs si nécessaire
                .build();
    }

    /**
     * Convertit un DTO ProduitDto en entité Produit.
     *
     * @param produitDto Le DTO ProduitDto à convertir.
     * @return L'entité Produit correspondant.
     */
    public static Produits toEntity(ProduitDto produitDto) {
        if (produitDto == null) {
            return null;
        }

        Produits produit = new Produits();
        produit.setId(produitDto.getId());
        produit.setCodeProduit(produitDto.getCodeProduit());
        produit.setDesignation(produitDto.getDesignation());
        produit.setPrixUnitaireTtc(produitDto.getPrixUnitaireTtc());
        // Ajoutez d'autres champs si nécessaire

        return produit;
    }
}
