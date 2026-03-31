package com.macspace.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

/**
 * Entité représentant une commande passée à un fournisseur dans MacSpace.
 *
 * Une commande fournisseur est liée à un fournisseur
 * et contient plusieurs lignes de commande.
 * Mappée à la table 'commandefournisseur' en base de données.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "commandefournisseur")
public class CommandeFournisseur extends AbstractEntity {

    /**
     * Code unique identifiant la commande fournisseur.
     */
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    /**
     * Date à laquelle la commande a été passée.
     */
    @Column(name = "date_commande", nullable = false)
    private Instant dateCommande;

    /**
     * État actuel de la commande.
     * Valeurs possibles : EN_COURS, VALIDEE, LIVREE, ANNULEE
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "etat_commande", nullable = false)
    private EtatCommande etatCommande;

    /**
     * Fournisseur associé à cette commande.
     * EAGER pour éviter le LazyInitializationException
     * lors de la conversion en DTO hors session Hibernate.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_fournisseur", referencedColumnName = "id")
    private Fournisseur fournisseur;

    /**
     * Liste des lignes de commande associées à cette commande.
     * EAGER pour retourner les lignes dans la réponse.
     */
    @OneToMany(mappedBy = "commandeFournisseur", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;

}