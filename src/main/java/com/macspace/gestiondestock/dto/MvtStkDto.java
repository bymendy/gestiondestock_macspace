package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.MvtStk;
import com.macspace.gestiondestock.model.SourceMvtStk;
import com.macspace.gestiondestock.model.TypeMvtStk;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO pour l'entité {@link MvtStk} dans MacSpace.
 * Assure le transfert des données de mouvement de stock
 * entre l'API et les clients externes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MvtStkDto {

    /**
     * Identifiant unique du mouvement de stock.
     */
    private Integer id;

    /**
     * Date du mouvement de stock.
     */
    private Instant dateMvt;

    /**
     * Quantité du mouvement de stock.
     */
    private BigDecimal quantite;

    /**
     * Produit associé au mouvement de stock.
     */
    private ProduitDto produit;

    /**
     * Type du mouvement de stock.
     */
    private TypeMvtStk typeMvt;

    /**
     * Source du mouvement de stock.
     */
    private SourceMvtStk sourceMvt;

    /**
     * Identifiant de l'entreprise — multi-tenant.
     */
    private Integer idEntreprise;

    /**
     * Convertit une entité {@link MvtStk} en DTO.
     *
     * @param mvtStk L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static MvtStkDto fromEntity(MvtStk mvtStk) {
        if (mvtStk == null) {
            return null;
        }
        return MvtStkDto.builder()
                .id(mvtStk.getId())
                .dateMvt(mvtStk.getDateMvt())
                .quantite(mvtStk.getQuantite())
                .produit(ProduitDto.fromEntity(mvtStk.getProduit()))
                .typeMvt(mvtStk.getTypeMvt())
                .sourceMvt(mvtStk.getSourceMvt())
                .idEntreprise(mvtStk.getIdEntreprise())
                .build();
    }

    /**
     * Convertit un DTO en entité {@link MvtStk}.
     *
     * @param dto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static MvtStk toEntity(MvtStkDto dto) {
        if (dto == null) {
            return null;
        }
        MvtStk mvtStk = MvtStk.builder()
                .dateMvt(dto.getDateMvt())
                .quantite(dto.getQuantite())
                .produit(ProduitDto.toEntity(dto.getProduit()))
                .typeMvt(dto.getTypeMvt())
                .sourceMvt(dto.getSourceMvt())
                .build();
        mvtStk.setIdEntreprise(dto.getIdEntreprise());
        return mvtStk;
    }
}