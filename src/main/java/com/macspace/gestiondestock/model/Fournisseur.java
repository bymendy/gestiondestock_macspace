package com.macspace.gestiondestock.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Classe représentant un fournisseur dans le système de gestion de stock.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>nom : Le nom du fournisseur.</li>
 *   <li>prenom : Le prénom du fournisseur.</li>
 *   <li>adresse : L'adresse du fournisseur, représentée par une entité {@link Adresse}.</li>
 *   <li>photo : Une photo du fournisseur.</li>
 *   <li>mail : L'adresse email du fournisseur.</li>
 *   <li>numTel : Le numéro de téléphone du fournisseur.</li>
 *   <li>commandeFournisseurs : La liste des commandes passées par ce fournisseur.</li>
 * </ul>
 * </p>
 * <p>
 * La classe est annotée avec {@link Entity} et {@link Table} pour indiquer qu'il s'agit
 * d'une entité JPA mappée à la table 'fournisseur' de la base de données. Les annotations Lombok
 * {@link Data}, {@link NoArgsConstructor}, {@link AllArgsConstructor}, et {@link EqualsAndHashCode}
 * sont utilisées pour générer automatiquement les méthodes getter, setter, toString, equals et hashCode.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "fournisseur")
public class Fournisseur extends AbstractEntity {

    /**
     * Le nom du fournisseur.
     */
    @Column(name = "nom")
    private String nom;

    /**
     * Le prénom du fournisseur.
     */
    @Column(name = "prenom")
    private String prenom;

    /**
     * L'adresse du fournisseur.
     * Champ composé utilisant l'entité {@link Adresse}.
     */
    @Embedded
    private Adresse adresse;

    /**
     * La photo du fournisseur.
     */
    @Column(name = "photo")
    private String photo;

    /**
     * L'adresse email du fournisseur.
     */
    @Column(name = "mail")
    private String mail;

    /**
     * Le numéro de téléphone du fournisseur.
     */
    @Column(name = "numTel")
    private String numTel;

    /**
     * La liste des commandes passées par ce fournisseur.
     * Relation OneToMany avec l'entité {@link CommandeFournisseur}.
     */
    @OneToMany(mappedBy = "fournisseur")
    private List<CommandeFournisseur> commandeFournisseurs;
}