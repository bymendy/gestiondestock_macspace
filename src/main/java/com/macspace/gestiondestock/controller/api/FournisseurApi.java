package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.FournisseurDto;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.macspace.gestiondestock.utils.Constants.FOURNISSEUR_ENDPOINT;

/**
 * Interface API pour gérer les opérations liées aux fournisseurs.
 * Fournit des méthodes pour créer, lire, et supprimer des fournisseurs.
 */
@Api("fournisseur")
public interface FournisseurApi {

    /**
     * Enregistre un nouveau fournisseur ou met à jour un fournisseur existant.
     *
     * @param dto Le DTO du fournisseur à enregistrer ou à mettre à jour.
     * @return Le DTO du fournisseur enregistré ou mis à jour.
     */
    @PostMapping(FOURNISSEUR_ENDPOINT + "/create")
    FournisseurDto save(@RequestBody FournisseurDto dto);

    /**
     * Recherche un fournisseur par son ID.
     *
     * @param id L'ID du fournisseur à rechercher.
     * @return Le DTO du fournisseur trouvé.
     */
    @GetMapping(FOURNISSEUR_ENDPOINT + "/{idFournisseur}")
    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);

    /**
     * Renvoie une liste de tous les fournisseurs.
     *
     * @return Une liste de DTO des fournisseurs.
     */
    @GetMapping(FOURNISSEUR_ENDPOINT + "/all")
    List<FournisseurDto> findAll();

    /**
     * Supprime un fournisseur par son ID.
     *
     * @param id L'ID du fournisseur à supprimer.
     */
    @DeleteMapping(FOURNISSEUR_ENDPOINT + "/delete/{idFournisseur}")
    void delete(@PathVariable("idFournisseur") Integer id);

}
