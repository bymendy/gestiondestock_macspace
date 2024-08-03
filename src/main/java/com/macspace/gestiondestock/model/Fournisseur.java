package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;


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

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "fournisseur")
public class Fournisseur extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Le nom du fournisseur.
     */
    private String nom;

    /**
     * Le prénom du fournisseur.
     */
    private String prenom;

    /**
     * L'adresse du fournisseur.
     * Champ composé utilisant l'entité {@link Adresse}.
     */
    @OneToOne
    private Adresse adresse;

    /**
     * La photo du fournisseur.
     */
    private String photo;

    /**
     * L'adresse email du fournisseur.
     */
    private String mail;

    // Attribut technique à ajouter pour chaque entite sauf pour Entreprise et Utilisateur
    // si on parle de conception UMl ce n'est pas 100% correct de le mettre
    // si on parle de implementation technique, cette id va simplifier beaucoup les tâches
    private Integer idEntreprise;

    /**
     * Le numéro de téléphone du fournisseur.
     */
    private String numTel;

    /**
     * La liste des commandes passées par ce fournisseur.
     * Relation OneToMany avec l'entité {@link CommandeFournisseur}.
     */
    @OneToMany
    private List<CommandeFournisseur> commandeFournisseurs;
}