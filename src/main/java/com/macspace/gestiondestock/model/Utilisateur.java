package com.macspace.gestiondestock.model;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper= true)

@Entity
@Table(name = "utilisateur")
public class Utilisateur extends AbstractEntity{

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prennom;

    @Column(name = "datedenaissance")
    private String datedenaissance;

    @Column(name = "fonction")
    private String fonction;

    @Column(name = "password")
    private String password;

    @Embedded
    private Adresse adresse;

    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "identreprise")
    private Entreprise entreprise;

    private List<Roles> roles;

}
