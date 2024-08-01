package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.InterventionClient;
import com.macspace.gestiondestock.model.Interventions;
import lombok.Builder;
import lombok.Data;


import java.time.Instant;

@Data
@Builder
public class LigneInterventionClientDto {
    private Integer id;

    private InterventionsDto interventions;

    private InterventionClientDto interventionClient;

    /**
     * La date d'ouverture du ticket d'intervention.
     */
    private Instant creationdate;

    /**
     * La description de la problématique rencontrée.
     */
    private String problematique;
}
