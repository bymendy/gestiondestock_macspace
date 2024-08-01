package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;


/**
 * Classe représentant un rôle dans le système de gestion de stock.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>roleName : Le nom du rôle.</li>
 *   <li>utilisateur : L'utilisateur associé à ce rôle.</li>
 * </ul>
 * </p>
 * <p>
 * La classe est annotée avec {@link Entity} et {@link Table} pour indiquer qu'il s'agit
 * d'une entité JPA mappée à la table 'roles' de la base de données. Les annotations Lombok
 * {@link Data}, {@link Builder}, {@link NoArgsConstructor}, {@link AllArgsConstructor}, et {@link EqualsAndHashCode}
 * sont utilisées pour générer automatiquement les méthodes getter, setter, toString, equals et hashCode.
 * </p>
 */
@Data

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class Roles extends AbstractEntity {

    /**
     * Le nom du rôle.
     */
    private String roleName;

    // Attribut technique à ajouter pour chaque entite sauf pour Entreprise et Utilisateur
    // si on parle de conception UMl ce n'est pas 100% correct de le mettre
    // si on parle de implementation technique, cette id va simplifier beaucoup les tâches
    private Integer identreprise;

    /**
     * L'utilisateur associé à ce rôle.
     */
    @ManyToOne
    private Utilisateur utilisateur;
}