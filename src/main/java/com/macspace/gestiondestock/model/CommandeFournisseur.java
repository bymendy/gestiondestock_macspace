package com.macspace.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

/**
 * Classe représentant une commande fournisseur dans le système de gestion de stock.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>code : Le code de la commande.</li>
 *   <li>dateCommande : La date à laquelle la commande a été passée.</li>
 *   <li>fournisseur : Le fournisseur associé à la commande.</li>
 *   <li>ligneCommandeFournisseurs : La liste des lignes de commande associées à la commande fournisseur.</li>
 * </ul>
 * </p>
 * <p>
 * La classe est annotée avec {@link Entity} et {@link Table} pour indiquer qu'il s'agit
 * d'une entité JPA mappée à la table 'commandefournisseur' de la base de données. Les annotations Lombok
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
@Table(name = "commandefournisseur")
public class CommandeFournisseur extends AbstractEntity {

    /**
     * Le code de la commande fournisseur.
     */
    @Column(name = "code")
    private String code;

    /**
     * La date à laquelle la commande a été passée.
     */
    @Column(name = "datecommande")
    private Instant dateCommande;

    /**
     * Le fournisseur associé à la commande.
     */
    @ManyToOne
    @JoinColumn(name = "idfournisseur")
    private Fournisseur fournisseur;

    /**
     * La liste des lignes de commande associées à la commande fournisseur.
     */
    @OneToMany(mappedBy = "commandeFournisseur")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;
}