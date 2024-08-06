package com.macspace.gestiondestock.config;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * Classe de configuration pour Flickr.
 * Elle permet de configurer et de récupérer un client Flickr avec les informations d'authentification nécessaires.
 * Flickr est une API qui permet de stocker des photos sur le cloud pour éviter d'alourdir notre base de données.
 * Une offre non commerciale a été choisie car les photos peuvent être publiques.
 */
@Configuration
public class FlickrConfiguration {

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

    /**
     * Configure et retourne un objet {@link Flickr} authentifié.
     * Cette méthode initialise la connexion à Flickr avec les clés d'application et les permissions nécessaires.
     *
     * @return Un objet {@link Flickr} authentifié.
     */
    @Bean
    public Flickr getFlickr2() {
        Flickr flickr = new Flickr(apiKey, apiSecret, new REST());
        Auth auth = new Auth();
        auth.setPermission(Permission.READ);
        auth.setToken(appKey);
        auth.setTokenSecret(appSecret);
        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuth(auth);
        flickr.setAuth(auth);
        return flickr;
    }
}
