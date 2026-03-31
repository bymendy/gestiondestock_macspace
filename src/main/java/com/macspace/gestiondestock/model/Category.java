package com.macspace.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Entité représentant une catégorie de produits de sécurité dans MacSpace.
 *
 * Une catégorie regroupe plusieurs produits.
 * Mappée à la table 'category' en base de données.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
public class Category extends AbstractEntity {

    /**
     * Code unique identifiant la catégorie.
     */
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    /**
     * Désignation / libellé de la catégorie.
     */
    @Column(name = "designation", nullable = false)
    private String designation;

    /**
     * Liste des produits appartenant à cette catégorie.
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Produit> produits;
}