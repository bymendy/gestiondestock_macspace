package com.macspace.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macspace.gestiondestock.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO pour l'entité {@link Client} dans MacSpace.
 * Assure le transfert des données client entre
 * l'API et les clients externes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    /**
     * Identifiant unique du client.
     */
    private Integer id;

    /**
     * Nom du client.
     */
    private String nom;

    /**
     * Prénom du client.
     */
    private String prenom;

    /**
     * Adresse postale du client.
     */
    private AdresseDto adresse;

    /**
     * Photo du client (URL Flickr).
     */
    private String photo;

    /**
     * Adresse e-mail du client.
     */
    private String email;

    /**
     * Numéro de téléphone du client.
     */
    private String numTel;

    /**
     * Identifiant de l'entreprise — multi-tenant.
     */
    private Integer idEntreprise;

    /**
     * Liste des interventions du client.
     * Ignorée en JSON pour éviter les références circulaires.
     */
    @JsonIgnore
    private List<InterventionClientDto> interventionClients;

    /**
     * Convertit une entité {@link Client} en DTO.
     *
     * @param client L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static ClientDto fromEntity(Client client) {
        if (client == null) {
            return null;
        }
        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .adresse(AdresseDto.fromEntity(client.getAdresse()))
                .photo(client.getPhoto())
                .email(client.getEmail())
                .numTel(client.getNumTel())
                .idEntreprise(client.getIdEntreprise())
                .build();
    }

    /**
     * Convertit un DTO en entité {@link Client}.
     *
     * @param clientDto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static Client toEntity(ClientDto clientDto) {
        if (clientDto == null) {
            return null;
        }
        Client client = Client.builder()
                .nom(clientDto.getNom())
                .prenom(clientDto.getPrenom())
                .adresse(AdresseDto.toEntity(clientDto.getAdresse()))
                .photo(clientDto.getPhoto())
                .email(clientDto.getEmail())
                .numTel(clientDto.getNumTel())
                .build();
        client.setIdEntreprise(clientDto.getIdEntreprise());
        return client;
    }
}