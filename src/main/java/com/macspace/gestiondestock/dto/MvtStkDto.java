package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.MvtStk;
import com.macspace.gestiondestock.model.SourceMvtStk;
import com.macspace.gestiondestock.model.TypeMvtStk;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Objet de Transfert de Données pour {@link MvtStk}.
 */
@Data
@Builder
public class MvtStkDto {

    /**
     * L'identifiant unique du mouvement de stock.
     */
    private Integer id;

    /**
     * La date du mouvement de stock.
     */
    private Instant dateMvt;

    /**
     * La quantité du mouvement de stock.
     */
    private BigDecimal quantite;

    /**
     * Le produit associé au mouvement de stock.
     */
    private ProduitDto produit;

    /**
     * Le type de mouvement de stock.
     */
    private TypeMvtStk typeMvt;

    /**
     * La source du mouvement de stock.
     */
    private SourceMvtStk sourceMvt;

    /**
     * L'identifiant de l'entreprise associée au mouvement de stock.
     */
    private Integer idEntreprise;

    /**
     * Convertit une entité {@link MvtStk} en un {@link MvtStkDto}.
     *
     * @param mvtStk l'entité à convertir
     * @return le DTO converti, ou null si l'entité d'entrée est null
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
     * Convertit un {@link MvtStkDto} en une entité {@link MvtStk}.
     *
     * @param dto le DTO à convertir
     * @return l'entité convertie, ou null si le DTO d'entrée est null
     */
    public static MvtStk toEntity(MvtStkDto dto) {
        if (dto == null) {
            return null;
        }

        MvtStk mvtStk = new MvtStk();
        mvtStk.setId(dto.getId());
        mvtStk.setDateMvt(dto.getDateMvt());
        mvtStk.setQuantite(dto.getQuantite());
        mvtStk.setProduit(ProduitDto.toEntity(dto.getProduit()));
        mvtStk.setTypeMvt(dto.getTypeMvt());
        mvtStk.setSourceMvt(dto.getSourceMvt());
        mvtStk.setIdEntreprise(dto.getIdEntreprise());
        return mvtStk;
    }
}
