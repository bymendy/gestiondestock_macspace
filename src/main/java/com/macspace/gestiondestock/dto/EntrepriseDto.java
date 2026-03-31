package com.macspace.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.model.Adresse;
import com.macspace.gestiondestock.model.Entreprise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO pour l'entité {@link Entreprise} dans MacSpace.
 * Assure le transfert des données d'entreprise entre
 * l'API et les clients externes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntrepriseDto {

    /**
     * Identifiant unique de l'entreprise.
     */
    private Integer id;

    /**
     * Nom de l'entreprise.
     */
    private String nom;

    /**
     * Description de l'entreprise.
     */
    private String description;

    /**
     * Adresse postale de l'entreprise.
     */
    private AdresseDto adresse;

    /**
     * Code fiscal de l'entreprise.
     */
    private String codeFiscal;

    /**
     * Photo de l'entreprise (URL Flickr).
     */
    private String photo;

    /**
     * Adresse email de l'entreprise.
     */
    private String email;

    /**
     * Numéro de téléphone de l'entreprise.
     */
    private String numTel;

    /**
     * Site web de l'entreprise.
     */
    private String siteWeb;

    /**
     * Liste des utilisateurs de l'entreprise.
     * Ignorée en JSON pour éviter les références circulaires.
     */
    @JsonIgnore
    private List<UtilisateurDto> utilisateurs;

    /**
     * Convertit une entité {@link Entreprise} en DTO.
     *
     * @param entreprise L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
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
                .siteWeb(entreprise.getSiteWeb())
                .build();
    }

    /**
     * Convertit un DTO en entité {@link Entreprise}.
     *
     * @param dto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static Entreprise toEntity(EntrepriseDto dto) {
        if (dto == null) {
            return null;
        }

        // Reconstruction de l'adresse avec son ID pour éviter
        // le problème "detached entity passed to persist"
        Adresse adresse = null;
        if (dto.getAdresse() != null) {
            adresse = Adresse.builder()
                    .id(dto.getAdresse().getId())
                    .adresse1(dto.getAdresse().getAdresse1())
                    .adresse2(dto.getAdresse().getAdresse2())
                    .ville(dto.getAdresse().getVille())
                    .codePostal(dto.getAdresse().getCodePostal())
                    .pays(dto.getAdresse().getPays())
                    .build();
        }

        return Entreprise.builder()
                .id(dto.getId())              // ← ID ajouté
                .nom(dto.getNom())
                .description(dto.getDescription())
                .adresse(adresse)             // ← Adresse avec ID
                .codeFiscal(dto.getCodeFiscal())
                .photo(dto.getPhoto())
                .email(dto.getEmail())
                .numTel(dto.getNumTel())
                .siteWeb(dto.getSiteWeb())
                .build();
    }
}