package com.macspace.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.macspace.gestiondestock.dto.ClientDto;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.services.ClientService;
import com.macspace.gestiondestock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

/**
 * Stratégie de sauvegarde des photos des clients dans MacSpace.
 * Utilise Flickr pour stocker la photo et met à jour le client avec l'URL.
 */
@Service("clientStrategy")
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDto> {

    private final FlickrService flickrService;
    private final ClientService clientService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param flickrService Le service Flickr pour la sauvegarde des photos.
     * @param clientService Le service pour la gestion des clients.
     */
    @Autowired
    public SaveClientPhoto(FlickrService flickrService,
                           ClientService clientService) {
        this.flickrService = flickrService;
        this.clientService = clientService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientDto savePhoto(Integer id, InputStream photo,
                               String titre) throws FlickrException {
        log.info("Sauvegarde de la photo pour le client avec l'ID {}", id);

        ClientDto client = clientService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);

        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException(
                    "Erreur lors de l'enregistrement de la photo du client",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION
            );
        }
        client.setPhoto(urlPhoto);
        return clientService.save(client);
    }
}