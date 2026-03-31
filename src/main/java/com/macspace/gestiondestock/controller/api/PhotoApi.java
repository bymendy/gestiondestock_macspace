package com.macspace.gestiondestock.controller.api;

import com.flickr4java.flickr.FlickrException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.macspace.gestiondestock.utils.Constants.APP_ROOT;

/**
 * Interface définissant les endpoints de gestion des photos dans MacSpace.
 * Utilise Flickr pour le stockage des photos.
 */
@Tag(name = "Photos",
        description = "API de gestion des photos via Flickr")
public interface PhotoApi {

    /**
     * Sauvegarde une photo sur Flickr et l'associe à une entité.
     *
     * @param context Le contexte de la photo
     *                (client, fournisseur, produit, entreprise, utilisateur).
     * @param id      L'identifiant de l'entité associée.
     * @param photo   Le fichier photo à uploader.
     * @param title   Le titre de la photo.
     * @return L'objet mis à jour avec l'URL de la photo.
     * @throws IOException      Si une erreur d'entrée/sortie se produit.
     * @throws FlickrException  Si une erreur Flickr se produit.
     */
    @Operation(
            summary = "Sauvegarder une photo",
            description = "Permet d'uploader une photo sur Flickr et de l'associer à une entité"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Photo sauvegardée avec succès"),
            @ApiResponse(responseCode = "400",
                    description = "Fichier invalide ou contexte inconnu"),
            @ApiResponse(responseCode = "500",
                    description = "Erreur lors de l'upload sur Flickr")
    })
    @PostMapping(value = APP_ROOT + "/photos/save/{id}/{title}/{context}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Object savePhoto(
            @PathVariable("context") String context,
            @PathVariable("id") Integer id,
            @RequestPart("file") MultipartFile photo,
            @PathVariable("title") String title
    ) throws IOException, FlickrException;
}