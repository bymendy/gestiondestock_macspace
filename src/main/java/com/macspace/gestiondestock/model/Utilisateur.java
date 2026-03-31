package com.macspace.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

/**
 * Entité représentant un utilisateur de MacSpace.
 *
 * Un utilisateur peut être un administrateur, manager,
 * technicien ou client. Il est lié à une entreprise,
 * une adresse et possède un ou plusieurs rôles.
 * Mappée à la table 'utilisateur' en base de données.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "utilisateur")
public class Utilisateur extends AbstractEntity {

    /**
     * Nom de l'utilisateur.
     */
    @Column(name = "nom", nullable = false)
    private String nom;

    /**
     * Prénom de l'utilisateur.
     */
    @Column(name = "prenom", nullable = false)
    private String prenom;

    /**
     * Date de naissance de l'utilisateur.
     */
    @Column(name = "dateDeNaissance")
    private LocalDate dateDeNaissance;

    /**
     * Fonction de l'utilisateur.
     */
    @Column(name = "fonction")
    private String fonction;

    /**
     * Email de l'utilisateur — unique.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Mot de passe de l'utilisateur.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Adresse de l'utilisateur.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAdresse")
    private Adresse adresse;

    /**
     * Photo de l'utilisateur (URL Flickr).
     */
    @Column(name = "photo")
    private String photo;

    /**
     * Entreprise de l'utilisateur.
     * insertable=false, updatable=false car
     * idEntreprise est déjà géré par AbstractEntity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entreprise",
            insertable = false,
            updatable = false)
    private Entreprise entreprise;

    /**
     * Rôles de l'utilisateur.
     */
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "utilisateur",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Role> roles;
}