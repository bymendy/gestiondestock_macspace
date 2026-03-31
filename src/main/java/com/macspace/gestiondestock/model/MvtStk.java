package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Entité représentant un mouvement de stock dans MacSpace.
 *
 * Un mouvement de stock enregistre chaque entrée ou sortie
 * de produit dans le système (commande fournisseur,
 * intervention, correction manuelle).
 * Mappée à la table 'mvtstk' en base de données.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mvtstk")
public class MvtStk extends AbstractEntity {

    /**
     * Date du mouvement de stock.
     */
    @Column(name = "dateMvt", nullable = false)
    private Instant dateMvt;

    /**
     * Quantité de produits déplacés.
     */
    @Column(name = "quantite", nullable = false,
            precision = 10, scale = 2)
    private BigDecimal quantite;

    /**
     * Produit associé au mouvement de stock.
     * EAGER pour éviter le LazyInitializationException
     * lors de la conversion en DTO hors session Hibernate.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProduit")
    private Produit produit;

    /**
     * Source du mouvement de stock.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "sourceMvt")
    private SourceMvtStk sourceMvt;

    /**
     * Type du mouvement de stock.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "typeMvt", nullable = false)
    private TypeMvtStk typeMvt;
}