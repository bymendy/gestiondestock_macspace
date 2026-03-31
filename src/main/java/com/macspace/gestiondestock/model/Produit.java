package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Entité représentant un produit de sécurité dans MacSpace.
 *
 * Un produit est un équipement ou service de sécurité
 * (caméra, alarme, détecteur...) géré dans le stock.
 * Il peut être lié à des interventions, des commandes
 * fournisseurs et des mouvements de stock.
 * Mappée à la table 'produits' en base de données.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "produits")
public class Produit extends AbstractEntity {

    /**
     * Code unique du produit.
     */
    @Column(name = "codeProduit", nullable = false, unique = true)
    private String codeProduit;

    /**
     * Désignation ou nom du produit.
     */
    @Column(name = "designation", nullable = false)
    private String designation;

    /**
     * Prix unitaire hors taxes.
     */
    @Column(name = "prixUnitaireHt", precision = 10, scale = 2)
    private BigDecimal prixUnitaireHt;

    /**
     * Taux de TVA appliqué.
     */
    @Column(name = "tauxTva", precision = 5, scale = 2)
    private BigDecimal tauxTva;

    /**
     * Prix unitaire toutes taxes comprises.
     */
    @Column(name = "prixUnitaireTtc", precision = 10, scale = 2)
    private BigDecimal prixUnitaireTtc;

    /**
     * Photo du produit (URL Flickr).
     */
    @Column(name = "photo")
    private String photo;

    /**
     * Catégorie du produit.
     * EAGER pour éviter le LazyInitializationException
     * lors de la conversion en DTO hors session Hibernate.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCategory")
    private Category category;

    /**
     * Fournisseur du produit.
     * EAGER pour éviter le LazyInitializationException
     * lors de la conversion en DTO hors session Hibernate.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idFournisseur")
    private Fournisseur fournisseur;

    /**
     * Lignes d'intervention associées.
     */
    @OneToMany(mappedBy = "produit",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<LigneIntervention> ligneInterventions;

    /**
     * Lignes d'intervention client associées.
     */
    @OneToMany(mappedBy = "produit",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<LigneInterventionClient> ligneInterventionClients;

    /**
     * Lignes de commande fournisseur associées.
     */
    @OneToMany(mappedBy = "produit",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;

    /**
     * Mouvements de stock associés.
     */
    @OneToMany(mappedBy = "produit",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<MvtStk> mvtStk;
}