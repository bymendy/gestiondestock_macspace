package com.macspace.gestiondestock.model;

import jakarta.persistence.Column;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Classe représentant un produit dans le système de gestion de stock.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>codeProduit : Le code unique du produit.</li>
 *   <li>designation : La désignation ou le nom du produit.</li>
 *   <li>prixUnitaireHt : Le prix unitaire hors taxes du produit.</li>
 *   <li>tauxTva : Le taux de TVA appliqué au produit.</li>
 *   <li>prixUnitaireTtc : Le prix unitaire toutes taxes comprises du produit.</li>
 *   <li>photo : Une photo du produit.</li>
 *   <li>category : La catégorie à laquelle appartient le produit, représentée par une entité {@link Category}.</li>
 * </ul>
 * </p>
 * <p>
 * La classe est annotée avec {@link Entity} et {@link Table} pour indiquer qu'il s'agit
 * d'une entité JPA mappée à la table 'produits' de la base de données. Les annotations Lombok
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
@Table(name = "produits")
public class Produits extends AbstractEntity {

    /**
     * Le code unique du produit.
     */
    @Column(name = "codeproduit")
    private String codeProduit;

    /**
     * La désignation ou le nom du produit.
     */
    @Column(name = "designation")
    private String designation;

    /**
     * Le prix unitaire hors taxes du produit.
     */
    @Column(name = "prixunitaireht")
    private BigDecimal prixUnitaireHt;

    /**
     * Le taux de TVA appliqué au produit.
     */
    @Column(name = "tauxtva")
    private BigDecimal tauxTva;

    /**
     * Le prix unitaire toutes taxes comprises du produit.
     */
    @Column(name = "prixunitairettc")
    private BigDecimal prixUnitaireTtc;

    /**
     * Une photo du produit.
     */
    @Column(name = "photo")
    private String photo;

    /**
     * La catégorie à laquelle appartient le produit.
     * Relation ManyToOne avec l'entité {@link Category}.
     */
    @ManyToOne
    @JoinColumn(name = "idcategory")
    private Category category;
}