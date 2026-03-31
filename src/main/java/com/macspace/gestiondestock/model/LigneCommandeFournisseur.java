package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Entité représentant une ligne de commande fournisseur dans MacSpace.
 *
 * Chaque ligne est associée à un produit et à une commande fournisseur.
 * Elle contient la quantité commandée et le prix unitaire négocié.
 * Mappée à la table 'lignecommandefournisseur' en base de données.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lignecommandefournisseur")
public class LigneCommandeFournisseur extends AbstractEntity {

    /**
     * Produit associé à cette ligne de commande.
     * EAGER pour éviter le LazyInitializationException.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produit", referencedColumnName = "id")
    private Produit produit;

    /**
     * Commande fournisseur à laquelle cette ligne appartient.
     * LAZY — on n'a pas besoin de la commande dans la ligne.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande_fournisseur", referencedColumnName = "id")
    private CommandeFournisseur commandeFournisseur;

    /**
     * Quantité commandée pour ce produit.
     */
    @Column(name = "quantite", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantite;

    /**
     * Prix unitaire du produit pour cette commande.
     */
    @Column(name = "prix_unitaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixUnitaire;
}