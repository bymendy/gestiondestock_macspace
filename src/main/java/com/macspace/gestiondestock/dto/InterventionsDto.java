package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Interventions;
import lombok.Builder;
import lombok.Data;


import java.time.Instant;
import java.util.List;

@Data
@Builder
public class InterventionsDto {
    private Integer id;

    /**
     * Le code unique de l'intervention.
     */
    private String code;

    /**
     * La date de l'intervention.
     */
    private Instant dateIntervention;

    /**
     * La description de la problématique rencontrée.
     */
    private String problematique;

    private List<LigneInterventionDto> ligneInterventions;

    private Integer idEntreprise;

    public static InterventionsDto fromEntity(Interventions intervention) {
        if (intervention == null) {
            return null;
        }
        return InterventionsDto.builder()
                .id(intervention.getId())
                .code(intervention.getCode())
                .problematique(intervention.getProblematique())
                .idEntreprise(intervention.getIdEntreprise())
                .build();
    }

    public static Interventions toEntity(InterventionsDto dto) {
        if (dto == null) {
            return null;
        }
        Interventions interventions = new Interventions();
        interventions.setId(dto.getId());
        interventions.setCode(interventions.getCode());
        interventions.setProblematique(dto.getProblematique());
        interventions.setIdEntreprise(dto.getIdEntreprise());
        return interventions;
    }
}
