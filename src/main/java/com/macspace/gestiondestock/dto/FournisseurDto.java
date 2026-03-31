package com.macspace.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.model.Fournisseur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO pour l'entité {@link Fournisseur} dans MacSpace.
 * Assure le transfert des données fournisseur entre
 * l'API et les clients externes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FournisseurDto {

    /**
     * Identifiant unique du fournisseur.
     */
    private Integer id;

    /**
     * Nom du fournisseur.
     */
    private String nom;

    /**
     * Prénom du fournisseur.
     */
    private String prenom;

    /**
     * Adresse postale du fournisseur.
     */
    private AdresseDto adresse;

    /**
     * Photo du fournisseur (URL Flickr).
     */
    private String photo;

    /**
     * Adresse email du fournisseur.
     */
    private String email;

    /**
     * Numéro de téléphone du fournisseur.
     */
    private String numTel;

    /**
     * Identifiant de l'entreprise — multi-tenant.
     */
    private Integer idEntreprise;

    /**
     * Liste des commandes du fournisseur.
     * Ignorée en JSON pour éviter les références circulaires.
     */
    @JsonIgnore
    private List<CommandeFournisseurDto> commandeFournisseurs;

    /**
     * Convertit une entité {@link Fournisseur} en DTO.
     *
     * @param fournisseur L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static FournisseurDto fromEntity(Fournisseur fournisseur) {
        if (fournisseur == null) {
            return null;
        }
        return FournisseurDto.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                .adresse(AdresseDto.fromEntity(fournisseur.getAdresse())) // ← Remis
                .photo(fournisseur.getPhoto())
                .email(fournisseur.getEmail())
                .numTel(fournisseur.getNumTel())
                .idEntreprise(fournisseur.getIdEntreprise())
                .build();
    }

    /**
     * Convertit un DTO en entité {@link Fournisseur}.
     *
     * @param dto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static Fournisseur toEntity(FournisseurDto dto) {
        if (dto == null) {
            return null;
        }
        Fournisseur fournisseur = Fournisseur.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .adresse(AdresseDto.toEntity(dto.getAdresse()))
                .photo(dto.getPhoto())
                .email(dto.getEmail())
                .numTel(dto.getNumTel())
                .build();
        fournisseur.setIdEntreprise(dto.getIdEntreprise());
        return fournisseur;
    }
}