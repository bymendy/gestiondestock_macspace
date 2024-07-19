package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Builder
public class EntrepriseDto {
    private Integer id;

    /**
     * Le nom de l'entreprise.
     */
    private String nom;

    /**
     * Une description de l'entreprise.
     */
    private String description;

    /**
     * Le code fiscal de l'entreprise.
     */
    private String codeFiscal;

    /**
     * Une photo de l'entreprise.
     */
    private String photo;

    /**
     * L'adresse email de l'entreprise.
     */
    private String email;

    /**
     * Le numéro de téléphone de l'entreprise.
     */
    private String numTel;

    /**
     * Le site web de l'entreprise.
     */
    private String siteWeb;

    private List<UtilisateurDto> utilisateurs;
}
