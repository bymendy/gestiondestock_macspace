package com.macspace.gestiondestock.services;

import com.macspace.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.macspace.gestiondestock.dto.FournisseurDto;
import com.macspace.gestiondestock.dto.UtilisateurDto;

import java.util.List;

/**
 * Interface qui définit les services de gestion des utilisateurs dans l'application.
 * <p>
 * Cette interface fournit des méthodes pour gérer les utilisateurs, y compris la création,
 * la lecture, la mise à jour, la suppression, et la recherche d'utilisateurs par email.
 * Elle inclut également une méthode pour changer le mot de passe d'un utilisateur.
 * </p>
 */
public interface UtilisateurService {

    /**
     * Enregistre un nouvel utilisateur ou met à jour un utilisateur existant.
     *
     * @param dto l'objet {@link UtilisateurDto} contenant les informations de l'utilisateur à enregistrer ou mettre à jour
     * @return l'objet {@link UtilisateurDto} après son enregistrement ou sa mise à jour
     */
    UtilisateurDto save(UtilisateurDto dto);

    /**
     * Recherche un utilisateur par son identifiant unique.
     *
     * @param id l'identifiant de l'utilisateur à rechercher
     * @return l'objet {@link UtilisateurDto} correspondant à l'utilisateur trouvé, ou null si aucun utilisateur n'est trouvé
     */
    UtilisateurDto findById(Integer id);

    /**
     * Récupère la liste de tous les utilisateurs.
     *
     * @return une liste d'objets {@link UtilisateurDto} représentant tous les utilisateurs du système
     */
    List<UtilisateurDto> findAll();

    /**
     * Supprime un utilisateur par son identifiant unique.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     */
    void delete(Integer id);

    /**
     * Recherche un utilisateur par son email.
     *
     * @param email l'email de l'utilisateur à rechercher
     * @return l'objet {@link UtilisateurDto} correspondant à l'utilisateur trouvé, ou null si aucun utilisateur n'est trouvé
     */
    UtilisateurDto findByEmail(String email);

    /**
     * Change le mot de passe d'un utilisateur.
     *
     * @param dto l'objet {@link ChangerMotDePasseUtilisateurDto} contenant les informations nécessaires pour changer le mot de passe
     * @return l'objet {@link UtilisateurDto} après la mise à jour du mot de passe
     */
    UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);
}
