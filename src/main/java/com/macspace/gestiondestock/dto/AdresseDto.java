package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Adresse;
import lombok.Builder;
import lombok.Data;

// Data Transfer Oject : c'est une Couche qui permet d'assurer la communication entre notre api et
// les clients externes tels que notre application web
/**
 * Classe de transfert de données (DTO) pour l'entité Adresse.
 * <p>
 * Cette classe sert à transférer les données de l'entité Adresse entre les différentes couches de l'application.
 * </p>
 */
@Data
@Builder
public class AdresseDto {

    /**
     * L'identifiant unique de l'adresse.
     */
    private Integer id;

    /**
     * La première ligne de l'adresse.
     */
    private String adresse1;

    /**
     * La deuxième ligne de l'adresse.
     */
    private String adresse2;

    /**
     * La ville de l'adresse.
     */
    private String ville;

    /**
     * Le code postal de l'adresse.
     */
    private String codePostale;

    /**
     * Le pays de l'adresse.
     */
    private String pays;

    /**
     * Convertit une entité Adresse en DTO AdresseDto.
     *
     * @param adresse L'entité Adresse à convertir.
     * @return Le DTO AdresseDto correspondant à l'entité Adresse.
     */
    public static AdresseDto fromEntity(Adresse adresse) {
        if (adresse == null) {
            return null;
        }

        return AdresseDto.builder()
                .adresse1(adresse.getAdresse1())
                .adresse2(adresse.getAdresse2())
                .ville(adresse.getVille())
                .codePostale(adresse.getCodePostale())
                .pays(adresse.getPays())
                .build();
    }

    /**
     * Convertit un DTO AdresseDto en entité Adresse.
     *
     * @param adresseDto Le DTO AdresseDto à convertir.
     * @return L'entité Adresse correspondant au DTO AdresseDto.
     */
    public static Adresse toEntity(AdresseDto adresseDto) {
        if (adresseDto == null) {
            return null;
        }

        Adresse adresse = new Adresse();

        adresse.setAdresse1(adresseDto.getAdresse1());
        adresse.setAdresse2(adresseDto.getAdresse2());
        adresse.setVille(adresseDto.getVille());
        adresse.setCodePostale(adresseDto.getCodePostale());
        adresse.setPays(adresseDto.getPays());

        return adresse;
    }
}