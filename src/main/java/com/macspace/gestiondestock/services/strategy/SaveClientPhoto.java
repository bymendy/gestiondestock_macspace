package com.macspace.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

import com.macspace.gestiondestock.dto.ClientDto;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.services.ClientService;
import com.macspace.gestiondestock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Implémentation de la stratégie pour la sauvegarde des photos des clients.
 * <p>
 * Cette classe est responsable de l'enregistrement des photos associées aux clients
 * en utilisant les services Flickr pour l'enregistrement des photos et ClientService
 * pour la gestion des données des clients.
 * </p>
 */
@Service("clientStrategy")
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDto> {

    private FlickrService flickrService;
    private ClientService clientService;

    /**
     * Constructeur pour injecter les services nécessaires.
     *
     * @param flickrService le service utilisé pour l'enregistrement des photos sur Flickr
     * @param clientService le service utilisé pour gérer les informations des clients
     */
    @Autowired
    public SaveClientPhoto(FlickrService flickrService, ClientService clientService) {
        this.flickrService = flickrService;
        this.clientService = clientService;
    }

    /**
     * Sauvegarde une photo pour un client spécifique.
     * <p>
     * Cette méthode récupère le client avec l'identifiant fourni, enregistre la photo
     * sur Flickr, et met à jour le client avec l'URL de la photo obtenue. Si l'enregistrement
     * de la photo échoue (c'est-à-dire que l'URL est invalide ou vide), une {@link InvalidOperationException}
     * est lancée.
     * </p>
     *
     * @param id l'identifiant du client pour lequel la photo doit être sauvegardée
     * @param photo le flux d'entrée contenant les données de la photo à enregistrer
     * @param titre le titre de la photo
     * @return le client mis à jour avec l'URL de la photo
     * @throws FlickrException si une erreur se produit lors de l'enregistrement de la photo sur Flickr
     * @throws InvalidOperationException si l'URL de la photo est invalide ou vide
     */
    @Override
    public ClientDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        // Récupère le client avec l'identifiant fourni
        ClientDto client = clientService.findById(id);

        // Enregistre la photo sur Flickr et obtient l'URL
        String urlPhoto = flickrService.savePhoto(photo, titre);

        // Vérifie si l'URL est valide, sinon lance une exception
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo du client", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // Met à jour le client avec l'URL de la photo
        client.setPhoto(urlPhoto);
        return clientService.save(client);
    }
}
