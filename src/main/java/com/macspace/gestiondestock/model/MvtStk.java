package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Classe représentant un mouvement de stock dans le système de gestion de stock.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>dateMvt : La date du mouvement de stock.</li>
 *   <li>quantite : La quantité de produits déplacés lors du mouvement.</li>
 *   <li>produit : Le produit associé au mouvement de stock.</li>
 *   <li>typeMvt : Le type de mouvement de stock (entrée, sortie, etc.).</li>
 * </ul>
 * </p>
 * <p>
 * La classe est annotée avec {@link Entity} et {@link Table} pour indiquer qu'il s'agit
 * d'une entité JPA mappée à la table 'mvtstk' de la base de données. Les annotations Lombok
 * {@link Data}, {@link NoArgsConstructor}, {@link AllArgsConstructor}, et {@link EqualsAndHashCode}
 * sont utilisées pour générer automatiquement les méthodes getter, setter, toString, equals et hashCode.
 * </p>
 */
@Data

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mvtstk") // Mouvement de stock
public class MvtStk extends AbstractEntity {

    /**
     * La date du mouvement de stock.
     */
    private Instant dateMvt;

    /**
     * La quantité de produits déplacés lors du mouvement.
     */
    private BigDecimal quantite;

    /**
     * Le produit associé au mouvement de stock.
     */
    @ManyToOne
    private Produits produit;

    // Attribut technique à ajouter pour chaque entite sauf pour Entreprise et Utilisateur
    // si on parle de conception UMl ce n'est pas 100% correct de le mettre
    // si on parle de implementation technique, cette id va simplifier beaucoup les tâches
    private Integer identreprise;

    /**
     * Le type de mouvement de stock (entrée, sortie, etc.).
     */
    private TypeMvtStk typeMvt;
}