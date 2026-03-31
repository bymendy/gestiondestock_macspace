package com.macspace.gestiondestock.config;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Flickr pour MacSpace.
 * Initialise le client Flickr avec les clés d'authentification
 * définies dans application.properties.
 * Flickr est utilisé pour stocker les photos des entités
 * (clients, fournisseurs, produits...) dans le cloud.
 */
@Configuration
public class FlickrConfiguration {

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

    /**
     * Crée et retourne un client Flickr authentifié.
     * Utilise la permission WRITE pour permettre l'upload de photos.
     *
     * @return Le client Flickr configuré et authentifié.
     */
    @Bean
    public Flickr flickr() {
        Flickr flickr = new Flickr(apiKey, apiSecret, new REST());
        Auth auth = new Auth();
        auth.setPermission(Permission.WRITE);
        auth.setToken(appKey);
        auth.setTokenSecret(appSecret);
        RequestContext requestContext =
                RequestContext.getRequestContext();
        requestContext.setAuth(auth);
        flickr.setAuth(auth);
        return flickr;
    }
}