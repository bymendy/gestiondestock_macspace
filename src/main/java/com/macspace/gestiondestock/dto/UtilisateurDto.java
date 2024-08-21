package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Adresse;
import com.macspace.gestiondestock.model.Entreprise;
import com.macspace.gestiondestock.model.Roles;
import com.macspace.gestiondestock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) pour les utilisateurs.
 * <p>
 * Cette classe est utilisée pour transférer les données des utilisateurs entre les couches de l'application.
 * Elle contient des informations telles que le nom, le prénom, l'email, le mot de passe, l'adresse, la photo,
 * l'entreprise associée et les rôles de l'utilisateur.
 * </p>
 */
@Data
@Builder
public class UtilisateurDto {
    private Integer id;

    private String nom;

    private String prenom;

    private String datedenaissance;

    private String fonction;

    private String email;

    private String password;

    private AdresseDto adresse;

    private String photo;

    private EntrepriseDto entreprise;

    private List<RolesDto> roles;

    /**
     * Convertit une entité {@link Utilisateur} en un DTO {@link UtilisateurDto}.
     *
     * @param utilisateur L'entité utilisateur à convertir.
     * @return Un DTO représentant l'utilisateur, ou {@code null} si l'entité est {@code null}.
     */
    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }

        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .datedenaissance(utilisateur.getDatedenaissance())
                .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
                .photo(utilisateur.getPhoto())
                .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
                .roles(
                        utilisateur.getRoles() != null ?
                                utilisateur.getRoles().stream()
                                        .map(RolesDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }

    /**
     * Convertit un DTO {@link UtilisateurDto} en une entité {@link Utilisateur}.
     *
     * @param dto Le DTO à convertir.
     * @return Une entité utilisateur correspondant au DTO, ou {@code null} si le DTO est {@code null}.
     */
    public static Utilisateur toEntity(UtilisateurDto dto) {
        if (dto == null) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(dto.getId());
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setPassword(dto.getPassword());
        utilisateur.setDatedenaissance(dto.getDatedenaissance());
        utilisateur.setAdresse(AdresseDto.toEntity(dto.getAdresse()));
        utilisateur.setPhoto(dto.getPhoto());
        utilisateur.setEntreprise(EntrepriseDto.toEntity(dto.getEntreprise()));

        return utilisateur;
    }
}
