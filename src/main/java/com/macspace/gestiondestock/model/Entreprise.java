package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import java.util.List;

/**
 * Classe représentant une entreprise dans le système de gestion de stock.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>nom : Le nom de l'entreprise.</li>
 *   <li>description : Une description de l'entreprise.</li>
 *   <li>codeFiscal : Le code fiscal de l'entreprise.</li>
 *   <li>photo : Une photo de l'entreprise.</li>
 *   <li>email : L'adresse email de l'entreprise.</li>
 *   <li>numTel : Le numéro de téléphone de l'entreprise.</li>
 *   <li>siteWeb : Le site web de l'entreprise.</li>
 *   <li>utilisateurs : Une liste d'utilisateurs associés à l'entreprise.</li>
 * </ul>
 * </p>
 * <p>
 * La classe est annotée avec {@link Entity} et {@link Table} pour indiquer qu'il s'agit
 * d'une entité JPA mappée à la table 'entreprise' de la base de données. Les annotations Lombok
 * {@link Data}, {@link Builder}, {@link NoArgsConstructor}, et {@link AllArgsConstructor}
 * sont utilisées pour générer automatiquement les méthodes getter, setter, toString, equals et hashCode.
 * </p>
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entreprise")
public class Entreprise extends AbstractEntity {

    /**
     * Le nom de l'entreprise.
     */
    private String nom;

    /**
     * Une description de l'entreprise.
     */
    private String description;

    @Embedded
    private Adresse adresse;


    /**
     * Le code fiscal de l'entreprise.
     */
    private String codeFiscal;

    /**
     * Une photo de l'entreprise.
     */
    private String photo;

    /**
     * L'adresse email de l'entreprise.
     */
    private String email;

    /**
     * Le numéro de téléphone de l'entreprise.
     */
    private String numTel;

    /**
     * Le site web de l'entreprise.
     */
    private String siteWeb;

    /**
     * La liste des utilisateurs associés à l'entreprise.
     * Relation OneToMany avec l'entité {@link Utilisateur}.
     */
    @OneToMany
    private List<Utilisateur> utilisateurs;
}