package com.macspace.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

import com.macspace.gestiondestock.dto.ProduitDto;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.services.FlickrService;
import com.macspace.gestiondestock.services.ProduitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Stratégie de sauvegarde des photos pour les produits.
 * <p>
 * Cette classe implémente l'interface {@link Strategy} pour gérer la sauvegarde des photos
 * spécifiquement pour les produits. Elle utilise {@link FlickrService} pour enregistrer
 * la photo sur Flickr et {@link ProduitService} pour manipuler les informations sur les produits.
 * </p>
 */
@Service("produitStrategy")
@Slf4j
public class SaveProduitPhoto implements Strategy<ProduitDto> {

    private FlickrService flickrService;
    private ProduitService produitService;

    /**
     * Constructeur pour injecter les services nécessaires.
     *
     * @param flickrService le service pour la gestion des photos sur Flickr
     * @param produitService le service pour la gestion des produits
     */
    @Autowired
    public SaveProduitPhoto(FlickrService flickrService, ProduitService produitService) {
        this.flickrService = flickrService;
        this.produitService = produitService;
    }

    /**
     * Sauvegarde une photo pour un produit spécifique.
     * <p>
     * La méthode récupère le produit avec l'identifiant fourni, enregistre la photo
     * sur Flickr et met à jour le produit avec l'URL de la photo. Si l'enregistrement de la photo échoue,
     * une {@link InvalidOperationException} est lancée.
     * </p>
     *
     * @param id l'identifiant du produit pour lequel la photo est sauvegardée
     * @param photo le flux d'entrée contenant les données de la photo à enregistrer
     * @param titre le titre donné à la photo
     * @return le produit mis à jour avec l'URL de la photo
     * @throws FlickrException si une erreur se produit lors de l'enregistrement de la photo sur Flickr
     */
    @Override
    public ProduitDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        // Récupère le produit avec l'identifiant fourni
        ProduitDto produit = produitService.findById(id);

        // Enregistre la photo sur Flickr et obtient l'URL
        String urlPhoto = flickrService.savePhoto(photo, titre);

        // Vérifie si l'URL est valide, sinon lance une exception
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo du produit", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // Met à jour le produit avec l'URL de la photo
        produit.setPhoto(urlPhoto);

        // Sauvegarde les modifications du produit
        return produitService.save(produit);
    }
}
