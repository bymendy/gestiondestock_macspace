package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Classe représentant une intervention dans le système de gestion de stock.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>code : Le code unique de l'intervention.</li>
 *   <li>dateIntervention : La date de l'intervention.</li>
 *   <li>problematique : La description de la problématique rencontrée.</li>
 * </ul>
 * </p>
 * <p>
 * La classe est annotée avec {@link Entity} et {@link Table} pour indiquer qu'il s'agit
 * d'une entité JPA mappée à la table 'interventions' de la base de données. Les annotations Lombok
 * {@link Data}, {@link Builder}, {@link NoArgsConstructor}, {@link AllArgsConstructor}, et {@link EqualsAndHashCode}
 * sont utilisées pour générer automatiquement les méthodes getter, setter, toString, equals et hashCode.
 * </p>
 */
@Data

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "interventions")
public class Interventions extends AbstractEntity {
    // public Interventions(){
    //   this.dateIntervention = new Date();
    //}
    /**
     * Le code unique de l'intervention.
     */
    private String code;

    /**
     * La date de l'intervention.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateIntervention;

    // Attribut technique à ajouter pour chaque entite sauf pour Entreprise et Utilisateur
    // si on parle de conception UMl ce n'est pas 100% correct de le mettre
    // si on parle de implementation technique, cette id va simplifier beaucoup les tâches
    private Integer idEntreprise;

    /**
     * La description de la problématique rencontrée.
     */
    private String problematique;

    @OneToMany
    private List<LigneIntervention> ligneInterventions;
}