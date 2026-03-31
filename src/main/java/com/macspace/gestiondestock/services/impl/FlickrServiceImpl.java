package com.macspace.gestiondestock.services.impl;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.macspace.gestiondestock.services.FlickrService;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/**
 * Implémentation du service pour l'interaction avec l'API Flickr dans MacSpace.
 * Permet la sauvegarde et la suppression de photos sur Flickr.
 */
@Service
@Slf4j
public class FlickrServiceImpl implements FlickrService {

    /**
     * Clé API Flickr.
     */
    @Value("${flickr.apiKey}")
    private String apiKey;

    /**
     * Secret API Flickr.
     */
    @Value("${flickr.apiSecret}")
    private String apiSecret;

    /**
     * Clé de l'application Flickr.
     */
    @Value("${flickr.appKey}")
    private String appKey;

    /**
     * Secret de l'application Flickr.
     */
    @Value("${flickr.appSecret}")
    private String appSecret;

    private Flickr flickr;

    /**
     * Initialise la connexion Flickr au démarrage de l'application.
     */
    @PostConstruct
    public void init() {
        try {
            connect();
            log.info("Connexion à Flickr établie avec succès");
        } catch (Exception e) {
            log.error("Erreur lors de la connexion à Flickr", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SneakyThrows
    public String savePhoto(InputStream photo, String title) {
        connectIfNeeded();
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(title);
        String photoId = flickr.getUploader().upload(photo, uploadMetaData);
        return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SneakyThrows
    public void deletePhoto(String photoId) {
        connectIfNeeded();
        flickr.getPhotosInterface().delete(photoId);
        log.info("Photo avec l'ID {} supprimée de Flickr", photoId);
    }

    /**
     * Établit la connexion à l'API Flickr si elle n'est pas déjà établie.
     */
    private void connectIfNeeded()
            throws InterruptedException, ExecutionException,
            IOException, FlickrException {
        if (flickr == null) {
            connect();
        }
    }

    /**
     * Établit la connexion à l'API Flickr.
     */
    private void connect()
            throws InterruptedException, ExecutionException,
            IOException, FlickrException {
        flickr = new Flickr(apiKey, apiSecret, new REST());
        Auth auth = new Auth();
        auth.setPermission(Permission.WRITE);
        auth.setToken(appKey);
        auth.setTokenSecret(appSecret);
        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuth(auth);
        flickr.setAuth(auth);
    }
}