package com.macspace.gestiondestock.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.Instant;

/**
 * Classe abstraite représentant une entité de base avec des propriétés
 * communes à toutes les entités de l'application MacSpace.
 *
 * Champs communs :
 * - id              : Identifiant unique auto-généré
 * - creationDate    : Date de création (automatique)
 * - lastUpdateDate  : Date de dernière modification (automatique)
 * - idEntreprise    : Identifiant de l'entreprise liée (multi-tenant)
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identifiant unique de l'entité.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Date de création de l'entité.
     */
    @CreatedDate
    @Column(name = "creation_date", nullable = false, updatable = false)
    @JsonIgnore
    private Instant creationDate;


    /**
     * Date de la dernière modification de l'entité.
     */
    @LastModifiedDate
    @Column(name = "last_update_date", nullable = false)
    @JsonIgnore
    private Instant lastUpdateDate;

    /**
     * Identifiant de l'entreprise propriétaire de l'entité.
     * Utilisé pour la gestion multi-entreprise.
     */
    @Column(name = "id_entreprise")
    private Integer idEntreprise;


    /**
     * Initialise les dates avant la première persistance.
     */
    @PrePersist
    protected void onCreate() {
        if (this.creationDate == null) {
            this.creationDate = Instant.now();
        }
        if (this.lastUpdateDate == null) {
            this.lastUpdateDate = Instant.now();
        }
    }


    /**
     * Met à jour la date de modification avant chaque mise à jour.
     */
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdateDate = Instant.now();
    }
}