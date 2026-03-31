package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.EtatIntervention;
import com.macspace.gestiondestock.model.InterventionClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * DTO pour l'entité {@link InterventionClient} dans MacSpace.
 * Assure le transfert des données d'intervention client
 * entre l'API et les clients externes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterventionClientDto {

    /**
     * Identifiant unique de l'intervention.
     */
    private Integer id;

    /**
     * Code unique de l'intervention.
     */
    private String code;

    /**
     * Date de l'intervention.
     */
    private Instant dateIntervention;

    /**
     * Client associé à l'intervention.
     */
    private ClientDto client;

    /**
     * État actuel de l'intervention.
     */
    private EtatIntervention etatIntervention;

    /**
     * Identifiant de l'entreprise — multi-tenant.
     */
    private Integer idEntreprise;

    /**
     * Liste des lignes associées à l'intervention client.
     */
    private List<LigneInterventionClientDto> ligneInterventionClients;

    /**
     * Convertit une entité {@link InterventionClient} en DTO.
     * Les ligneInterventionClients ne sont pas mappées pour
     * éviter le LazyInitializationException hors session Hibernate.
     *
     * @param interventionClient L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static InterventionClientDto fromEntity(InterventionClient interventionClient) {
        if (interventionClient == null) {
            return null;
        }
        return InterventionClientDto.builder()
                .id(interventionClient.getId())
                .code(interventionClient.getCode())
                .dateIntervention(interventionClient.getDateIntervention())
                .etatIntervention(interventionClient.getEtatIntervention())
                .client(ClientDto.fromEntity(interventionClient.getClient()))
                .idEntreprise(interventionClient.getIdEntreprise())
                .ligneInterventionClients(
                        interventionClient.getLigneInterventionClients() != null
                                ? interventionClient.getLigneInterventionClients().stream()
                                .map(LigneInterventionClientDto::fromEntity)
                                .toList()
                                : null)
                .build();
    }

    /**
     * Convertit un DTO en entité {@link InterventionClient}.
     *
     * @param dto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static InterventionClient toEntity(InterventionClientDto dto) {
        if (dto == null) {
            return null;
        }
        InterventionClient interventionClient = InterventionClient.builder()
                .code(dto.getCode())
                .etatIntervention(dto.getEtatIntervention())
                .client(ClientDto.toEntity(dto.getClient()))
                .build();
        interventionClient.setIdEntreprise(dto.getIdEntreprise());
        return interventionClient;
    }

    /**
     * Vérifie si l'intervention est terminée.
     *
     * @return true si l'état est TERMINEE.
     */
    public boolean isInterventionTerminee() {
        return EtatIntervention.TERMINEE.equals(this.etatIntervention);
    }
}