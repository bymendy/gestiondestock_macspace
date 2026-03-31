package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.LigneCommandeFournisseur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO pour l'entité {@link LigneCommandeFournisseur} dans MacSpace.
 * Assure le transfert des données de ligne de commande fournisseur
 * entre l'API et les clients externes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LigneCommandeFournisseurDto {

    /**
     * Identifiant unique de la ligne de commande.
     */
    private Integer id;

    /**
     * Produit associé à cette ligne de commande.
     */
    private ProduitDto produit;

    /**
     * Commande fournisseur associée à cette ligne.
     */
    private CommandeFournisseurDto commandeFournisseur;

    /**
     * Quantité commandée.
     */
    private BigDecimal quantite;

    /**
     * Prix unitaire négocié avec le fournisseur.
     */
    private BigDecimal prixUnitaire;

    /**
     * Identifiant de l'entreprise — multi-tenant.
     */
    private Integer idEntreprise;

    /**
     * Convertit une entité {@link LigneCommandeFournisseur} en DTO.
     *
     * @param ligneCommandeFournisseur L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static LigneCommandeFournisseurDto fromEntity(
            LigneCommandeFournisseur ligneCommandeFournisseur) {
        if (ligneCommandeFournisseur == null) {
            return null;
        }
        return LigneCommandeFournisseurDto.builder()
                .id(ligneCommandeFournisseur.getId())
                .produit(ProduitDto.fromEntity(ligneCommandeFournisseur.getProduit()))
                // commandeFournisseur non mappé — évite référence circulaire
                .quantite(ligneCommandeFournisseur.getQuantite())
                .prixUnitaire(ligneCommandeFournisseur.getPrixUnitaire())
                .idEntreprise(ligneCommandeFournisseur.getIdEntreprise())
                .build();
    }

    /**
     * Convertit un DTO en entité {@link LigneCommandeFournisseur}.
     *
     * @param dto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static LigneCommandeFournisseur toEntity(LigneCommandeFournisseurDto dto) {
        if (dto == null) {
            return null;
        }
        LigneCommandeFournisseur ligne = LigneCommandeFournisseur.builder()
                .produit(ProduitDto.toEntity(dto.getProduit()))
                .commandeFournisseur(CommandeFournisseurDto.toEntity(dto.getCommandeFournisseur()))
                .quantite(dto.getQuantite())
                .prixUnitaire(dto.getPrixUnitaire())
                .build();
        ligne.setIdEntreprise(dto.getIdEntreprise());
        return ligne;
    }
}