package com.macspace.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.model.Entreprise;
import com.macspace.gestiondestock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;


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

    private AdresseDto adresse;


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

    @JsonIgnore
    private List<UtilisateurDto> utilisateurs;

    public static EntrepriseDto fromEntity(Entreprise entreprise) {
        if (entreprise == null) {
            return null;
        }
        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .description(entreprise.getDescription())
                .adresse(AdresseDto.fromEntity(entreprise.getAdresse()))
                .codeFiscal(entreprise.getCodeFiscal())
                .photo(entreprise.getPhoto())
                .email(entreprise.getEmail())
                .numTel(entreprise.getNumTel())
                .build();
    }

    public static Entreprise toEntity(EntrepriseDto dto) {
        if (dto == null) {
            return null;
        }
        Entreprise entreprise = new Entreprise();
        entreprise.setId(dto.getId());
        entreprise.setNom(dto.getNom());
        entreprise.setDescription(dto.getDescription());
        entreprise.setAdresse(AdresseDto.toEntity(dto.getAdresse()));
        entreprise.setCodeFiscal(dto.getCodeFiscal());
        entreprise.setPhoto(dto.getPhoto());
        entreprise.setEmail(dto.getEmail());
        entreprise.setNumTel(dto.getNumTel());

        return entreprise;
    }

}
