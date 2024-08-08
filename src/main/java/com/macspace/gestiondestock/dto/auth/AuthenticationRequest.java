package com.macspace.gestiondestock.dto.auth;

import lombok.Builder;
import lombok.Data;

/**
 * Représente une demande d'authentification contenant les informations nécessaires pour
 * identifier un utilisateur et vérifier ses informations d'identification.
 * <p>
 * Cette classe est utilisée pour encapsuler les données de connexion (login et mot de passe)
 * qui seront envoyées lors d'une tentative de connexion.
 * </p>
 */
@Data
@Builder
public class AuthenticationRequest {

    /**
     * Le nom d'utilisateur ou l'identifiant de l'utilisateur qui souhaite se connecter.
     * <p>
     * Ce champ est utilisé pour identifier de manière unique l'utilisateur dans le système.
     * </p>
     */
    private String login;

    /**
     * Le mot de passe de l'utilisateur pour la connexion.
     * <p>
     * Ce champ est utilisé pour vérifier l'identité de l'utilisateur et autoriser l'accès
     * aux ressources protégées.
     * </p>
     */
    private String password;

}
