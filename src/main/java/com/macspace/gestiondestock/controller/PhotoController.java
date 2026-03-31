package com.macspace.gestiondestock.controller;

import com.flickr4java.flickr.FlickrException;
import com.macspace.gestiondestock.controller.api.PhotoApi;
import com.macspace.gestiondestock.services.strategy.StrategyPhotoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Contrôleur REST pour la gestion des photos dans MacSpace.
 * Implémente les endpoints définis dans {@link PhotoApi}.
 * Utilise le pattern Strategy via {@link StrategyPhotoContext}.
 */
@RestController
public class PhotoController implements PhotoApi {

    private final StrategyPhotoContext strategyPhotoContext;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param strategyPhotoContext Le contexte de stratégie pour la gestion des photos.
     */
    @Autowired
    public PhotoController(StrategyPhotoContext strategyPhotoContext) {
        this.strategyPhotoContext = strategyPhotoContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object savePhoto(String context, Integer id,
                            MultipartFile photo, String title)
            throws IOException, FlickrException {
        return strategyPhotoContext.savePhoto(
                context, id, photo.getInputStream(), title);
    }
}