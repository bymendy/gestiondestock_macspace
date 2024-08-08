package com.macspace.gestiondestock.controller;

import java.util.List;

import com.macspace.gestiondestock.controller.api.InterventionsApi;
import com.macspace.gestiondestock.dto.InterventionsDto;
import com.macspace.gestiondestock.services.InterventionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur pour la gestion des interventions.
 * <p>
 * Cette classe implémente l'interface {@link InterventionsApi} et fournit les
 * points de terminaison REST pour gérer les interventions à travers les services
 * définis dans {@link InterventionsService}.
 * </p>
 */
@RestController
public class InterventionsController implements InterventionsApi {

    private final InterventionsService interventionsService;

    /**
     * Constructeur de la classe {@link InterventionsController}.
     * <p>
     * Initialise le service des interventions injecté par Spring.
     * </p>
     *
     * @param interventionsService le service des interventions
     */
    @Autowired
    public InterventionsController(InterventionsService interventionsService) {
        this.interventionsService = interventionsService;
    }

    /**
     * Enregistre une nouvelle intervention.
     * <p>
     * Appelle le service pour sauvegarder une intervention et retourne
     * l'objet de transfert de données de l'intervention sauvegardée.
     * </p>
     *
     * @param dto l'objet de transfert de données de l'intervention à sauvegarder
     * @return l'objet de transfert de données de l'intervention sauvegardée
     */
    @Override
    public InterventionsDto save(InterventionsDto dto) {
        return interventionsService.save(dto);
    }

    /**
     * Récupère une intervention par son identifiant.
     * <p>
     * Appelle le service pour obtenir une intervention par son identifiant
     * et retourne l'objet de transfert de données de l'intervention trouvée.
     * </p>
     *
     * @param id l'identifiant de l'intervention à rechercher
     * @return l'objet de transfert de données de l'intervention trouvée
     */
    @Override
    public InterventionsDto findById(Integer id) {
        return interventionsService.findById(id);
    }

    /**
     * Récupère une intervention par son code.
     * <p>
     * Appelle le service pour obtenir une intervention par son code
     * et retourne l'objet de transfert de données de l'intervention trouvée.
     * </p>
     *
     * @param code le code de l'intervention à rechercher
     * @return l'objet de transfert de données de l'intervention trouvée
     */
    @Override
    public InterventionsDto findByCode(String code) {
        return interventionsService.findByCode(code);
    }

    /**
     * Récupère toutes les interventions.
     * <p>
     * Appelle le service pour obtenir la liste de toutes les interventions
     * et retourne cette liste d'objets de transfert de données.
     * </p>
     *
     * @return la liste de tous les objets de transfert de données des interventions
     */
    @Override
    public List<InterventionsDto> findAll() {
        return interventionsService.findAll();
    }

    /**
     * Supprime une intervention par son identifiant.
     * <p>
     * Appelle le service pour supprimer une intervention en fonction de son identifiant.
     * </p>
     *
     * @param id l'identifiant de l'intervention à supprimer
     */
    @Override
    public void delete(Integer id) {
        interventionsService.delete(id);
    }
}
