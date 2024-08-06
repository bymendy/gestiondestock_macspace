package com.macspace.gestiondestock.services.impl;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.macspace.gestiondestock.services.FlickrService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/**
 * Service d'implémentation pour l'interaction avec l'API Flickr.
 * Cette classe permet de sauvegarder des photos sur Flickr.
 */
@Service
@Slf4j
public class FlickrServiceImpl implements FlickrService {

    /**
     * Clé API de Flickr, récupérée à partir des propriétés de l'application.
     */
    @Value("${flickr.apiKey}")
    private String apiKey;

    /**
     * Secret API de Flickr, récupéré à partir des propriétés de l'application.
     */
    @Value("${flickr.apiSecret}")
    private String apiSecret;

    /**
     * Clé de l'application Flickr, récupérée à partir des propriétés de l'application.
     */
    @Value("${flickr.appKey}")
    private String appKey;

    /**
     * Secret de l'application Flickr, récupéré à partir des propriétés de l'application.
     */
    @Value("${flickr.appSecret}")
    private String appSecret;

    private Flickr flickr;

    /**
     * Sauvegarde une photo sur Flickr.
     *
     * @param photo InputStream représentant la photo à télécharger.
     * @param title Le titre de la photo.
     * @return L'URL de la photo téléchargée.
     * @throws InterruptedException Si le thread actuel est interrompu.
     * @throws ExecutionException Si une exception s'est produite pendant l'exécution de la tâche.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     * @throws FlickrException Si une erreur liée à l'API Flickr se produit.
     */
    @Override
    @SneakyThrows
    public String savePhoto(InputStream photo, String title) {
        connect();
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(title);

        String photoId = flickr.getUploader().upload(photo, uploadMetaData);
        return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
    }

    /**
     * Connecte le service à l'API Flickr en utilisant les clés API et les clés d'application.
     *
     * @throws InterruptedException Si le thread actuel est interrompu.
     * @throws ExecutionException Si une exception s'est produite pendant l'exécution de la tâche.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     * @throws FlickrException Si une erreur liée à l'API Flickr se produit.
     */
    private void connect() throws InterruptedException, ExecutionException, IOException, FlickrException {
        flickr = new Flickr(apiKey, apiSecret, new REST());
        Auth auth = new Auth();
        auth.setPermission(Permission.READ);
        auth.setToken(appKey);
        auth.setTokenSecret(appSecret);
        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuth(auth);
        flickr.setAuth(auth);
    }
}
