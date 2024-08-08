package com.macspace.gestiondestock.controller.api;

import com.flickr4java.flickr.FlickrException;
import io.swagger.annotations.Api;
import java.io.IOException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import static com.macspace.gestiondestock.utils.Constants.APP_ROOT;

/**
 * API pour la gestion des photos.
 * <p>
 * Cette interface définit les opérations disponibles pour la gestion des photos, y compris
 * l'enregistrement des photos avec des métadonnées associées.
 * </p>
 */
@Api("photos")
public interface PhotoApi {

    /**
     * Enregistre une photo avec les métadonnées fournies.
     * <p>
     * Cette méthode enregistre une photo en téléchargeant le fichier photo et en
     * associant des informations telles que le contexte, l'identifiant et le titre.
     * </p>
     *
     * @param context le contexte dans lequel la photo est enregistrée (par exemple, "profil", "produit")
     * @param id l'identifiant associé à la photo (peut être l'identifiant d'un utilisateur, d'un produit, etc.)
     * @param photo le fichier photo à télécharger
     * @param title le titre donné à la photo
     * @return un objet de réponse (type générique, peut être précisé en fonction de l'implémentation)
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'enregistrement de la photo
     * @throws FlickrException si une erreur spécifique à Flickr se produit lors de l'enregistrement
     */
    @PostMapping(APP_ROOT + "/save/{id}/{title}/{context}")
    Object savePhoto(
            @PathVariable("context") String context,
            @PathVariable("id") Integer id,
            @RequestPart("file") MultipartFile photo,
            @PathVariable("title") String title
    ) throws IOException, FlickrException;
}
