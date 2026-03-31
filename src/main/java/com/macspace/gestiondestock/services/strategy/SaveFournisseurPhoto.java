package com.macspace.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.macspace.gestiondestock.dto.FournisseurDto;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.services.FlickrService;
import com.macspace.gestiondestock.services.FournisseurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

/**
 * Stratégie de sauvegarde des photos des fournisseurs dans MacSpace.
 * Utilise Flickr pour stocker la photo et met à jour le fournisseur avec l'URL.
 */
@Service("fournisseurStrategy")
@Slf4j
public class SaveFournisseurPhoto implements Strategy<FournisseurDto> {

    private final FlickrService flickrService;
    private final FournisseurService fournisseurService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param flickrService      Le service Flickr pour la sauvegarde des photos.
     * @param fournisseurService Le service pour la gestion des fournisseurs.
     */
    @Autowired
    public SaveFournisseurPhoto(FlickrService flickrService,
                                FournisseurService fournisseurService) {
        this.flickrService = flickrService;
        this.fournisseurService = fournisseurService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FournisseurDto savePhoto(Integer id, InputStream photo,
                                    String titre) throws FlickrException {
        log.info("Sauvegarde de la photo pour le fournisseur avec l'ID {}", id);

        FournisseurDto fournisseur = fournisseurService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);

        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException(
                    "Erreur lors de l'enregistrement de la photo du fournisseur",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION
            );
        }
        fournisseur.setPhoto(urlPhoto);
        return fournisseurService.save(fournisseur);
    }
}