package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.InterventionClient;
import com.macspace.gestiondestock.model.Interventions;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Data
@Builder
public class LigneInterventionDto {
    private Integer id;



    private InterventionsDto interventions;

    private Instant creationdate;

    private String problematique;
}
