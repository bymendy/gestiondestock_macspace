package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.macspace.gestiondestock.dto.UtilisateurDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.macspace.gestiondestock.utils.Constants.UTILISATEUR_ENDPOINT;

/**
 * Interface définissant les endpoints de gestion des utilisateurs dans MacSpace.
 */
@Tag(name = "Utilisateurs",
        description = "API de gestion des utilisateurs")
public interface UtilisateurApi {

    /**
     * Enregistre ou met à jour un utilisateur.
     *
     * @param dto Le DTO de l'utilisateur à enregistrer.
     * @return Le DTO de l'utilisateur enregistré.
     */
    @Operation(summary = "Enregistrer un utilisateur",
            description = "Permet de créer ou modifier un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Utilisateur créé ou modifié"),
            @ApiResponse(responseCode = "400",
                    description = "Données invalides")
    })
    @PostMapping(value = UTILISATEUR_ENDPOINT + "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    UtilisateurDto save(@RequestBody UtilisateurDto dto);

    /**
     * Change le mot de passe d'un utilisateur.
     *
     * @param dto Le DTO contenant les informations de changement de mot de passe.
     * @return Le DTO de l'utilisateur mis à jour.
     */
    @Operation(summary = "Changer le mot de passe",
            description = "Permet de changer le mot de passe d'un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Mot de passe changé avec succès"),
            @ApiResponse(responseCode = "400",
                    description = "Données invalides ou mots de passe non conformes")
    })
    @PostMapping(value = UTILISATEUR_ENDPOINT + "/update/password",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    UtilisateurDto changerMotDePasse(
            @RequestBody ChangerMotDePasseUtilisateurDto dto);

    /**
     * Recherche un utilisateur par son identifiant.
     *
     * @param id L'identifiant de l'utilisateur.
     * @return Le DTO de l'utilisateur trouvé.
     */
    @Operation(summary = "Rechercher un utilisateur par ID",
            description = "Permet de chercher un utilisateur par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404",
                    description = "Utilisateur non trouvé")
    })
    @GetMapping(value = UTILISATEUR_ENDPOINT + "/{idUtilisateur}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

    /**
     * Recherche un utilisateur par son email.
     *
     * @param email L'email de l'utilisateur.
     * @return Le DTO de l'utilisateur trouvé.
     */
    @Operation(summary = "Rechercher un utilisateur par email",
            description = "Permet de chercher un utilisateur par son email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404",
                    description = "Utilisateur non trouvé")
    })
    @GetMapping(value = UTILISATEUR_ENDPOINT + "/find/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    UtilisateurDto findByEmail(@PathVariable("email") String email);

    /**
     * Récupère tous les utilisateurs.
     *
     * @return La liste de tous les utilisateurs.
     */
    @Operation(summary = "Lister tous les utilisateurs",
            description = "Retourne la liste de tous les utilisateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des utilisateurs ou liste vide")
    })
    @GetMapping(value = UTILISATEUR_ENDPOINT + "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<UtilisateurDto> findAll();

    /**
     * Récupère tous les utilisateurs d'une entreprise.
     *
     * @param idEntreprise L'identifiant de l'entreprise.
     * @return La liste des utilisateurs de l'entreprise.
     */
    @Operation(summary = "Lister les utilisateurs d'une entreprise",
            description = "Retourne la liste des utilisateurs d'une entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des utilisateurs ou liste vide")
    })
    @GetMapping(value = UTILISATEUR_ENDPOINT + "/all/entreprise/{idEntreprise}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<UtilisateurDto> findAllByEntreprise(
            @PathVariable("idEntreprise") Integer idEntreprise);

    /**
     * Récupère tous les utilisateurs par fonction.
     *
     * @param fonction La fonction de l'utilisateur.
     * @return La liste des utilisateurs correspondants.
     */
    @Operation(summary = "Lister les utilisateurs par fonction",
            description = "Retourne la liste des utilisateurs selon leur fonction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Liste des utilisateurs ou liste vide")
    })
    @GetMapping(value = UTILISATEUR_ENDPOINT + "/all/fonction/{fonction}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<UtilisateurDto> findAllByFonction(
            @PathVariable("fonction") String fonction);

    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id L'identifiant de l'utilisateur à supprimer.
     */
    @Operation(summary = "Supprimer un utilisateur",
            description = "Permet de supprimer un utilisateur par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Utilisateur supprimé"),
            @ApiResponse(responseCode = "404",
                    description = "Utilisateur non trouvé")
    })
    @DeleteMapping(UTILISATEUR_ENDPOINT + "/delete/{idUtilisateur}")
    void delete(@PathVariable("idUtilisateur") Integer id);
}