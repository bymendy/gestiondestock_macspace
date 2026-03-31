package com.macspace.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Entité représentant un fournisseur de produits de sécurité dans MacSpace.
 *
 * Un fournisseur possède une adresse, un contact,
 * et peut être lié à plusieurs commandes fournisseurs.
 * La relation avec {@link Adresse} est gérée manuellement
 * dans le service pour éviter les conflits JPA.
 * Mappée à la table 'fournisseur' en base de données.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "fournisseur")
public class Fournisseur extends AbstractEntity {

    /**
     * Nom du fournisseur.
     */
    @Column(name = "nom", nullable = false)
    private String nom;

    /**
     * Prénom du fournisseur.
     */
    @Column(name = "prenom")
    private String prenom;

    /**
     * Adresse postale du fournisseur.
     * EAGER pour éviter le LazyInitializationException
     * lors de la conversion en DTO hors session Hibernate.
     * Pas de cascade — l'adresse est sauvegardée
     * manuellement dans FournisseurServiceImpl.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_adresse", referencedColumnName = "id")
    private Adresse adresse;

    /**
     * Photo du fournisseur (URL Flickr).
     */
    @Column(name = "photo")
    private String photo;

    /**
     * Adresse e-mail du fournisseur.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Numéro de téléphone du fournisseur.
     */
    @Column(name = "num_tel", nullable = false)
    private String numTel;

    /**
     * Liste des commandes passées à ce fournisseur.
     */
    @OneToMany(mappedBy = "fournisseur",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CommandeFournisseur> commandeFournisseurs;
}