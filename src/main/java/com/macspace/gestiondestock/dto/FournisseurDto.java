package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Adresse;
import com.macspace.gestiondestock.model.CommandeFournisseur;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import java.util.List;

public class FournisseurDto {
    private Integer id;

    /**
     * Le nom du fournisseur.
     */
    private String nom;

    /**
     * Le prénom du fournisseur.
     */
    private String prenom;

    private AdresseDto adresse;

    /**
     * La photo du fournisseur.
     */
    private String photo;

    /**
     * L'adresse email du fournisseur.
     */
    private String mail;

    /**
     * Le numéro de téléphone du fournisseur.
     */
    private String numTel;


    private List<CommandeFournisseurDto> commandeFournisseurs;
}
