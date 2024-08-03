package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.EtatIntervention;
import com.macspace.gestiondestock.model.InterventionClient;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * Objet de Transfert de Données pour {@link InterventionClient}.
 */
@Data
@Builder
public class InterventionClientDto {

    /**
     * L'identifiant unique de l'intervention.
     */
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
     * Le client associé à l'intervention.
     */
    private ClientDto client;

    /**
     * L'état de l'intervention.
     */
    private EtatIntervention etatIntervention;

    /**
     * La liste des lignes associées à l'intervention client.
     */
    private List<LigneInterventionClientDto> ligneInterventionClients;

    /**
     * Convertit une entité {@link InterventionClient} en un {@link InterventionClientDto}.
     *
     * @param interventionClient l'entité à convertir
     * @return le DTO converti, ou null si l'entité d'entrée est null
     */
    public static InterventionClientDto fromEntity(InterventionClient interventionClient) {
        if (interventionClient == null) {
            return null;
        }
        return InterventionClientDto.builder()
                .id(interventionClient.getId())
                .code(interventionClient.getCode())
                .dateIntervention(interventionClient.getCreationDate())
                .etatIntervention(interventionClient.getEtatIntervention())
                .client(ClientDto.fromEntity(interventionClient.getClient()))
                .build();
    }

    /**
     * Convertit un {@link InterventionClientDto} en une entité {@link InterventionClient}.
     *
     * @param dto le DTO à convertir
     * @return l'entité convertie, ou null si le DTO d'entrée est null
     */
    public static InterventionClient toEntity(InterventionClientDto dto) {
        if (dto == null) {
            return null;
        }
        InterventionClient interventionClient = new InterventionClient();
        interventionClient.setId(dto.getId());
        interventionClient.setCode(dto.getCode());
        interventionClient.setClient(ClientDto.toEntity(dto.getClient()));
        interventionClient.setEtatIntervention(dto.getEtatIntervention());

        return interventionClient;
    }

    /**
     * Vérifie si l'intervention est terminée.
     *
     * @return true si l'état de l'intervention est {@link EtatIntervention#TERMINEE}, false sinon
     */
    public boolean isInterventionTerminee() {
        return EtatIntervention.TERMINEE.equals(this.etatIntervention);
    }
}
