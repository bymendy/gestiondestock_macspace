package com.macspace.gestiondestock.dto;

import com.macspace.gestiondestock.model.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO pour l'entité {@link Utilisateur} dans MacSpace.
 * Assure le transfert des données utilisateur entre
 * l'API et les clients externes.
 * <p>
 * Le champ {@code idEntreprise} est automatiquement mappé
 * depuis l'objet {@code entreprise} lors de la conversion
 * en entité, pour assurer le support multi-tenant.
 * Les relations Lazy (adresse, entreprise, roles) ne sont pas
 * mappées dans {@code fromEntity()} pour éviter le
 * LazyInitializationException hors session Hibernate.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDto {

    /**
     * Identifiant unique de l'utilisateur.
     */
    private Integer id;

    /**
     * Nom de l'utilisateur.
     */
    private String nom;

    /**
     * Prénom de l'utilisateur.
     */
    private String prenom;

    /**
     * Date de naissance de l'utilisateur.
     */
    private LocalDate dateDeNaissance;

    /**
     * Fonction de l'utilisateur dans l'entreprise.
     */
    private String fonction;

    /**
     * Email de l'utilisateur — identifiant de connexion.
     */
    private String email;

    /**
     * Mot de passe hashé BCrypt.
     */
    private String password;

    /**
     * Adresse de l'utilisateur.
     */
    private AdresseDto adresse;

    /**
     * Photo de l'utilisateur (URL Flickr).
     */
    private String photo;

    /**
     * Entreprise de l'utilisateur — multi-tenant.
     */
    private EntrepriseDto entreprise;

    /**
     * Identifiant de l'entreprise — multi-tenant.
     */
    private Integer idEntreprise;

    /**
     * Rôles de l'utilisateur pour Spring Security.
     */
    private List<RoleDto> roles;

    /**
     * Convertit une entité {@link Utilisateur} en DTO.
     * Les relations Lazy (adresse, entreprise, roles) ne sont pas
     * mappées pour éviter le LazyInitializationException.
     * Le password n'est pas mappé pour des raisons de sécurité.
     *
     * @param utilisateur L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est null.
     */
    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .dateDeNaissance(utilisateur.getDateDeNaissance())
                .fonction(utilisateur.getFonction())
                .email(utilisateur.getEmail())
                // password non mappé — sécurité
                .photo(utilisateur.getPhoto())
                .idEntreprise(utilisateur.getIdEntreprise())
                // adresse, entreprise, roles non mappés
                // évite LazyInitializationException hors session
                .build();
    }

    /**
     * Convertit un DTO en entité {@link Utilisateur}.
     * Le champ {@code idEntreprise} est déduit de l'objet
     * {@code entreprise} si présent.
     *
     * @param dto Le DTO à convertir.
     * @return L'entité correspondante, ou null si le DTO est null.
     */
    public static Utilisateur toEntity(UtilisateurDto dto) {
        if (dto == null) {
            return null;
        }
        Utilisateur utilisateur = Utilisateur.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .dateDeNaissance(dto.getDateDeNaissance())
                .fonction(dto.getFonction())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .adresse(AdresseDto.toEntity(dto.getAdresse()))
                .photo(dto.getPhoto())
                .entreprise(EntrepriseDto.toEntity(dto.getEntreprise()))
                .roles(dto.getRoles() != null
                        ? dto.getRoles().stream()
                        .map(RoleDto::toEntity)
                        .toList()
                        : null)
                .build();

        // Mapping idEntreprise depuis entreprise ou idEntreprise direct
        if (dto.getEntreprise() != null
                && dto.getEntreprise().getId() != null) {
            utilisateur.setIdEntreprise(dto.getEntreprise().getId());
        } else if (dto.getIdEntreprise() != null) {
            utilisateur.setIdEntreprise(dto.getIdEntreprise());
        }

        return utilisateur;
    }
}