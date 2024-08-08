package com.macspace.gestiondestock.controller;

import java.util.List;

import com.macspace.gestiondestock.controller.api.EntrepriseApi;
import com.macspace.gestiondestock.dto.EntrepriseDto;
import com.macspace.gestiondestock.services.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour gérer les entreprises.
 * Implémente l'interface {@link EntrepriseApi} et utilise le service {@link EntrepriseService}
 * pour effectuer les opérations de gestion des entreprises.
 */
@RestController
public class EntrepriseController implements EntrepriseApi {

    private EntrepriseService entrepriseService;

    /**
     * Constructeur avec injection de dépendance pour {@link EntrepriseService}.
     *
     * @param entrepriseService le service des entreprises à injecter.
     */
    @Autowired
    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    /**
     * Enregistre ou met à jour une entreprise.
     *
     * @param dto le DTO de l'entreprise à enregistrer ou à mettre à jour.
     * @return le DTO de l'entreprise enregistrée ou mise à jour.
     */
    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        return entrepriseService.save(dto);
    }

    /**
     * Recherche une entreprise par son ID.
     *
     * @param id l'ID de l'entreprise à rechercher.
     * @return le DTO de l'entreprise trouvée.
     */
    @Override
    public EntrepriseDto findById(Integer id) {
        return entrepriseService.findById(id);
    }

    /**
     * Récupère la liste de toutes les entreprises.
     *
     * @return la liste des DTO des entreprises.
     */
    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseService.findAll();
    }

    /**
     * Supprime une entreprise par son ID.
     *
     * @param id l'ID de l'entreprise à supprimer.
     */
    @Override
    public void delete(Integer id) {
        entrepriseService.delete(id);
    }
}
