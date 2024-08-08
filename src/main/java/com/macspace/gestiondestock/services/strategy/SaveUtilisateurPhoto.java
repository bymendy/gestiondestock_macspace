package com.macspace.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

import com.macspace.gestiondestock.dto.UtilisateurDto;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.services.FlickrService;
import com.macspace.gestiondestock.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Stratégie de sauvegarde des photos pour les utilisateurs.
 * <p>
 * Cette classe implémente l'interface {@link Strategy} pour gérer la sauvegarde des photos
 * spécifiquement pour les utilisateurs. Elle utilise {@link FlickrService} pour enregistrer
 * la photo sur Flickr et {@link UtilisateurService} pour manipuler les informations sur les utilisateurs.
 * </p>
 */
@Service("utilisateurStrategy")
@Slf4j
public class SaveUtilisateurPhoto implements Strategy<UtilisateurDto> {

    private FlickrService flickrService;
    private UtilisateurService utilisateurService;

    /**
     * Constructeur pour injecter les services nécessaires.
     *
     * @param flickrService le service pour la gestion des photos sur Flickr
     * @param utilisateurService le service pour la gestion des utilisateurs
     */
    @Autowired
    public SaveUtilisateurPhoto(FlickrService flickrService, UtilisateurService utilisateurService) {
        this.flickrService = flickrService;
        this.utilisateurService = utilisateurService;
    }

    /**
     * Sauvegarde une photo pour un utilisateur spécifique.
     * <p>
     * La méthode récupère l'utilisateur avec l'identifiant fourni, enregistre la photo
     * sur Flickr et met à jour l'utilisateur avec l'URL de la photo. Si l'enregistrement de la photo échoue,
     * une {@link InvalidOperationException} est lancée.
     * </p>
     *
     * @param id l'identifiant de l'utilisateur pour lequel la photo est sauvegardée
     * @param photo le flux d'entrée contenant les données de la photo à enregistrer
     * @param titre le titre donné à la photo
     * @return l'utilisateur mis à jour avec l'URL de la photo
     * @throws FlickrException si une erreur se produit lors de l'enregistrement de la photo sur Flickr
     */
    @Override
    public UtilisateurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        // Récupère l'utilisateur avec l'identifiant fourni
        UtilisateurDto utilisateur = utilisateurService.findById(id);

        // Enregistre la photo sur Flickr et obtient l'URL
        String urlPhoto = flickrService.savePhoto(photo, titre);

        // Vérifie si l'URL est valide, sinon lance une exception
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo de l'utilisateur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // Met à jour l'utilisateur avec l'URL de la photo
        utilisateur.setPhoto(urlPhoto);

        // Sauvegarde les modifications de l'utilisateur
        return utilisateurService.save(utilisateur);
    }
}
