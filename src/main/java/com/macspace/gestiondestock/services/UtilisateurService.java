package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.macspace.gestiondestock.dto.UtilisateurDto;

import java.util.List;

/**
 * Interface de service pour la gestion des utilisateurs dans MacSpace.
 * Définit les opérations métier disponibles sur les utilisateurs.
 */
public interface UtilisateurService {

    /**
     * Enregistre ou met à jour un utilisateur.
     *
     * @param dto Le DTO de l'utilisateur à enregistrer.
     * @return Le DTO de l'utilisateur enregistré.
     */
    UtilisateurDto save(UtilisateurDto dto);

    /**
     * Recherche un utilisateur par son identifiant.
     *
     * @param id L'identifiant de l'utilisateur.
     * @return Le DTO de l'utilisateur trouvé.
     */
    UtilisateurDto findById(Integer id);

    /**
     * Recherche un utilisateur par son email.
     * Utilisé pour l'authentification JWT.
     *
     * @param email L'email de l'utilisateur.
     * @return Le DTO de l'utilisateur trouvé.
     */
    UtilisateurDto findByEmail(String email);

    /**
     * Récupère tous les utilisateurs.
     *
     * @return La liste de tous les utilisateurs.
     */
    List<UtilisateurDto> findAll();

    /**
     * Récupère tous les utilisateurs d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des utilisateurs de l'entreprise.
     */
    List<UtilisateurDto> findAllByEntreprise(Integer idEntreprise);

    /**
     * Récupère tous les utilisateurs par fonction.
     *
     * @param fonction La fonction de l'utilisateur.
     * @return La liste des utilisateurs correspondants.
     */
    List<UtilisateurDto> findAllByFonction(String fonction);

    /**
     * Change le mot de passe d'un utilisateur.
     *
     * @param dto Le DTO contenant les informations de changement de mot de passe.
     * @return Le DTO de l'utilisateur après mise à jour.
     */
    UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);

    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id L'identifiant de l'utilisateur à supprimer.
     */
    void delete(Integer id);
}