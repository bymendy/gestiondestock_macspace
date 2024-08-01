package com.macspace.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;


import java.time.Instant;

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
}
