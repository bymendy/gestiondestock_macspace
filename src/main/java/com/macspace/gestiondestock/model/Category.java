package com.macspace.gestiondestock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Classe représentant une catégorie de produits dans le système de gestion de stock.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>code : Le code unique de la catégorie.</li>
 *   <li>designation : La désignation de la catégorie.</li>
 *   <li>produit : La liste des produits appartenant à cette catégorie.</li>
 * </ul>
 * </p>
 * <p>
 * La classe est annotée avec {@link Entity} et {@link Table} pour indiquer qu'il s'agit
 * d'une entité JPA mappée à la table 'category' de la base de données. Les annotations Lombok
 * {@link Data}, {@link NoArgsConstructor}, {@link AllArgsConstructor}, et {@link EqualsAndHashCode}
 * sont utilisées pour générer automatiquement les méthodes getter, setter, toString, equals et hashCode.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
public class Category extends AbstractEntity {

    /**
     * Le code unique de la catégorie.
     */
    @Column(name = "code")
    private String code;

    /**
     * La désignation de la catégorie.
     */
    @Column(name = "designation")
    private String designation;

    /**
     * La liste des produits appartenant à cette catégorie.
     */
    @OneToMany(mappedBy = "category")
    private List<Produits> produit;
}