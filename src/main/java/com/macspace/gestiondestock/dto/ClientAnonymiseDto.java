package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.utils.AnonymisationUtils;
import lombok.Builder;
import lombok.Data;

/**
 * DTO anonymisé pour l'export des données clients MacSpace.
 * Applique les techniques de masquage et pseudonymisation
 * conformément au RGPD et au cours Analyse et transformation
 * des données.
 */
@Data
@Builder
public class ClientAnonymiseDto {

    /** Identifiant pseudonymisé — ex: Client_001 */
    private String identifiant;

    /** Email masqué — ex: je**@gmail.com */
    private String emailMasque;

    /** Téléphone masqué — ex: 06****78 */
    private String telephoneMasque;

    /** Ville du client — donnée non sensible conservée */
    private String ville;

    /** Pays du client — donnée non sensible conservée */
    private String pays;

    /** Identifiant entreprise — conservé pour le multi-tenant */
    private Integer idEntreprise;

    /**
     * Construit un DTO anonymisé depuis un ClientDto.
     */
    public static ClientAnonymiseDto fromClient(ClientDto dto) {
        return ClientAnonymiseDto.builder()
                .identifiant(AnonymisationUtils.pseudonymiser(dto.getId()))
                .emailMasque(AnonymisationUtils.masquerEmail(dto.getEmail()))
                .telephoneMasque(AnonymisationUtils.masquerTelephone(dto.getNumTel()))
                .ville(dto.getAdresse() != null ? dto.getAdresse().getVille() : null)
                .pays(dto.getAdresse() != null ? dto.getAdresse().getPays() : null)
                .idEntreprise(dto.getIdEntreprise())
                .build();
    }
}