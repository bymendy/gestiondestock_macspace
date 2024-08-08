package com.macspace.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

import com.macspace.gestiondestock.dto.FournisseurDto;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.services.FlickrService;
import com.macspace.gestiondestock.services.FournisseurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Implémentation de la stratégie pour la sauvegarde des photos des fournisseurs.
 * <p>
 * Cette classe est responsable de la gestion de l'enregistrement des photos
 * associées aux fournisseurs en utilisant les services Flickr pour l'enregistrement
 * des photos et FournisseurService pour la gestion des données des fournisseurs.
 * </p>
 */
@Service("fournisseurStrategy")
@Slf4j
public class SaveFournisseurPhoto implements Strategy<FournisseurDto> {

    private FlickrService flickrService;
    private FournisseurService fournisseurService;

    /**
     * Constructeur pour injecter les services nécessaires.
     *
     * @param flickrService le service utilisé pour l'enregistrement des photos sur Flickr
     * @param fournisseurService le service utilisé pour gérer les informations des fournisseurs
     */
    @Autowired
    public SaveFournisseurPhoto(FlickrService flickrService, FournisseurService fournisseurService) {
        this.flickrService = flickrService;
        this.fournisseurService = fournisseurService;
    }

    /**
     * Sauvegarde une photo pour un fournisseur spécifique.
     * <p>
     * Cette méthode récupère le fournisseur avec l'identifiant fourni, enregistre la photo
     * sur Flickr, et met à jour le fournisseur avec l'URL de la photo obtenue. Si l'enregistrement
     * de la photo échoue (c'est-à-dire que l'URL est invalide ou vide), une {@link InvalidOperationException}
     * est lancée.
     * </p>
     *
     * @param id l'identifiant du fournisseur pour lequel la photo doit être sauvegardée
     * @param photo le flux d'entrée contenant les données de la photo à enregistrer
     * @param titre le titre de la photo
     * @return le fournisseur mis à jour avec l'URL de la photo
     * @throws FlickrException si une erreur se produit lors de l'enregistrement de la photo sur Flickr
     * @throws InvalidOperationException si l'URL de la photo est invalide ou vide
     */
    @Override
    public FournisseurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        // Récupère le fournisseur avec l'identifiant fourni
        FournisseurDto fournisseur = fournisseurService.findById(id);

        // Enregistre la photo sur Flickr et obtient l'URL
        String urlPhoto = flickrService.savePhoto(photo, titre);

        // Vérifie si l'URL est valide, sinon lance une exception
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo du fournisseur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // Met à jour le fournisseur avec l'URL de la photo
        fournisseur.setPhoto(urlPhoto);
        return fournisseurService.save(fournisseur);
    }
}
