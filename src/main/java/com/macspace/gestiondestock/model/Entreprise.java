package com.macspace.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Entité représentant l'entreprise Mac Sécurité dans MacSpace.
 *
 * L'entreprise est l'entité racine du système.
 * Elle est liée à une adresse et possède plusieurs utilisateurs.
 * Mappée à la table 'entreprise' en base de données.
 *
 * Note : Cette entité n'utilise pas idEntreprise
 * car elle EST l'entreprise racine.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "entreprise")
public class Entreprise extends AbstractEntity {

    /**
     * Nom de l'entreprise.
     */
    @Column(name = "nom", nullable = false)
    private String nom;

    /**
     * Description de l'entreprise.
     */
    @Column(name = "description")
    private String description;

    /**
     * Adresse postale de l'entreprise.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_adresse", referencedColumnName = "id")
    private Adresse adresse;

    /**
     * Code fiscal de l'entreprise.
     */
    @Column(name = "code_fiscal", nullable = false, unique = true)
    private String codeFiscal;

    /**
     * Photo de l'entreprise (URL Flickr).
     */
    @Column(name = "photo")
    private String photo;

    /**
     * Adresse e-mail de l'entreprise.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Numéro de téléphone de l'entreprise.
     */
    @Column(name = "num_tel", nullable = false)
    private String numTel;

    /**
     * Site web de l'entreprise.
     */
    @Column(name = "site_web")
    private String siteWeb;

    /**
     * Liste des utilisateurs associés à cette entreprise.
     */
    @OneToMany(mappedBy = "entreprise", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Utilisateur> utilisateurs;
}