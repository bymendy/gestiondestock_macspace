package com.macspace.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import java.io.InputStream;

import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service de gestion du contexte de stratégie pour la sauvegarde des photos.
 * <p>
 * Cette classe utilise le design pattern Strategy pour déterminer la stratégie appropriée
 * pour la sauvegarde des photos en fonction du contexte fourni. Le contexte détermine
 * quel bean de stratégie est utilisé pour effectuer l'enregistrement de la photo.
 * </p>
 */
@Service
public class StrategyPhotoContext {

    private BeanFactory beanFactory;
    private Strategy strategy;

    @Setter
    private String context;

    /**
     * Constructeur pour injecter le {@link BeanFactory}.
     *
     * @param beanFactory le {@link BeanFactory} utilisé pour obtenir les beans de stratégie
     */
    @Autowired
    public StrategyPhotoContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Sauvegarde une photo en utilisant la stratégie déterminée par le contexte.
     * <p>
     * La méthode détermine la stratégie appropriée en fonction du contexte fourni,
     * puis utilise cette stratégie pour sauvegarder la photo avec l'identifiant et le titre spécifiés.
     * </p>
     *
     * @param context le contexte pour déterminer la stratégie de sauvegarde (par exemple, "article", "client", etc.)
     * @param id l'identifiant associé à la photo
     * @param photo le flux d'entrée contenant les données de la photo à enregistrer
     * @param title le titre donné à la photo
     * @return l'objet résultant de l'enregistrement de la photo
     * @throws FlickrException si une erreur se produit lors de l'interaction avec Flickr
     */
    public Object savePhoto(String context, Integer id, InputStream photo, String title) throws FlickrException {
        determinContext(context);
        return strategy.savePhoto(id, photo, title);
    }

    /**
     * Détermine la stratégie appropriée en fonction du contexte fourni.
     * <p>
     * La méthode utilise le contexte pour choisir le bean de stratégie adéquat à partir du {@link BeanFactory}.
     * Si le contexte ne correspond à aucun des cas prévus, une {@link InvalidOperationException} est lancée.
     * </p>
     *
     * @param context le contexte pour déterminer la stratégie de sauvegarde
     * @throws InvalidOperationException si le contexte est inconnu
     */
    private void determinContext(String context) {
        final String beanName = context + "Strategy";
        switch (context) {
            case "produit":
                strategy = beanFactory.getBean(beanName, SaveProduitPhoto.class);
                break;
            case "client":
                strategy = beanFactory.getBean(beanName, SaveClientPhoto.class);
                break;
            case "fournisseur":
                strategy = beanFactory.getBean(beanName, SaveFournisseurPhoto.class);
                break;
            case "entreprise":
                strategy = beanFactory.getBean(beanName, SaveEntreprisePhoto.class);
                break;
            case "utilisateur":
                strategy = beanFactory.getBean(beanName, SaveUtilisateurPhoto.class);
                break;
            default:
                throw new InvalidOperationException("Contexte inconnu pour l'enregistrement de la photo", ErrorCodes.UNKNOWN_CONTEXT);
        }
    }
}
