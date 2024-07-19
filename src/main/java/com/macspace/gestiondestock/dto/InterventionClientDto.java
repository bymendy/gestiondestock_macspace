package com.macspace.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class InterventionClientDto {
    private Integer id;


    /**
     * Le code unique de l'intervention.
     */
    private String code;

    /**
     * La date de l'intervention.
     */
    private Instant dateIntervention;


    private ClientDto client;

    private List<LigneInterventionClientDto> ligneInterventionClients;
}
