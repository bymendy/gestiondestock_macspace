package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.InterventionClient;
import com.macspace.gestiondestock.model.Interventions;
import lombok.Builder;
import lombok.Data;


import java.time.Instant;

@Data
@Builder
public class LigneInterventionDto {
    private Integer id;

    private String numeroContrat;

    private InterventionsDto interventions;

    private Instant creationdate;

    private String problematique;
}
