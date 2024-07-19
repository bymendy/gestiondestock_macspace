package com.macspace.gestiondestock.model;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "interventions")
public class Interventions extends AbstractEntity {

    /**
     * Le code unique de l'intervention.
     */
    @Column(name = "code")
    private String code;

    /**
     * La date de l'intervention.
     */
    @Column(name = "dateintervention")
    private Instant dateIntervention;

    /**
     * La description de la problématique rencontrée.
     */
    @Column(name = "problematique")
    private String problematique;
}