package com.macspace.gestiondestock.controller;

import com.macspace.gestiondestock.controller.api.FournisseurApi;
import com.macspace.gestiondestock.dto.FournisseurDto;
import com.macspace.gestiondestock.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des fournisseurs dans MacSpace.
 * Implémente les endpoints définis dans {@link FournisseurApi}.
 */
@RestController
public class FournisseurController implements FournisseurApi {

    private final FournisseurService fournisseurService;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param fournisseurService Le service de gestion des fournisseurs.
     */
    @Autowired
    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FournisseurDto save(FournisseurDto dto) {
        return fournisseurService.save(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FournisseurDto findById(Integer id) {
        return fournisseurService.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FournisseurDto findByEmail(String email) {
        return fournisseurService.findByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FournisseurDto> findAllByNom(String nom) {
        return fournisseurService.findAllByNom(nom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurService.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FournisseurDto> findAllByIdEntreprise(Integer idEntreprise) {
        return fournisseurService.findAllByIdEntreprise(idEntreprise);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        fournisseurService.delete(id);
    }
}