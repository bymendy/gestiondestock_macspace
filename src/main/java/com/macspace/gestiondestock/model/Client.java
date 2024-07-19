package com.macspace.gestiondestock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper= true)
@Entity
@Table(name = "client")
public class Client extends AbstractEntity{


    // Un client peut passer plusieurs interventions

    //Chaque interventions comporte une ligne

    // Une ligne vient déterminer une Intervention Client

    // Une ligne Intervention appartient à un seul client
    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Embedded // Champs composé. Possible de l'utiliser plusieurs fois
    private Adresse adresse;

    @Column(name = "photo")
    private String photo;

    @Column(name = "mail")
    private String mail;

    @Column(name = "numTel")
    private String numTel;

    @OneToMany(mappedBy = "client")
    private List<InterventionClient> interventionClients ;


}
