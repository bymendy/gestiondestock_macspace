package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Category;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
}
