package com.macspace.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import com.macspace.gestiondestock.exception.ErrorCodes;
import com.macspace.gestiondestock.exception.InvalidOperationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Service orchestrateur du pattern Strategy pour la sauvegarde des photos dans MacSpace.
 * Détermine et exécute la stratégie appropriée selon le contexte fourni.
 */
@Service
public class StrategyPhotoContext {

    private final BeanFactory beanFactory;
    private Strategy<?> strategy;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param beanFactory Le BeanFactory Spring pour récupérer les stratégies.
     */
    @Autowired
    public StrategyPhotoContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Sauvegarde une photo en utilisant la stratégie déterminée par le contexte.
     *
     * @param context Le contexte déterminant la stratégie
     *                (produit, client, fournisseur, entreprise, utilisateur).
     * @param id      L'identifiant de l'entité associée.
     * @param photo   Le flux de la photo à sauvegarder.
     * @param title   Le titre de la photo.
     * @return L'objet mis à jour avec l'URL de la photo.
     * @throws FlickrException Si une erreur Flickr se produit.
     */
    @SuppressWarnings("unchecked")
    public Object savePhoto(String context, Integer id,
                            InputStream photo, String title) throws FlickrException {
        determinContext(context);
        return strategy.savePhoto(id, photo, title);
    }

    /**
     * Détermine la stratégie appropriée en fonction du contexte.
     *
     * @param context Le contexte de sauvegarde.
     * @throws InvalidOperationException Si le contexte est inconnu.
     */
    private void determinContext(String context) {
        final String beanName = context + "Strategy";
        strategy = switch (context) {
            case "produit" ->
                    beanFactory.getBean(beanName, SaveProduitPhoto.class);
            case "client" ->
                    beanFactory.getBean(beanName, SaveClientPhoto.class);
            case "fournisseur" ->
                    beanFactory.getBean(beanName, SaveFournisseurPhoto.class);
            case "entreprise" ->
                    beanFactory.getBean(beanName, SaveEntreprisePhoto.class);
            case "utilisateur" ->
                    beanFactory.getBean(beanName, SaveUtilisateurPhoto.class);
            default -> throw new InvalidOperationException(
                    "Contexte inconnu pour l'enregistrement de la photo : " + context,
                    ErrorCodes.UNKNOWN_CONTEXT
            );
        };
    }
}