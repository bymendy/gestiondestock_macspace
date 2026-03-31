package com.macspace.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.macspace.gestiondestock.dto.UtilisateurDto;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.services.FlickrService;
import com.macspace.gestiondestock.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

/**
 * Stratégie de sauvegarde des photos des utilisateurs dans MacSpace.
 * Utilise Flickr pour stocker la photo et met à jour l'utilisateur avec l'URL.
 */
@Service("utilisateurStrategy")
@Slf4j
public class SaveUtilisateurPhoto implements Strategy<UtilisateurDto> {

    private final FlickrService flickrService;
    private final UtilisateurService utilisateurService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param flickrService      Le service Flickr pour la sauvegarde des photos.
     * @param utilisateurService Le service pour la gestion des utilisateurs.
     */
    @Autowired
    public SaveUtilisateurPhoto(FlickrService flickrService,
                                UtilisateurService utilisateurService) {
        this.flickrService = flickrService;
        this.utilisateurService = utilisateurService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UtilisateurDto savePhoto(Integer id, InputStream photo,
                                    String titre) throws FlickrException {
        log.info("Sauvegarde de la photo pour l'utilisateur avec l'ID {}", id);

        UtilisateurDto utilisateur = utilisateurService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);

        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException(
                    "Erreur lors de l'enregistrement de la photo de l'utilisateur",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION
            );
        }
        utilisateur.setPhoto(urlPhoto);
        return utilisateurService.save(utilisateur);
    }
}