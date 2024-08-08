package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

/**
 * La classe LigneInterventionClient représente une ligne d'intervention pour un client.
 * Elle est liée à une intervention et à une intervention client.
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
 *   <li>@Table(name = "ligneinterventionclient") : Spécifie le nom de la table correspondante dans la base de données.</li>
 * </ul>
 *
 * <p>Les relations Many-to-One sont utilisées pour associer cette entité aux entités Interventions et InterventionClient.</p>
 *
 * <p>Exemple d'utilisation :</p>
 * <pre>{@code
 * LigneInterventionClient ligneInterventionClient = new LigneInterventionClient();
 * ligneInterventionClient.setInterventions(interventions);
 * ligneInterventionClient.setInterventionClient(interventionClient);
 * }</pre>
 *
 * @see AbstractEntity
 * @see Interventions
 * @see InterventionClient
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ligneinterventionclient")
public class LigneInterventionClient extends AbstractEntity {

    //public LigneInterventionClient(){
    //   this.creationdate = new Date();
    //}
    /**
     * L'intervention associée à cette ligne d'intervention client.
     *
     * <p>La relation Many-to-One signifie que plusieurs lignes d'intervention client peuvent être liées à une seule intervention.</p>
     */
    @ManyToOne
    private Interventions interventions;

    private String numeroContrat;

    /**
     * L'intervention client associée à cette ligne d'intervention client.
     *
     * <p>La relation Many-to-One signifie que plusieurs lignes d'intervention client peuvent être liées à une seule intervention client.</p>
     */
    @ManyToOne
    private InterventionClient interventionClient;

    private BigDecimal quantite;


    @ManyToOne
    private Produits produit;
    // Attribut technique à ajouter pour chaque entite sauf pour Entreprise et Utilisateur
    // si on parle de conception UMl ce n'est pas 100% correct de le mettre
    // si on parle de implementation technique, cette id va simplifier beaucoup les tâches
    private Integer idEntreprise;

    /**
     * La date d'ouverture du ticket d'intervention.
     */
    @Column(name = "creation_date") // Renommage explicite de la colonne
    private Instant creationDate;

    /**
     * La description de la problématique rencontrée.
     */
    private String problematique;
}