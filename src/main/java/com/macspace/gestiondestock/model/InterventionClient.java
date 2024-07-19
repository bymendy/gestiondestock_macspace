package com.macspace.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

/**
 * La classe InterventionClient représente une intervention réalisée pour un client.
 * Elle inclut des informations sur l'intervention telles que le code, la date,
 * le client associé, et les lignes d'intervention.
 *
 * <p>Cette classe hérite de la classe AbstractEntity qui fournit un identifiant unique.</p>
 *
 * <p>Annotations utilisées :</p>
 * <ul>
 *   <li>@Data : Génère les getters, setters, toString, equals et hashCode automatiquement.</li>
 *   <li>@NoArgsConstructor : Génère un constructeur sans arguments.</li>
 *   <li>@AllArgsConstructor : Génère un constructeur avec tous les arguments.</li>
 *   <li>@EqualsAndHashCode(callSuper= true) : Inclut les champs de la classe parente dans les méthodes equals et hashCode.</li>
 *   <li>@Entity : Spécifie que cette classe est une entité JPA.</li>
 *   <li>@Table(name = "interventionclient") : Spécifie le nom de la table correspondante dans la base de données.</li>
 * </ul>
 *
 * <p>Les relations Many-to-One et One-to-Many sont utilisées pour associer cette entité aux entités Client et LigneInterventionClient.</p>
 *
 * <p>Exemple d'utilisation :</p>
 * <pre>{@code
 * InterventionClient interventionClient = new InterventionClient();
 * interventionClient.setCode("INT123");
 * interventionClient.setDateIntervention(Instant.now());
 * interventionClient.setClient(client);
 * interventionClient.setLigneInterventionClients(ligneInterventionClients);
 * }</pre>
 *
 * @see AbstractEntity
 * @see Client
 * @see LigneInterventionClient
 */
@Data
@Builder

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "interventionclient")
public class InterventionClient extends AbstractEntity {

    /**
     * Le code unique de l'intervention.
     */
    @Column(name = "code")
    private String code;

    /**
     * La date de l'intervention.
     */
    @Column(name = "dateIntervention")
    private Instant dateIntervention;

    /**
     * Le client associé à cette intervention.
     *
     * <p>La relation Many-to-One signifie que plusieurs interventions peuvent être liées à un seul client.</p>
     */
    @ManyToOne
    @JoinColumn(name = "idclient")
    private Client client;

    /**
     * La liste des lignes d'intervention associées à cette intervention client.
     *
     * <p>La relation One-to-Many signifie qu'une intervention peut avoir plusieurs lignes d'intervention.</p>
     */
    @OneToMany(mappedBy = "interventionClient")
    private List<LigneInterventionClient> ligneInterventionClients;
}
