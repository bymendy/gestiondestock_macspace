package com.macspace.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.model.Roles;
import com.macspace.gestiondestock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class RolesDto {
    private Integer id;

    /**
     * Le nom du rôle.
     */
    private String roleName;

    @JsonIgnore
    private UtilisateurDto utilisateur;

    public static RolesDto fromEntity(Roles roles) {
        if (roles == null) {
            return null;
        }
        return RolesDto.builder()
                .id(roles.getId())
                .roleName(roles.getRoleName())
                .build();
    }

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
