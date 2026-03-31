package com.macspace.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

/**
 * Entité représentant une intervention technique de Mac Sécurité dans MacSpace.
 *
 * Une intervention est une opération terrain (installation,
 * maintenance, dépannage) liée à un système de sécurité.
 * Elle contient plusieurs lignes d'intervention détaillant
 * les produits et services utilisés.
 * Mappée à la table 'interventions' en base de données.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "interventions")
public class Intervention extends AbstractEntity {

    /**
     * Code unique identifiant l'intervention.
     */
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    /**
     * Date à laquelle l'intervention a été réalisée.
     */
    @Column(name = "date_intervention", nullable = false)
    @Builder.Default
    private Instant dateIntervention = Instant.now();

    /**
     * Description de la problématique rencontrée lors de l'intervention.
     */
    @Column(name = "problematique", columnDefinition = "TEXT")
    private String problematique;

    /**
     * État actuel de l'intervention.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "etat_intervention", nullable = false)
    private EtatIntervention etatIntervention;

    /**
     * Technicien responsable de l'intervention.
     * EAGER pour éviter le LazyInitializationException
     * lors de la conversion en DTO hors session Hibernate.
     * Pour la V1 : une intervention est assignée à un seul technicien.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTechnicien")
    private Utilisateur technicien;

    /**
     * Liste des lignes d'intervention associées.
     * EAGER pour retourner les lignes dans la réponse.
     * Pas de cascade — les lignes sont gérées manuellement
     * dans InterventionServiceImpl pour contrôler le stock.
     */
    @OneToMany(mappedBy = "intervention", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<LigneIntervention> ligneInterventions;
}