package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.EtatIntervention;
import com.macspace.gestiondestock.model.Intervention;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * DTO pour l'entité {@link Intervention} dans MacSpace.
 * Assure le transfert des données d'intervention technique
 * entre l'API et les clients externes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterventionDto {

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
     * Description de la problématique rencontrée.
     */
    private String problematique;

    /**
     * État actuel de l'intervention.
     */
    private EtatIntervention etatIntervention;

    /**
     * Technicien responsable de l'intervention.
     */
    private UtilisateurDto technicien;

    /**
     * Identifiant de l'entreprise — multi-tenant.
     */
    private Integer idEntreprise;

    /**
     * Liste des lignes d'intervention associées.
     */
    private List<LigneInterventionDto> ligneInterventions;

    /**
     * Convertit une entité {@link Intervention} en DTO.
     *
     * @param intervention L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static InterventionDto fromEntity(Intervention intervention) {
        if (intervention == null) {
            return null;
        }
        return InterventionDto.builder()
                .id(intervention.getId())
                .code(intervention.getCode())
                .dateIntervention(intervention.getDateIntervention())
                .problematique(intervention.getProblematique())
                .etatIntervention(intervention.getEtatIntervention())
                .technicien(UtilisateurDto.fromEntity(intervention.getTechnicien()))
                .idEntreprise(intervention.getIdEntreprise())
                .ligneInterventions(
                        intervention.getLigneInterventions() != null
                                ? intervention.getLigneInterventions().stream()
                                .map(LigneInterventionDto::fromEntity)
                                .toList()
                                : null)
                .build();
    }

    /**
     * Convertit un DTO en entité {@link Intervention}.
     *
     * @param dto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static Intervention toEntity(InterventionDto dto) {
        if (dto == null) {
            return null;
        }
        Intervention intervention = Intervention.builder()
                .code(dto.getCode())
                .problematique(dto.getProblematique())
                .etatIntervention(dto.getEtatIntervention())
                .technicien(UtilisateurDto.toEntity(dto.getTechnicien()))
                .build();
        intervention.setIdEntreprise(dto.getIdEntreprise());
        return intervention;
    }
}