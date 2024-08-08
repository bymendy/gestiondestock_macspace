package com.macspace.gestiondestock.controller;

import com.flickr4java.flickr.FlickrException;
import java.io.IOException;

import com.macspace.gestiondestock.controller.api.PhotoApi;
import com.macspace.gestiondestock.services.strategy.StrategyPhotoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Contrôleur pour la gestion des photos.
 * <p>
 * Cette classe implémente l'interface {@link PhotoApi} pour fournir des opérations sur les photos,
 * telles que l'enregistrement des photos avec des métadonnées associées.
 * </p>
 */
@RestController
public class PhotoController implements PhotoApi {

    private final StrategyPhotoContext strategyPhotoContext;

    /**
     * Constructeur pour initialiser le contrôleur avec le contexte de stratégie de photo.
     * <p>
     * Ce constructeur permet d'injecter la dépendance {@link StrategyPhotoContext}
     * via l'injection de dépendances Spring.
     * </p>
     *
     * @param strategyPhotoContext le contexte de stratégie utilisé pour gérer les opérations liées aux photos
     */
    @Autowired
    public PhotoController(StrategyPhotoContext strategyPhotoContext) {
        this.strategyPhotoContext = strategyPhotoContext;
    }

    /**
     * Enregistre une photo avec les métadonnées fournies.
     * <p>
     * Cette méthode reçoit un fichier photo, un contexte, un identifiant, et un titre,
     * puis utilise le contexte de stratégie pour enregistrer la photo.
     * </p>
     *
     * @param context le contexte dans lequel la photo est enregistrée (par exemple, "profil", "produit")
     * @param id l'identifiant associé à la photo (peut être l'identifiant d'un utilisateur, d'un produit, etc.)
     * @param photo le fichier photo à télécharger
     * @param title le titre donné à la photo
     * @return un objet de réponse (type générique, peut être précisé en fonction de l'implémentation)
     * @throws IOException si une erreur d'entrée/sortie se produit lors du traitement du fichier photo
     * @throws FlickrException si une erreur spécifique à Flickr se produit lors de l'enregistrement
     */
    @Override
    public Object savePhoto(String context, Integer id, MultipartFile photo, String title) throws IOException, FlickrException {
        return strategyPhotoContext.savePhoto(context, id, photo.getInputStream(), title);
    }
}
