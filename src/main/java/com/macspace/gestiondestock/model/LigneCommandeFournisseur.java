package com.macspace.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Classe représentant une ligne de commande fournisseur dans le système de gestion de stock.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>produit : Le produit associé à cette ligne de commande, représenté par une entité {@link Produits}.</li>
 *   <li>commandeFournisseur : La commande fournisseur à laquelle cette ligne appartient, représentée par une entité {@link CommandeFournisseur}.</li>
 * </ul>
 * </p>
 * <p>
 * La classe est annotée avec {@link Entity} et {@link Table} pour indiquer qu'il s'agit
 * d'une entité JPA mappée à la table 'lignecommandefournisseur' de la base de données. Les annotations Lombok
 * {@link Data}, {@link NoArgsConstructor}, {@link AllArgsConstructor}, et {@link EqualsAndHashCode}
 * sont utilisées pour générer automatiquement les méthodes getter, setter, toString, equals et hashCode.
 * </p>
 */
@Data
@Builder

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lignecommandefournisseur")
public class LigneCommandeFournisseur extends AbstractEntity {

    /**
     * Le produit associé à cette ligne de commande.
     * Relation ManyToOne avec l'entité {@link Produits}.
     */
    @ManyToOne
    @JoinColumn(name = "idproduit")
    private Produits produit;

    /**
     * La commande fournisseur à laquelle cette ligne appartient.
     * Relation ManyToOne avec l'entité {@link CommandeFournisseur}.
     */
    @ManyToOne
    @JoinColumn(name = "idcommandefournisseur")
    private CommandeFournisseur commandeFournisseur;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "prixUnitaire")
    private BigDecimal prixUnitaire;
}