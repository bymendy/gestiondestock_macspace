package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Produit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO pour l'entité {@link Produit} dans MacSpace.
 * Assure le transfert des données produit entre
 * l'API et les clients externes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProduitDto {

    /**
     * Identifiant unique du produit.
     */
    private Integer id;

    /**
     * Code unique du produit.
     */
    private String codeProduit;

    /**
     * Désignation ou nom du produit.
     */
    private String designation;

    /**
     * Prix unitaire hors taxes.
     */
    private BigDecimal prixUnitaireHt;

    /**
     * Taux de TVA appliqué.
     */
    private BigDecimal tauxTva;

    /**
     * Prix unitaire toutes taxes comprises.
     */
    private BigDecimal prixUnitaireTtc;

    /**
     * Photo du produit (URL Flickr).
     */
    private String photo;

    /**
     * Catégorie du produit.
     */
    private CategoryDto category;

    /**
     * Fournisseur du produit.
     */
    private FournisseurDto fournisseur;

    /**
     * Identifiant de l'entreprise — multi-tenant.
     */
    private Integer idEntreprise;

    /**
     * Convertit une entité {@link Produit} en DTO.
     *
     * @param produit L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static ProduitDto fromEntity(Produit produit) {
        if (produit == null) {
            return null;
        }
        return ProduitDto.builder()
                .id(produit.getId())
                .codeProduit(produit.getCodeProduit())
                .designation(produit.getDesignation())
                .prixUnitaireHt(produit.getPrixUnitaireHt())
                .tauxTva(produit.getTauxTva())
                .prixUnitaireTtc(produit.getPrixUnitaireTtc())
                .photo(produit.getPhoto())
                .category(CategoryDto.fromEntity(produit.getCategory()))
                .fournisseur(FournisseurDto.fromEntity(produit.getFournisseur()))
                .idEntreprise(produit.getIdEntreprise())
                .build();
    }

    /**
     * Convertit un DTO en entité {@link Produit}.
     *
     * @param produitDto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static Produit toEntity(ProduitDto produitDto) {
        if (produitDto == null) {
            return null;
        }
        Produit produit = Produit.builder()
                .codeProduit(produitDto.getCodeProduit())
                .designation(produitDto.getDesignation())
                .prixUnitaireHt(produitDto.getPrixUnitaireHt())
                .tauxTva(produitDto.getTauxTva())
                .prixUnitaireTtc(produitDto.getPrixUnitaireTtc())
                .photo(produitDto.getPhoto())
                .category(CategoryDto.toEntity(produitDto.getCategory()))
                .fournisseur(FournisseurDto.toEntity(produitDto.getFournisseur()))
                .build();
        produit.setIdEntreprise(produitDto.getIdEntreprise());
        return produit;
    }
}