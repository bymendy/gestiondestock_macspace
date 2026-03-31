package com.macspace.gestiondestock.services;

import java.io.InputStream;

/**
 * Interface de service pour la gestion des photos via Flickr dans MacSpace.
 * Définit les opérations disponibles pour la sauvegarde et
 * la suppression des photos sur Flickr.
 */
public interface FlickrService {

    /**
     * Sauvegarde une photo sur Flickr.
     *
     * @param photo  Le flux d'entrée de la photo à sauvegarder.
     * @param title  Le titre de la photo.
     * @return L'URL de la photo sauvegardée sur Flickr.
     */
    String savePhoto(InputStream photo, String title);

    /**
     * Supprime une photo de Flickr.
     *
     * @param photoId L'identifiant de la photo à supprimer.
     */
    void deletePhoto(String photoId);
}