package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Entité représentant une ligne d'intervention technique dans MacSpace.
 *
 * Chaque ligne est associée à une intervention et à un produit utilisé.
 * Elle détaille le numéro de contrat, la problématique rencontrée
 * et la quantité de produit utilisée.
 * Mappée à la table 'ligneintervention' en base de données.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ligneintervention")
public class LigneIntervention extends AbstractEntity {

    /**
     * Numéro de contrat associé à cette ligne d'intervention.
     */
    @Column(name = "numero_contrat")
    private String numeroContrat;

    /**
     * Description de la problématique rencontrée.
     */
    @Column(name = "problematique", columnDefinition = "TEXT")
    private String problematique;

    /**
     * Quantité de produit utilisée lors de cette intervention.
     */
    @Column(name = "quantite", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantite;

    /**
     * Intervention associée à cette ligne.
     * LAZY — on n'a pas besoin de l'intervention dans la ligne.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_intervention", referencedColumnName = "id")
    private Intervention intervention;

    /**
     * Produit utilisé lors de cette ligne d'intervention.
     * EAGER pour éviter le LazyInitializationException.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produit", referencedColumnName = "id")
    private Produit produit;
}