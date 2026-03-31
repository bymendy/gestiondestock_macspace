package com.macspace.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

/**
 * Entité représentant une intervention réalisée pour un client dans MacSpace.
 *
 * Une intervention client est liée à un client spécifique
 * et contient plusieurs lignes d'intervention détaillant
 * les produits et services fournis.
 * Mappée à la table 'interventionclient' en base de données.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "interventionclient")
public class InterventionClient extends AbstractEntity {

    /**
     * Code unique identifiant l'intervention client.
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
     * État actuel de l'intervention.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "etat_intervention", nullable = false)
    private EtatIntervention etatIntervention;

    /**
     * Client associé à cette intervention.
     * EAGER pour éviter le LazyInitializationException
     * lors de la conversion en DTO hors session Hibernate.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    @JsonIgnore
    private Client client;

    /**
     * Liste des lignes d'intervention associées.
     * EAGER pour retourner les lignes dans la réponse.
     */
    @OneToMany(mappedBy = "interventionClient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LigneInterventionClient> ligneInterventionClients;
}