package com.macspace.gestiondestock.controller;

import java.util.List;

import com.macspace.gestiondestock.controller.api.FournisseurApi;
import com.macspace.gestiondestock.dto.FournisseurDto;
import com.macspace.gestiondestock.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour gérer les opérations liées aux fournisseurs.
 * Implémente les méthodes définies dans l'interface FournisseurApi.
 */
@RestController
public class FournisseurController implements FournisseurApi {

    private final FournisseurService fournisseurService;

    /**
     * Constructeur avec injection du service FournisseurService.
     *
     * @param fournisseurService Le service utilisé pour gérer les fournisseurs.
     */
    @Autowired
    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    /**
     * Enregistre un nouveau fournisseur ou met à jour un fournisseur existant.
     *
     * @param dto Le DTO du fournisseur à enregistrer ou à mettre à jour.
     * @return Le DTO du fournisseur enregistré ou mis à jour.
     */
    @Override
    public FournisseurDto save(FournisseurDto dto) {
        return fournisseurService.save(dto);
    }

    /**
     * Recherche un fournisseur par son ID.
     *
     * @param id L'ID du fournisseur à rechercher.
     * @return Le DTO du fournisseur trouvé.
     */
    @Override
    public FournisseurDto findById(Integer id) {
        return fournisseurService.findById(id);
    }

    /**
     * Renvoie une liste de tous les fournisseurs.
     *
     * @return Une liste de DTO des fournisseurs.
     */
    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurService.findAll();
    }

    /**
     * Supprime un fournisseur par son ID.
     *
     * @param id L'ID du fournisseur à supprimer.
     */
    @Override
    public void delete(Integer id) {
        fournisseurService.delete(id);
    }
}
