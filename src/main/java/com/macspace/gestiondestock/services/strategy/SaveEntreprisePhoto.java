package com.macspace.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.macspace.gestiondestock.dto.EntrepriseDto;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.services.EntrepriseService;
import com.macspace.gestiondestock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

/**
 * Stratégie de sauvegarde des photos des entreprises dans MacSpace.
 * Utilise Flickr pour stocker la photo et met à jour l'entreprise avec l'URL.
 */
@Service("entrepriseStrategy")
@Slf4j
public class SaveEntreprisePhoto implements Strategy<EntrepriseDto> {

    private final FlickrService flickrService;
    private final EntrepriseService entrepriseService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param flickrService     Le service Flickr pour la sauvegarde des photos.
     * @param entrepriseService Le service pour la gestion des entreprises.
     */
    @Autowired
    public SaveEntreprisePhoto(FlickrService flickrService,
                               EntrepriseService entrepriseService) {
        this.flickrService = flickrService;
        this.entrepriseService = entrepriseService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntrepriseDto savePhoto(Integer id, InputStream photo,
                                   String titre) throws FlickrException {
        log.info("Sauvegarde de la photo pour l'entreprise avec l'ID {}", id);

        EntrepriseDto entreprise = entrepriseService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);

        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException(
                    "Erreur lors de l'enregistrement de la photo de l'entreprise",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION
            );
        }
        entreprise.setPhoto(urlPhoto);
        return entrepriseService.save(entreprise);
    }
}