package com.macspace.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

/**
 * Interface générique pour les stratégies de gestion des photos.
 * <p>
 * Cette interface définit une méthode pour sauvegarder une photo en utilisant une stratégie
 * spécifique qui peut varier selon les besoins de l'application.
 * </p>
 *
 * @param <T> le type de l'objet retourné par la méthode de sauvegarde de la photo
 */
public interface Strategy<T> {

    /**
     * Enregistre une photo avec les paramètres spécifiés.
     * <p>
     * Cette méthode prend un identifiant, un flux d'entrée pour la photo, et un titre,
     * et renvoie un objet du type générique {@code T}. La méthode peut lancer une {@link FlickrException}
     * si une erreur se produit lors de l'enregistrement de la photo.
     * </p>
     *
     * @param id l'identifiant associé à la photo (par exemple, l'identifiant d'un utilisateur ou d'un produit)
     * @param photo le flux d'entrée contenant les données de la photo à enregistrer
     * @param titre le titre donné à la photo
     * @return un objet du type générique {@code T} résultant de l'enregistrement de la photo
     * @throws FlickrException si une erreur se produit lors de l'interaction avec Flickr
     */
    T savePhoto(Integer id, InputStream photo, String titre) throws FlickrException;
}
