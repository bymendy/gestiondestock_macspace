package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.EntrepriseDto;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.macspace.gestiondestock.utils.Constants.ENTREPRISE_ENDPOINT;

/**
 * API pour gérer les opérations liées aux entreprises.
 * Cette interface définit les endpoints pour la création, la recherche,
 * la récupération de toutes les entreprises, et la suppression d'une entreprise.
 */
@Api("entreprises")
public interface EntrepriseApi {

    /**
     * Crée ou enregistre une nouvelle entreprise.
     *
     * @param dto le Data Transfer Object (DTO) représentant l'entreprise à créer ou à mettre à jour.
     * @return le DTO de l'entreprise créée ou mise à jour.
     */
    @PostMapping(ENTREPRISE_ENDPOINT + "/create")
    EntrepriseDto save(@RequestBody EntrepriseDto dto);

    /**
     * Recherche une entreprise par son ID.
     *
     * @param id l'ID de l'entreprise à rechercher.
     * @return le DTO de l'entreprise trouvée.
     */
    @GetMapping(ENTREPRISE_ENDPOINT + "/{idEntreprise}")
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);

    /**
     * Récupère la liste de toutes les entreprises.
     *
     * @return la liste des DTO des entreprises.
     */
    @GetMapping(ENTREPRISE_ENDPOINT + "/all")
    List<EntrepriseDto> findAll();

    /**
     * Supprime une entreprise par son ID.
     *
     * @param id l'ID de l'entreprise à supprimer.
     */
    @DeleteMapping(ENTREPRISE_ENDPOINT + "/delete/{idEntreprise}")
    void delete(@PathVariable("idEntreprise") Integer id);
}
