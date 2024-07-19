package com.macspace.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ligneintervention")
public class LigneIntervention extends AbstractEntity {

    /**
     * La référence à l'intervention associée.
     */
    @ManyToOne
    @JoinColumn(name = "idintervention")
    private Interventions interventions;

    /**
     * La date d'ouverture du ticket d'intervention.
     */
    @Column(name = "dateouvertureTicket")
    private Instant creationdate;

    /**
     * La description de la problématique rencontrée.
     */
    @Column(name = "problematique")
    private String problematique;
}