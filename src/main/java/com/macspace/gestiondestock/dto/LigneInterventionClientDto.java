package com.macspace.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.model.LigneInterventionClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO pour l'entité {@link LigneInterventionClient} dans MacSpace.
 * Assure le transfert des données de ligne d'intervention client
 * entre l'API et les clients externes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LigneInterventionClientDto {

    /**
     * Identifiant unique de la ligne d'intervention.
     */
    private Integer id;

    /**
     * Produit associé à la ligne d'intervention.
     */
    private ProduitDto produit;

    /**
     * Intervention client associée à cette ligne.
     */
    @JsonIgnore
    private InterventionClientDto interventionClient;

    /**
     * Numéro de contrat lié à la ligne d'intervention.
     */
    private String numeroContrat;

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
     * Convertit une entité {@link LigneInterventionClient} en DTO.
     *
     * @param ligneInterventionClient L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static LigneInterventionClientDto fromEntity(
            LigneInterventionClient ligneInterventionClient) {
        if (ligneInterventionClient == null) {
            return null;
        }
        return LigneInterventionClientDto.builder()
                .id(ligneInterventionClient.getId())
                .produit(ProduitDto.fromEntity(ligneInterventionClient.getProduit()))
                // interventionClient non mappé — évite référence circulaire
                .numeroContrat(ligneInterventionClient.getNumeroContrat())
                .problematique(ligneInterventionClient.getProblematique())
                .quantite(ligneInterventionClient.getQuantite())
                .idEntreprise(ligneInterventionClient.getIdEntreprise())
                .build();
    }

    /**
     * Convertit un DTO en entité {@link LigneInterventionClient}.
     *
     * @param dto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static LigneInterventionClient toEntity(LigneInterventionClientDto dto) {
        if (dto == null) {
            return null;
        }
        LigneInterventionClient ligne = LigneInterventionClient.builder()
                .produit(ProduitDto.toEntity(dto.getProduit()))
                .interventionClient(InterventionClientDto.toEntity(dto.getInterventionClient()))
                .numeroContrat(dto.getNumeroContrat())
                .problematique(dto.getProblematique())
                .quantite(dto.getQuantite())
                .build();
        ligne.setIdEntreprise(dto.getIdEntreprise());
        return ligne;
    }
}