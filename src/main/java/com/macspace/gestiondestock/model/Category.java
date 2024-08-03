package com.macspace.gestiondestock.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

/**
 * Classe représentant une catégorie de produits dans le système de gestion de stock.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>code : Le code unique de la catégorie.</li>
 *   <li>designation : La désignation de la catégorie.</li>
 *   <li>produits : La liste des produits appartenant à cette catégorie.</li>
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
public class Category extends AbstractEntity {

    /**
     * Le code unique de la catégorie.
     */
    private String code;

    /**
     * La désignation de la catégorie.
     */
    private String designation;

    /**
     * Attribut technique pour l'identifiant de l'entreprise.
     */
    private Integer idEntreprise;

    /**
     * La liste des produits appartenant à cette catégorie.
     */
    @OneToMany
    private List<Produits> produits;
}