package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Category;
import com.macspace.gestiondestock.model.Produits;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) pour les produits.
 * <p>
 * Cette classe est utilisée pour transférer les informations relatives aux produits dans l'application.
 * Elle contient des données telles que le code du produit, sa désignation, les prix, et la catégorie associée.
 * </p>
 */
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
     * Convertit une entité {@link Produits} en DTO {@link ProduitDto}.
     *
     * @param produit L'entité {@link Produits} à convertir.
     * @return Le DTO {@link ProduitDto} correspondant, ou {@code null} si l'entité est {@code null}.
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
     * Convertit un DTO {@link ProduitDto} en entité {@link Produits}.
     *
     * @param produitDto Le DTO {@link ProduitDto} à convertir.
     * @return L'entité {@link Produits} correspondant, ou {@code null} si le DTO est {@code null}.
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
