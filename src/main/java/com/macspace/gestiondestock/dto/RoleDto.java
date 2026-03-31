package com.macspace.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.model.Role;
import com.macspace.gestiondestock.model.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour l'entité {@link Role} dans MacSpace.
 * Assure le transfert des données de rôle entre
 * l'API et les clients externes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    /**
     * Identifiant unique du rôle.
     */
    private Integer id;

    /**
     * Type/nom du rôle.
     */
    private RoleType roleName;

    /**
     * Utilisateur associé à ce rôle.
     * Ignoré en JSON pour éviter les références circulaires.
     */
    @JsonIgnore
    private UtilisateurDto utilisateur;

    /**
     * Convertit une entité {@link Role} en DTO.
     *
     * @param role L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static RoleDto fromEntity(Role role) {
        if (role == null) {
            return null;
        }
        return RoleDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    /**
     * Convertit un DTO en entité {@link Role}.
     *
     * @param dto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static Role toEntity(RoleDto dto) {
        if (dto == null) {
            return null;
        }
        return Role.builder()
                .roleName(dto.getRoleName())
                .utilisateur(UtilisateurDto.toEntity(dto.getUtilisateur()))
                .build();
    }
}