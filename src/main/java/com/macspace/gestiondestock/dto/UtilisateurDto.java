package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Adresse;
import com.macspace.gestiondestock.model.Entreprise;
import com.macspace.gestiondestock.model.Roles;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
@Builder
public class UtilisateurDto {
    private Integer id;


    private String nom;

    private String prennom;

    private String datedenaissance;

    private String fonction;

    private String password;

    private AdresseDto adresse;

    private String photo;

    private EntrepriseDto entreprise;

    private List<RolesDto> roles;
}
