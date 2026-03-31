package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entité représentant une adresse postale dans MacSpace.
 *
 * Utilisée par les entités : Client, Fournisseur, Entreprise, Utilisateur.
 * Stockée dans la table 'adresse' en base de données.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "adresse")
public class Adresse extends AbstractEntity {

    /**
     * Première ligne de l'adresse (numéro et nom de rue).
     */
    @Column(name = "adresse1", nullable = false)
    private String adresse1;

    /**
     * Deuxième ligne de l'adresse (complément d'adresse).
     */
    @Column(name = "adresse2")
    private String adresse2;

    /**
     * Ville de l'adresse.
     */
    @Column(name = "ville", nullable = false)
    private String ville;

    /**
     * Code postal de l'adresse.
     */
    @Column(name = "code_postal", nullable = false)
    private String codePostal;

    /**
     * Pays de l'adresse.
     */
    @Column(name = "pays", nullable = false)
    private String pays;
}