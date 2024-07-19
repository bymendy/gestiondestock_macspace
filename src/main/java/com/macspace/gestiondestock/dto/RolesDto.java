package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
public class RolesDto {
    private Integer id;


    /**
     * Le nom du rôle.
     */
    private String roleName;

    private UtilisateurDto utilisateur;
}
