package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.InterventionClient;
import com.macspace.gestiondestock.model.Interventions;
import com.macspace.gestiondestock.model.LigneIntervention;
import lombok.Builder;
import lombok.Data;


import java.time.Instant;

@Data
@Builder
public class LigneInterventionDto {
    private Integer id;

    private String numeroContrat;

    private InterventionsDto interventions;

    private ProduitDto produit;


    private Instant creationdate;

    private String problematique;

    private Integer idEntreprise;

    public static LigneInterventionDto fromEntity(LigneIntervention ligneIntervention) {
        if (ligneIntervention == null) {
            return null;
        }

        return LigneInterventionDto.builder()
                .id(ligneIntervention.getId())
                .interventions(InterventionsDto.fromEntity(ligneIntervention.getInterventions()))
                .produit(ProduitDto.fromEntity(ligneIntervention.getProduit()))
                .numeroContrat(ligneIntervention.getNumeroContrat())
                .problematique(ligneIntervention.getProblematique())
                .idEntreprise(ligneIntervention.getIdEntreprise())
                .build();
    }

    public static LigneIntervention toEntity(LigneInterventionDto dto) {
        if (dto == null) {
            return null;
        }
        LigneIntervention ligneIntervention = new LigneIntervention();
        ligneIntervention.setId(dto.getId());
        ligneIntervention.setInterventions(InterventionsDto.toEntity(dto.getInterventions()));
        ligneIntervention.setProduit(ProduitDto.toEntity(dto.getProduit()));
        ligneIntervention.setNumeroContrat(dto.getNumeroContrat());
        ligneIntervention.setProblematique(dto.getProblematique());
        ligneIntervention.setIdEntreprise(dto.getIdEntreprise());
        return ligneIntervention;
    }

}
