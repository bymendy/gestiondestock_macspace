package com.macspace.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder

public class AdresseDto {

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
}
