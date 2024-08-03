package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;

/**
 * Classe représentant une ligne d'intervention dans le système de gestion de stock.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>interventions : La référence à l'intervention associée.</li>
 *   <li>creationdate : La date d'ouverture du ticket d'intervention.</li>
 *   <li>problematique : La description de la problématique rencontrée.</li>
 * </ul>
 * </p>
 * <p>
 * La classe est annotée avec {@link Entity} et {@link Table} pour indiquer qu'il s'agit
 * d'une entité JPA mappée à la table 'ligneintervention' de la base de données. Les annotations Lombok
 * {@link Data}, {@link Builder}, {@link NoArgsConstructor}, {@link AllArgsConstructor}, et {@link EqualsAndHashCode}
 * sont utilisées pour générer automatiquement les méthodes getter, setter, toString, equals et hashCode.
 * </p>
 */
@Data

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ligneintervention")
public class LigneIntervention extends AbstractEntity {

    private String numeroContrat;
    //public LigneIntervention(){
    //    this.creationdate = new Date();
    // }

    /**
     * La référence à l'intervention associée.
     */
    @ManyToOne
    private Interventions interventions;

    /**
     * La date d'ouverture du ticket d'intervention.
     */
    @Column(name = "creation_date") // Renommage explicite de la colonne
    private Instant creationDate;

    // Attribut technique à ajouter pour chaque entite sauf pour Entreprise et Utilisateur
    // si on parle de conception UMl ce n'est pas 100% correct de le mettre
    // si on parle de implementation technique, cette id va simplifier beaucoup les tâches
    private Integer idEntreprise;

    /**
     * La description de la problématique rencontrée.
     */
    private String problematique;
}