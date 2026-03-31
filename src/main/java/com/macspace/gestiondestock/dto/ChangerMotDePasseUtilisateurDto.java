package com.macspace.gestiondestock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour le changement de mot de passe utilisateur dans MacSpace.
 * Utilisé pour valider et traiter la demande de changement
 * de mot de passe d'un utilisateur.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangerMotDePasseUtilisateurDto {

    /**
     * Identifiant de l'utilisateur dont le mot de passe doit être changé.
     */
    private Integer id;

    /**
     * Nouveau mot de passe de l'utilisateur.
     */
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String motDePasse;

    /**
     * Confirmation du nouveau mot de passe.
     * Doit être identique à motDePasse.
     */
    @NotBlank(message = "La confirmation du mot de passe est obligatoire")
    private String confirmMotDePasse;
}