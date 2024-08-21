package com.macspace.gestiondestock.model;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Classe abstraite représentant une entité de base avec des propriétés
 * communes à toutes les entités de l'application.
 * <p>
 * Cette classe inclut les propriétés suivantes :
 * <ul>
 *   <li>id : Identifiant unique de l'entité.</li>
 *   <li>creationDate : Date de création de l'entité.</li>
 *   <li>lastUpdateDate : Date de la dernière modification de l'entité.</li>
 * </ul>
 * </p>
 * <p>
 * Les annotations {@link CreatedDate} et {@link LastModifiedDate} sont utilisées
 * pour gérer automatiquement les dates de création et de dernière modification.
 * Les annotations {@link JsonIgnore} sont utilisées pour ignorer ces champs lors
 * de la sérialisation JSON.
 * </p>
 * <p>
 * La classe implémente {@link Serializable} pour permettre la sérialisation des instances.
 * Elle est également annotée avec {@link MappedSuperclass} pour indiquer qu'il s'agit
 * d'une classe de base mappée, et avec {@link EntityListeners} pour spécifier
 * l'utilisation de l'audit automatique avec {@link AuditingEntityListener}.
 * </p>
 * <p>
 * Les annotations Lombok {@link Data} sont utilisées pour générer automatiquement
 * les méthodes getter, setter, toString, equals et hashCode.
 * </p>
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identifiant unique de l'entité.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Date de création de l'entité.
     * Gérée automatiquement par Spring Data JPA.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant creationDate;

    /**
     * Attribut technique à ajouter pour chaque entité sauf pour Entreprise et Utilisateur.
     */
    private Integer idEntreprise;

    /**
     * Date de la dernière modification de l'entité.
     * Gérée automatiquement par Spring Data JPA.
     */
    @LastModifiedDate
    @Column(nullable = false)
    private Instant lastUpdateDate;

    /**
     * Méthode appelée avant que l'entité ne soit insérée dans la base de données.
     * Assure que la date de création est bien initialisée.
     */
    @PrePersist
    protected void onCreate() {
        this.creationDate = Instant.now();
        this.lastUpdateDate = Instant.now();
    }

    /**
     * Méthode appelée avant que l'entité ne soit mise à jour dans la base de données.
     * Met à jour la date de la dernière modification.
     */
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdateDate = Instant.now();
    }
}
