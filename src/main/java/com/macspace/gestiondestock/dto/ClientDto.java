package com.macspace.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.model.Adresse;
import com.macspace.gestiondestock.model.Client;
import com.macspace.gestiondestock.model.InterventionClient;
import lombok.Builder;
import lombok.Data;

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
    private String email;

    /**
     * Le numéro de téléphone du client.
     */
    private String numTel;

    /**
     * La liste des interventions liées au client.
     */
    @JsonIgnore
    private List<InterventionClientDto> interventionClients;


    /**
     * Convertit une entité Client en un DTO ClientDto.
     *
     * @param client L'entité Client.
     * @return Le DTO ClientDto correspondant.
     */
    public static ClientDto fromEntity(Client client) {
        if (client == null) {
            return null;
        }

        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .email(client.getEmail())
                .numTel(client.getNumTel())
                // Ajouter d'autres champs nécessaires ici
                .build();
    }

    /**
     * Convertit un DTO ClientDto en une entité Client.
     *
     * @param clientDto Le DTO ClientDto.
     * @return L'entité Client correspondante.
     */
    public static Client toEntity(ClientDto clientDto) {
        if (clientDto == null) {
            return null;
        }

        Client client = new Client();
        client.setId(clientDto.getId());
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setEmail(clientDto.getEmail());
        client.setNumTel(clientDto.getNumTel());
        // Ajouter d'autres champs nécessaires ici

        return client;
    }
}
