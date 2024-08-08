package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

/**
 * Classe représentant un client dans le système de gestion de stock.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>nom : Le nom du client.</li>
 *   <li>prenom : Le prénom du client.</li>
 *   <li>adresse : L'adresse du client.</li>
 *   <li>photo : La photo du client.</li>
 *   <li>mail : L'adresse e-mail du client.</li>
 *   <li>numTel : Le numéro de téléphone du client.</li>
 *   <li>interventionClients : La liste des interventions liées au client.</li>
 * </ul>
 * </p>
 * <p>
 * La classe est annotée avec {@link Entity} et {@link Table} pour indiquer qu'il s'agit
 * d'une entité JPA mappée à la table 'client' de la base de données. Les annotations Lombok
 * {@link Data}, {@link NoArgsConstructor}, {@link AllArgsConstructor}, et {@link EqualsAndHashCode}
 * sont utilisées pour générer automatiquement les méthodes getter, setter, toString, equals et hashCode.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "client")
public class Client extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Le nom du client.
     */
    private String nom;

    /**
     * Le prénom du client.
     */
    private String prenom;

    /**
     * L'adresse du client.
     * Utilise l'entité {@link Adresse} en tant que champ composé.
     */

    @OneToOne
    private Adresse adresse;

    /**
     * La photo du client.
     */
    private String photo;

    /**
     * L'adresse e-mail du client.
     */
    private String email;

    // Attribut technique à ajouter pour chaque entite sauf pour Entreprise et Utilisateur
    // si on parle de conception UMl ce n'est pas 100% correct de le mettre
    // si on parle de implementation technique, cette id va simplifier beaucoup les tâches
    private Integer idEntreprise;


    /**
     * Le numéro de téléphone du client.
     */
    private String numTel;

    /**
     * La liste des interventions liées au client.
     */
    @OneToMany
    private List<InterventionClient> interventionClients;
}