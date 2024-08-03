package com.macspace.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.model.InterventionClient;
import com.macspace.gestiondestock.model.Interventions;
import com.macspace.gestiondestock.model.LigneInterventionClient;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * Objet de Transfert de Données pour {@link LigneInterventionClient}.
 */
@Data
@Builder
public class LigneInterventionClientDto {

    /**
     * L'identifiant unique de la ligne d'intervention.
     */
    private Integer id;

    /**
     * Le produit associé à la ligne d'intervention.
     */
    private ProduitDto produit;

    /**
     * Les interventions associées à la ligne d'intervention.
     */
    private InterventionsDto interventions;

    /**
     * L'intervention client associée à cette ligne.
     */
    @JsonIgnore
    private InterventionClientDto interventionClient;

    /**
     * Le numéro de contrat lié à la ligne d'intervention.
     */
    private String numeroContrat;

    /**
     * La date d'ouverture du ticket d'intervention.
     */
    private Instant creationdate;

    /**
     * La description de la problématique rencontrée.
     */
    private String problematique;

    /**
     * Convertit une entité {@link LigneInterventionClient} en un {@link LigneInterventionClientDto}.
     *
     * @param ligneInterventionClient l'entité à convertir
     * @return le DTO converti, ou null si l'entité d'entrée est null
     */
    public static LigneInterventionClientDto fromEntity(LigneInterventionClient ligneInterventionClient) {
        if (ligneInterventionClient == null) {
            return null;
        }
        return LigneInterventionClientDto.builder()
                .id(ligneInterventionClient.getId())
                .produit(ProduitDto.fromEntity(ligneInterventionClient.getProduit()))
                .numeroContrat(ligneInterventionClient.getNumeroContrat())
                .creationdate(ligneInterventionClient.getCreationDate())
                .problematique(ligneInterventionClient.getProblematique())
                .build();
    }

    /**
     * Convertit un {@link LigneInterventionClientDto} en une entité {@link LigneInterventionClient}.
     *
     * @param dto le DTO à convertir
     * @return l'entité convertie, ou null si le DTO d'entrée est null
     */
    public static LigneInterventionClient toEntity(LigneInterventionClientDto dto) {
        if (dto == null) {
            return null;
        }

        LigneInterventionClient ligneInterventionClient = new LigneInterventionClient();
        ligneInterventionClient.setId(dto.getId());
        ligneInterventionClient.setProduit(ProduitDto.toEntity(dto.getProduit()));
        ligneInterventionClient.setNumeroContrat(dto.getNumeroContrat());
        ligneInterventionClient.setCreationDate(dto.getCreationdate());
        ligneInterventionClient.setProblematique(dto.getProblematique());
        return ligneInterventionClient;
    }
}
