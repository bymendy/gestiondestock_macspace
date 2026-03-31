package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Adresse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour l'entité {@link Adresse} dans MacSpace.
 * Assure le transfert des données d'adresse entre
 * l'API et les clients externes (web, mobile).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdresseDto {

    /**
     * Identifiant unique de l'adresse.
     */
    private Integer id;

    /**
     * Première ligne de l'adresse.
     */
    private String adresse1;

    /**
     * Deuxième ligne de l'adresse (complément).
     */
    private String adresse2;

    /**
     * Ville de l'adresse.
     */
    private String ville;

    /**
     * Code postal de l'adresse.
     */
    private String codePostal;

    /**
     * Pays de l'adresse.
     */
    private String pays;

    /**
     * Convertit une entité {@link Adresse} en DTO {@link AdresseDto}.
     *
     * @param adresse L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static AdresseDto fromEntity(Adresse adresse) {
        if (adresse == null) {
            return null;
        }
        return AdresseDto.builder()
                .id(adresse.getId())
                .adresse1(adresse.getAdresse1())
                .adresse2(adresse.getAdresse2())
                .ville(adresse.getVille())
                .codePostal(adresse.getCodePostal())
                .pays(adresse.getPays())
                .build();
    }

    /**
     * Convertit un DTO {@link AdresseDto} en entité {@link Adresse}.
     *
     * @param adresseDto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static Adresse toEntity(AdresseDto adresseDto) {
        if (adresseDto == null) {
            return null;
        }
        return Adresse.builder()
                .id(adresseDto.getId())
                .adresse1(adresseDto.getAdresse1())
                .adresse2(adresseDto.getAdresse2())
                .ville(adresseDto.getVille())
                .codePostal(adresseDto.getCodePostal())
                .pays(adresseDto.getPays())
                .build();
    }
}