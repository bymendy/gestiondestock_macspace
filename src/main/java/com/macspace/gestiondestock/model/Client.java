package com.macspace.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.security.encryption.AesEncryptor;
import jakarta.persistence.Convert;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Entité représentant un client de Mac Sécurité dans MacSpace.
 *
 * Un client possède une adresse, un contact,
 * et peut être lié à plusieurs interventions.
 * La relation avec {@link Adresse} est gérée manuellement
 * dans le service pour éviter les conflits JPA.
 * Mappée à la table 'client' en base de données.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "client")
public class Client extends AbstractEntity {

    /**
     * Nom du client.
     */
    @Column(name = "nom", nullable = false)
    private String nom;

    /**
     * Prénom du client.
     */
    @Column(name = "prenom", nullable = false)
    private String prenom;

    /**
     * Adresse postale du client.
     * EAGER pour éviter le LazyInitializationException
     * lors de la conversion en DTO hors session Hibernate.
     * Pas de cascade — l'adresse est sauvegardée
     * manuellement dans ClientServiceImpl.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_adresse", referencedColumnName = "id")
    private Adresse adresse;

    /**
     * Photo du client (URL Flickr).
     */
    @Column(name = "photo")
    private String photo;

    /**
     * Adresse e-mail du client.
     */
    @Convert(converter = AesEncryptor.class)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Numéro de téléphone du client.
     */
    @Convert(converter = AesEncryptor.class)
    @Column(name = "num_tel")
    private String numTel;

    /**
     * Liste des interventions liées à ce client.
     */
    @OneToMany(mappedBy = "client",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InterventionClient> interventionClients;
}