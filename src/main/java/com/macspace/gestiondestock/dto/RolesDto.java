package com.macspace.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.model.Roles;
import com.macspace.gestiondestock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) pour les rôles.
 * <p>
 * Cette classe est utilisée pour transférer les données des rôles entre les couches de l'application.
 * Elle contient des informations telles que l'identifiant et le nom du rôle. Le champ {@code utilisateur}
 * est ignoré lors de la sérialisation JSON pour éviter les boucles infinies et la surcharge des données.
 * </p>
 */
@Data
@Builder
public class RolesDto {
    private Integer id;

    /**
     * Le nom du rôle.
     */
    private String roleName;

    /**
     * L'utilisateur associé à ce rôle. Ce champ est ignoré lors de la sérialisation JSON.
     */
    @JsonIgnore
    private UtilisateurDto utilisateur;

    /**
     * Convertit une entité {@link Roles} en un DTO {@link RolesDto}.
     *
     * @param roles L'entité rôle à convertir.
     * @return Un DTO représentant le rôle, ou {@code null} si l'entité est {@code null}.
     */
    public static RolesDto fromEntity(Roles roles) {
        if (roles == null) {
            return null;
        }
        return RolesDto.builder()
                .id(roles.getId())
                .roleName(roles.getRoleName())
                .build();
    }

    /**
     * Convertit un DTO {@link RolesDto} en une entité {@link Roles}.
     *
     * @param dto Le DTO à convertir.
     * @return Une entité rôle correspondant au DTO, ou {@code null} si le DTO est {@code null}.
     */
    public static Roles toEntity(RolesDto dto) {
        if (dto == null) {
            return null;
        }
        Roles roles = new Roles();
        roles.setId(dto.getId());
        roles.setRoleName(dto.getRoleName());
        roles.setUtilisateur(UtilisateurDto.toEntity(dto.getUtilisateur()));
        return roles;
    }
}
