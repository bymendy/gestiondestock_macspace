package com.macspace.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.macspace.gestiondestock.dto.ProduitDto;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.services.FlickrService;
import com.macspace.gestiondestock.services.ProduitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

/**
 * Stratégie de sauvegarde des photos des produits dans MacSpace.
 * Utilise Flickr pour stocker la photo et met à jour le produit avec l'URL.
 */
@Service("produitStrategy")
@Slf4j
public class SaveProduitPhoto implements Strategy<ProduitDto> {

    private final FlickrService flickrService;
    private final ProduitService produitService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param flickrService  Le service Flickr pour la sauvegarde des photos.
     * @param produitService Le service pour la gestion des produits.
     */
    @Autowired
    public SaveProduitPhoto(FlickrService flickrService,
                            ProduitService produitService) {
        this.flickrService = flickrService;
        this.produitService = produitService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProduitDto savePhoto(Integer id, InputStream photo,
                                String titre) throws FlickrException {
        log.info("Sauvegarde de la photo pour le produit avec l'ID {}", id);

        ProduitDto produit = produitService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);

        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException(
                    "Erreur lors de l'enregistrement de la photo du produit",
                    ErrorCodes.UPDATE_PHOTO_EXCEPTION
            );
        }
        produit.setPhoto(urlPhoto);
        return produitService.save(produit);
    }
}