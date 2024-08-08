package com.macspace.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

import com.macspace.gestiondestock.dto.EntrepriseDto;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import com.macspace.gestiondestock.services.EntrepriseService;
import com.macspace.gestiondestock.services.FlickrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Implémentation de la stratégie pour la sauvegarde des photos des entreprises.
 * <p>
 * Cette classe est responsable de l'enregistrement des photos associées aux entreprises
 * en utilisant les services Flickr pour l'enregistrement des photos et EntrepriseService
 * pour la gestion des données des entreprises.
 * </p>
 */
@Service("entrepriseStrategy")
@Slf4j
public class SaveEntreprisePhoto implements Strategy<EntrepriseDto> {

    private FlickrService flickrService;
    private EntrepriseService entrepriseService;

    /**
     * Constructeur pour injecter les services nécessaires.
     *
     * @param flickrService le service utilisé pour l'enregistrement des photos sur Flickr
     * @param entrepriseService le service utilisé pour gérer les informations des entreprises
     */
    @Autowired
    public SaveEntreprisePhoto(FlickrService flickrService, EntrepriseService entrepriseService) {
        this.flickrService = flickrService;
        this.entrepriseService = entrepriseService;
    }

    /**
     * Sauvegarde une photo pour une entreprise spécifique.
     * <p>
     * Cette méthode récupère l'entreprise avec l'identifiant fourni, enregistre la photo
     * sur Flickr, et met à jour l'entreprise avec l'URL de la photo obtenue. Si l'enregistrement
     * de la photo échoue (c'est-à-dire que l'URL est invalide ou vide), une {@link InvalidOperationException}
     * est lancée.
     * </p>
     *
     * @param id l'identifiant de l'entreprise pour laquelle la photo doit être sauvegardée
     * @param photo le flux d'entrée contenant les données de la photo à enregistrer
     * @param titre le titre de la photo
     * @return l'entreprise mise à jour avec l'URL de la photo
     * @throws FlickrException si une erreur se produit lors de l'enregistrement de la photo sur Flickr
     * @throws InvalidOperationException si l'URL de la photo est invalide ou vide
     */
    @Override
    public EntrepriseDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        // Récupère l'entreprise avec l'identifiant fourni
        EntrepriseDto entreprise = entrepriseService.findById(id);

        // Enregistre la photo sur Flickr et obtient l'URL
        String urlPhoto = flickrService.savePhoto(photo, titre);

        // Vérifie si l'URL est valide, sinon lance une exception
        if (!StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur lors de l'enregistrement de photo de l'entreprise", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        // Met à jour l'entreprise avec l'URL de la photo
        entreprise.setPhoto(urlPhoto);
        return entrepriseService.save(entreprise);
    }
}
