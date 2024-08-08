package com.macspace.gestiondestock.controller.api;

import com.macspace.gestiondestock.dto.InterventionsDto;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.macspace.gestiondestock.utils.Constants.INTERVENTIONS_ENDPOINT;

/**
 * API pour la gestion des interventions.
 * Fournit des points de terminaison pour créer, récupérer et supprimer des interventions.
 */
@Api("interventions")
public interface InterventionsApi {

    /**
     * Enregistre une nouvelle intervention.
     *
     * @param dto l'objet de transfert de données pour l'intervention
     * @return l'objet de transfert de données de l'intervention sauvegardée
     */
    @PostMapping(INTERVENTIONS_ENDPOINT + "/create")
    InterventionsDto save(@RequestBody InterventionsDto dto);

    /**
     * Récupère une intervention par son identifiant.
     *
     * @param id l'identifiant de l'intervention
     * @return l'objet de transfert de données de l'intervention trouvée
     */
    @GetMapping(INTERVENTIONS_ENDPOINT + "/{idIntervention}")
    InterventionsDto findById(@PathVariable("idIntervention") Integer id);

    /**
     * Récupère une intervention par son code.
     *
     * @param code le code de l'intervention
     * @return l'objet de transfert de données de l'intervention trouvée
     */
    @GetMapping(INTERVENTIONS_ENDPOINT + "/{codeIntervention}")
    InterventionsDto findByCode(@PathVariable("codeIntervention") String code);

    /**
     * Récupère toutes les interventions.
     *
     * @return la liste de tous les objets de transfert de données des interventions
     */
    @GetMapping(INTERVENTIONS_ENDPOINT + "/all")
    List<InterventionsDto> findAll();

    /**
     * Supprime une intervention par son identifiant.
     *
     * @param id l'identifiant de l'intervention à supprimer
     */
    @DeleteMapping(INTERVENTIONS_ENDPOINT + "/delete/{idIntervention}")
    void delete(@PathVariable("idIntervention") Integer id);
}
