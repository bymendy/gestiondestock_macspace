package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.LigneIntervention;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO pour l'entité {@link LigneIntervention} dans MacSpace.
 * Assure le transfert des données de ligne d'intervention
 * entre l'API et les clients externes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LigneInterventionDto {

    /**
     * Identifiant unique de la ligne d'intervention.
     */
    private Integer id;

    /**
     * Numéro de contrat associé.
     */
    private String numeroContrat;

    /**
     * Intervention associée à cette ligne.
     */
    private InterventionDto intervention;

    /**
     * Produit utilisé lors de cette intervention.
     */
    private ProduitDto produit;

    /**
     * Description de la problématique rencontrée.
     */
    private String problematique;

    /**
     * Quantité de produit utilisée.
     */
    private BigDecimal quantite;

    /**
     * Identifiant de l'entreprise — multi-tenant.
     */
    private Integer idEntreprise;

    /**
     * Convertit une entité {@link LigneIntervention} en DTO.
     *
     * @param ligneIntervention L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static LigneInterventionDto fromEntity(LigneIntervention ligneIntervention) {
        if (ligneIntervention == null) {
            return null;
        }
        return LigneInterventionDto.builder()
                .id(ligneIntervention.getId())
                // intervention non mappée — évite référence circulaire
                .produit(ProduitDto.fromEntity(ligneIntervention.getProduit()))
                .numeroContrat(ligneIntervention.getNumeroContrat())
                .problematique(ligneIntervention.getProblematique())
                .quantite(ligneIntervention.getQuantite())
                .idEntreprise(ligneIntervention.getIdEntreprise())
                .build();
    }

    /**
     * Convertit un DTO en entité {@link LigneIntervention}.
     *
     * @param dto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static LigneIntervention toEntity(LigneInterventionDto dto) {
        if (dto == null) {
            return null;
        }
        LigneIntervention ligne = LigneIntervention.builder()
                .intervention(InterventionDto.toEntity(dto.getIntervention()))
                .produit(ProduitDto.toEntity(dto.getProduit()))
                .numeroContrat(dto.getNumeroContrat())
                .problematique(dto.getProblematique())
                .quantite(dto.getQuantite())
                .build();
        ligne.setIdEntreprise(dto.getIdEntreprise());
        return ligne;
    }
}