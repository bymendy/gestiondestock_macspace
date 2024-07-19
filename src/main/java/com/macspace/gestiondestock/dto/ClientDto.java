package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Adresse;
import com.macspace.gestiondestock.model.InterventionClient;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Builder
public class ClientDto {
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
    private AdresseDto adresse;

    /**
     * La photo du client.
     */
    private String photo;

    /**
     * L'adresse e-mail du client.
     */
    private String mail;

    /**
     * Le numéro de téléphone du client.
     */
    private String numTel;

    /**
     * La liste des interventions liées au client.
     */
    private List<InterventionClientDto> interventionClients;
}
